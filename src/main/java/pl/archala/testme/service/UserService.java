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
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
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

    public User findUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User does not exist"));
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

    public void deleteUser(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User does not exist"));

        if (user.getRole().equals(ADMIN) && findAllAdmins().size() == 1)
            throw new IllegalArgumentException("Deleting last admin is forbidden");

        List<Token> userTokens = tokenRepo.findAllByUserUsername(user.getUsername());
        if (!userTokens.isEmpty()) tokenRepo.deleteAll(userTokens);

        userRepo.delete(user);
    }

    public List<User> findAllAdmins() {
        return userRepo.findAll()
                .stream()
                .filter(u -> u.getRole().equals(ADMIN))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("all")
    public void updateUserRole(User newUser) {
        if (newUser.isNew()) throw new EntityNotFoundException("User does not exist");

        User user = userRepo.findById(newUser.getId()).orElseThrow(() -> new EntityNotFoundException("User does not exist"));

        if (user.getRole().equals(ADMIN)
                && newUser.getRole().equals(USER)
                && userRepo.findByRole(ADMIN).size() == 1)
            throw new IllegalArgumentException("Deleting last admin is forbidden");

        user.setRole(newUser.getRole());
        userRepo.save(user);
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

    public void updatePasswordByRequest(PasswordChangeRequest request) {
        User user = userRepo.findByUsername(request.getUsername()).orElseThrow(() -> new EntityNotFoundException("User does not exist"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword()))
            throw new IllegalArgumentException("The password you provided do not match with your current password");

        if (!user.getEmail().equals(request.getEmail()))
            throw new IllegalArgumentException("The email you provided do not match with your current email");

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword()))
            throw new IllegalArgumentException("The password you provided cannot be equal to your current password");

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepo.save(user);
    }

    public void resetPassword(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User does not exist"));
        if (!user.isEnabled()) throw new IllegalArgumentException("Resetting password for disabled user is forbidden");

        sendTokenMail(user, PASSWORD_RESET);
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

    public void updateNewPasswordUser(User newPasswordUser) {
        User user = userRepo.findByUsername(newPasswordUser.getUsername()).orElseThrow(() -> new EntityNotFoundException("User does not exist"));

        user.setPassword(passwordEncoder.encode(newPasswordUser.getPassword()));
        userRepo.save(user);
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