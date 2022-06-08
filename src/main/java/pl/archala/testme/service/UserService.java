package pl.archala.testme.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.archala.testme.dto.PasswordChangeRequest;
import pl.archala.testme.entity.Token;
import pl.archala.testme.entity.User;
import pl.archala.testme.repository.TokenRepository;
import pl.archala.testme.repository.UserRepository;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.archala.testme.enums.RoleEnum.ADMIN;
import static pl.archala.testme.enums.RoleEnum.USER;

@Service
public class UserService {

    private final UserRepository userRepo;

    private final PasswordEncoder passwordEncoder;

    private final TokenRepository tokenRepo;

    private final MailService mailService;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder, TokenRepository tokenRepo, MailService mailService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepo = tokenRepo;
        this.mailService = mailService;
    }

    public int registerUser(User user) {
        if (userRepo.findByUsername(user.getUsername()).isPresent()) return 0;
        if (userRepo.findByEmail(user.getEmail()).isPresent()) return 1;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        sendToken(user);
        return 2;
    }

    private void sendToken(User user) {
        String tokenValue = UUID.randomUUID().toString();

        Token token = new Token(user, tokenValue, LocalDateTime.now().plusMinutes(10));
        tokenRepo.save(token);

        String url = "http://localhost:4200/#/activate-account/" + tokenValue;
        String message = getMailMessage(url);

        try {
            mailService.sendMail(user.getEmail(), "Link aktywacyjny", message, false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    private String getMailMessage(String url) {
        StringBuilder sb = new StringBuilder();
        sb.append("Kliknij w poniższy link, aby aktywować Twoje konto:\n\n");
        sb.append(url);
        sb.append("\n\nAutor aplikacji: Damian Archała");
        return sb.toString();
    }

    public int deleteUser(Long id) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) return 0;

        if (user.getRole().equals(ADMIN) && getAllAdmins().size() == 1) return 1;

        List<Token> userTokens = tokenRepo.findAllByUserUsername(user.getUsername());
        if (!userTokens.isEmpty()) tokenRepo.deleteAll(userTokens);

        userRepo.delete(user);
        return 2;
    }

    private List<User> getAllAdmins() {
        return userRepo.findAll()
                .stream()
                .filter(u -> u.getRole().equals(ADMIN))
                .collect(Collectors.toList());
    }

    public int updateUserRole(User newUser) {
        if (newUser.isNew()) return 0;

        User user = userRepo.findById(newUser.getId()).orElse(null);
        if (user == null) return 0;

        if (user.getRole().equals(ADMIN)
                && newUser.getRole().equals(USER)
                && userRepo.findByRole(ADMIN).size() == 1)
            return 2;

        user.setRole(newUser.getRole());
        userRepo.save(user);
        return 1;
    }

    public int activateAccount(String value) {
        Token token = tokenRepo.findByValue(value).orElse(null);
        if (token == null) return 0;

        if (token.getExpirationDate().isBefore(LocalDateTime.now())) {
            tokenRepo.delete(token);
            Optional<User> user = userRepo.findByUsername(token.getUser().getUsername());
            if (user.isPresent() && !user.get().isEnabled()) userRepo.delete(user.get());
            return 1;
        }

        User user = token.getUser();
        if (user == null) return 2;

        user.setEnabled(true);
        userRepo.save(user);
        tokenRepo.delete(token);
        return 3;
    }

    public int updatePassword(PasswordChangeRequest passwordChangeRequest) {
        User user = userRepo.findByUsername(passwordChangeRequest.getUsername()).orElse(null);
        if (user == null) return 0;

        if (!passwordEncoder.matches(passwordChangeRequest.getCurrentPassword(), user.getPassword())) return 1;
        if (!user.getEmail().equals(passwordChangeRequest.getEmail())) return 2;
        if (passwordEncoder.matches(passwordChangeRequest.getNewPassword(), user.getPassword())) return 3;

        user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));

        userRepo.save(user);
        return 4;
    }
}