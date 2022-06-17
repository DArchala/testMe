package pl.archala.testme.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.archala.testme.entity.Token;
import pl.archala.testme.entity.User;
import pl.archala.testme.enums.TokenMailType;
import pl.archala.testme.repository.TokenRepository;
import pl.archala.testme.repository.UserRepository;

import javax.mail.MessagingException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static pl.archala.testme.enums.TokenMailType.ACTIVATE_ACCOUNT;
import static pl.archala.testme.enums.TokenMailType.PASSWORD_RESET;

@Service
public class UserAuthService {

    private final UserRepository userRepo;

    private final PasswordEncoder passwordEncoder;

    private final TokenRepository tokenRepo;

    private final MailService mailService;

    public UserAuthService(UserRepository userRepo, PasswordEncoder passwordEncoder, TokenRepository tokenRepo, MailService mailService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepo = tokenRepo;
        this.mailService = mailService;
    }

    public void resetPasswordByToken(String value) {
        Token token = tokenRepo.findByValue(value).orElseThrow(() -> new EntityNotFoundException("Token does not exist"));

        if (token.getExpirationDate().isBefore(LocalDateTime.now())) {
            tokenRepo.delete(token);
            throw new RuntimeException("Token has expired");
        }

    }

    public User findUserByTokenValue(String value) {
        Token token = tokenRepo.findByValue(value).orElseThrow(() -> new EntityNotFoundException("Token does not exist"));
        if (token.getUser() == null) throw new EntityNotFoundException("Token user does not exist");
        return token.getUser();
    }

    public void activateAccountByToken(String value) {
        Token token = tokenRepo.findByValue(value).orElseThrow(() -> new EntityNotFoundException("Token does not exist"));

        if (token.getExpirationDate().isBefore(LocalDateTime.now())) {
            tokenRepo.delete(token);
            Optional<User> user = userRepo.findByUsername(token.getUser().getUsername());
            if (user.isPresent() && !user.get().isEnabled()) userRepo.delete(user.get());
            throw new RuntimeException("Token has expired");
        }

        if (token.getUser() == null) throw new EntityNotFoundException("User does not exist");
        User user = token.getUser();

        user.setEnabled(true);
        userRepo.save(user);
        tokenRepo.delete(token);
    }

    public void registerUser(User user) {
        if (userRepo.findByUsername(user.getUsername()).isPresent())
            throw new EntityExistsException("Username is already taken");

        if (userRepo.findByEmail(user.getEmail()).isPresent())
            throw new EntityExistsException("Email is already taken");

        if (user.getUsername().equals(user.getEmail()))
            throw new IllegalArgumentException("Username cannot be equal to email");

        if (user.getUsername().equals(user.getPassword()))
            throw new IllegalArgumentException("Username cannot be equal to password");

        if (user.getEmail().equals(user.getPassword()))
            throw new IllegalArgumentException("Email cannot be equal to password");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        sendTokenMail(user, ACTIVATE_ACCOUNT);
    }

    private void sendTokenMail(User user, TokenMailType mailType) {
        String tokenValue = saveNewToken(user);
        String url, subject, message;
        switch (mailType) {
            case PASSWORD_RESET:
                url = "http://localhost:4200/#/password-reset-new/" + tokenValue;
                subject = "Password reset";
                message = getPasswordResetMailMessage(url);
                break;
            case ACTIVATE_ACCOUNT:
                url = "http://localhost:4200/#/activate-account/" + tokenValue;
                subject = "Activation link";
                message = getActivateAccountMailMessage(url);
                break;
            default:
                throw new IllegalArgumentException("TokenMailType is invalid.");
        }
        sendMail(user.getEmail(), subject, message);
    }

    private String saveNewToken(User user) {
        String tokenValue = UUID.randomUUID().toString();
        Token token = new Token(user, tokenValue, LocalDateTime.now().plusMinutes(10));
        tokenRepo.save(token);
        return tokenValue;
    }

    private String getPasswordResetMailMessage(String url) {
        return "Click in the link below, to redirect you to password change page:\n\n"
                + url + "\n\nMessage generated automatically";
    }

    private String getActivateAccountMailMessage(String url) {
        return "Click in the link below, to activate your account:\n\n"
                + url + "\n\nMessage generated automatically";
    }

    private void sendMail(String userEmail, String subject, String message) {
        try {
            mailService.sendMail(userEmail, subject, message, false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void resetPassword(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User does not exist"));
        if (!user.isEnabled()) throw new IllegalArgumentException("Resetting password for disabled user is forbidden");

        sendTokenMail(user, PASSWORD_RESET);
    }
}