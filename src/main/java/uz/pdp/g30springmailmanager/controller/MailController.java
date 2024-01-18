package uz.pdp.g30springmailmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.g30springmailmanager.dto.MailDto;
import uz.pdp.g30springmailmanager.service.EmailService;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<?> sendMailMessage(@RequestBody MailDto mailDto){
        emailService.send(mailDto);
        return ResponseEntity.ok("Email sent");
    }
}
