package pl.archala.testme.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.archala.testme.entity.User;
import pl.archala.testme.repository.TokenRepository;
import pl.archala.testme.repository.UserRepository;
import pl.archala.testme.security.Token;

import javax.mail.MessagingException;
import java.util.Optional;
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

        Token token = new Token();
        token.setValue(tokenValue);
        token.setUser(user);
        tokenRepo.save(token);

        String url = "http://localhost:8080/api/token?value=" + tokenValue;

        StringBuilder sb = new StringBuilder();
        sb.append("Kliknij w poniższy link, aby aktywować Twoje konto:\n\n");
        sb.append(url);
        sb.append("\n\nAutor aplikacji: Damian Archała");

        try {
            mailService.sendMail(user.getEmail(), "Link aktywacyjny", sb.toString(), false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public User findByUsername(String username) {
        Optional<User> user = userRepo.findByUsername(username);
        return user.orElse(null);
    }
}
