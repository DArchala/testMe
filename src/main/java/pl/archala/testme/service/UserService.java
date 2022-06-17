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

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder, TokenRepository tokenRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepo = tokenRepo;
    }

    public User findUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User does not exist"));
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