package uz.pdp.g30springmailmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.g30springmailmanager.domain.Authentication;
import uz.pdp.g30springmailmanager.domain.Status;
import uz.pdp.g30springmailmanager.domain.User;
import uz.pdp.g30springmailmanager.repo.AuthenticationRepository;
import uz.pdp.g30springmailmanager.repo.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationRepository authenticationRepository;
    private final UserRepository userRepository;


    public void validateUserAuthentication(final String token) {
        final var authentication = authenticationRepository.findByToken(token).orElseThrow(
                () -> new RuntimeException("Token not found")
        );

        final var user = userRepository.findById(authentication.getUser().getId()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        if (authentication.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token already expired");
        }

        user.setStatus(Status.ACTIVE);
    }

    public Authentication createAuthentication(User user){
        Authentication authentication = new Authentication();
        authentication.setCreationTime(LocalDateTime.now());
        authentication.setExpirationTime(LocalDateTime.now().plusHours(1));
        authentication.setUser(user);
        authentication.setToken(UUID.randomUUID().toString());
        return authenticationRepository.save(authentication);
    }
}
