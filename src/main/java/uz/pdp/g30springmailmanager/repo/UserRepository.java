package uz.pdp.g30springmailmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.g30springmailmanager.domain.Status;
import uz.pdp.g30springmailmanager.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailAndStatusNot(String email, Status status);
    Optional<User> findByEmail(String email);


}