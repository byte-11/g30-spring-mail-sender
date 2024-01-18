package uz.pdp.g30springmailmanager.controller;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.g30springmailmanager.dto.UserRegisterDto;
import uz.pdp.g30springmailmanager.service.AuthService;
import uz.pdp.g30springmailmanager.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody final UserRegisterDto userRegisterDto){
        return ResponseEntity.ok(userService.register(userRegisterDto));
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam String token){
        authService.validateUserAuthentication(token);
        return ResponseEntity.ok("Email verified");
    }
}
