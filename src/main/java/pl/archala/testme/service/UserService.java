package pl.archala.testme.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.archala.testme.entity.User;
import pl.archala.testme.repository.TokenRepository;
import pl.archala.testme.repository.UserRepository;
import pl.archala.testme.entity.Token;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.UUID;

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
        if (userRepo.findByEmail(user.getEmail()).isPresent()) return -1;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        sendToken(user);
        return 1;
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

}
