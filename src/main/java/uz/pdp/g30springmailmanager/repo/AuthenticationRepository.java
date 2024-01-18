package uz.pdp.g30springmailmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.g30springmailmanager.domain.Authentication;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {
    Optional<Authentication> findByToken(String token);
}