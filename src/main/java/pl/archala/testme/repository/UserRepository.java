package pl.archala.testme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.archala.testme.entity.User;
import pl.archala.testme.enums.RoleEnum;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String username);

    List<User> findByRole(RoleEnum roleEnum);
}
