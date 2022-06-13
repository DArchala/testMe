package pl.archala.testme.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.archala.testme.dto.DataTableSortPage;
import pl.archala.testme.dto.PasswordChangeRequest;
import pl.archala.testme.entity.Token;
import pl.archala.testme.entity.User;
import pl.archala.testme.enums.TokenMailType;
import pl.archala.testme.repository.TokenRepository;
import pl.archala.testme.repository.UserRepository;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.archala.testme.enums.RoleEnum.*;
import static pl.archala.testme.enums.TokenMailType.*;

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
        if (userRepo.findByUsername(user.getUsername()).isPresent()) return 1;
        if (userRepo.findByEmail(user.getEmail()).isPresent()) return 2;
        if (user.getUsername().equals(user.getEmail())) return 3;
        if (user.getUsername().equals(user.getPassword())) return 4;
        if (user.getEmail().equals(user.getPassword())) return 5;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        sendTokenMail(user, ACTIVATE_ACCOUNT);
        return 0;
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

    public int deleteUser(Long id) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) return 0;

        if (user.getRole().equals(ADMIN) && findAllAdmins().size() == 1) return 1;

        List<Token> userTokens = tokenRepo.findAllByUserUsername(user.getUsername());
        if (!userTokens.isEmpty()) tokenRepo.deleteAll(userTokens);

        userRepo.delete(user);
        return 2;
    }

    public List<User> findAllAdmins() {
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

    public int activateAccountByToken(String value) {
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

    public int updatePasswordByRequest(PasswordChangeRequest request) {
        User user = userRepo.findByUsername(request.getUsername()).orElse(null);
        if (user == null) return 0;

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) return 1;
        if (!user.getEmail().equals(request.getEmail())) return 2;
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) return 3;

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepo.save(user);
        return 4;
    }

    public int resetPassword(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        if (user == null) return 0;
        if (!user.isEnabled()) return 2;

        sendTokenMail(user, PASSWORD_RESET);
        return 1;
    }

    public int resetPasswordByToken(String value) {
        Token token = tokenRepo.findByValue(value).orElse(null);
        if (token == null) return 0;

        if (token.getExpirationDate().isBefore(LocalDateTime.now())) {
            tokenRepo.delete(token);
            return 1;
        }

        return 2;
    }

    public User findUserByTokenValue(String value) {
        Token token = tokenRepo.findByValue(value).orElseThrow();
        return token.getUser();
    }

    public int updateNewPasswordUser(User newPasswordUser) {
        User user = userRepo.findByUsername(newPasswordUser.getUsername()).orElse(null);
        if (user == null) return 0;

        user.setPassword(passwordEncoder.encode(newPasswordUser.getPassword()));
        userRepo.save(user);
        return 1;
    }

    public List<User> findAllUsersPaginated(DataTableSortPage dtSortPage) {
        String column = dtSortPage.getColumn();
        String direction = dtSortPage.getDirection().toUpperCase();

        Sort sort = getSortingType(direction, column);
        Pageable pageable = PageRequest.of(dtSortPage.getPage(), dtSortPage.getLength(), sort);

        return userRepo.findAll(pageable).getContent();
    }

    private Sort getSortingType(String direction, String column) {
        if (direction.equals("ASC")) return sortAscending(column);
        else return sortDescending(column);
    }

    private Sort sortAscending(String column) {
        return Sort.by(column).ascending();
    }

    private Sort sortDescending(String column) {
        return Sort.by(column).descending();
    }
}