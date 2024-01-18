package uz.pdp.g30springmailmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.g30springmailmanager.domain.Status;
import uz.pdp.g30springmailmanager.domain.User;
import uz.pdp.g30springmailmanager.dto.UserDto;
import uz.pdp.g30springmailmanager.dto.UserRegisterDto;
import uz.pdp.g30springmailmanager.repo.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserDto register(final UserRegisterDto userRegisterDto) {
        if (userRepository.existsByEmailAndStatusNot(userRegisterDto.email(), Status.BLOCKED)) {
            throw new RuntimeException("User blocked");
        }
        var user = new User();
        user.setEmail(userRegisterDto.email());
        user.setStatus(Status.PENDING);
        user.setPassword(userRegisterDto.password());
        user = userRepository.save(user);
        emailService.sendEmailVerificationMessage(user);
        return new UserDto(user.getId(), user.getEmail());
    }
}
