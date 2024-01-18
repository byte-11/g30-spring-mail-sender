package uz.pdp.g30springmailmanager.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import uz.pdp.g30springmailmanager.domain.Authentication;
import uz.pdp.g30springmailmanager.domain.User;
import uz.pdp.g30springmailmanager.dto.MailDto;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final Configuration configuration;
    private final AuthService authService;

    @Value("${frontend.url}")
    private String verificationUrl;

    @Value("${spring.application.support-email}")
    private String supportEmail;

    public void send(MailDto mailDto) {
        send(
                mailDto.getFrom(),
                mailDto.getTo(),
                mailDto.getSubject(),
                mailDto.getMessage()
        );
    }

    @SneakyThrows
    public void send(String from, String to, String subject, String message) {

    }

    @SneakyThrows
    public void sendEmailVerificationMessage(final User user) {
        var helper = new MimeMessageHelper(mailSender.createMimeMessage());
        helper.setSubject("Email Verification");
        helper.setFrom("pdp@gmail.com");
        helper.setTo(user.getEmail());
        Template template = configuration.getTemplate("mail/verification_email.ftl");
        Authentication authentication = authService.createAuthentication(user);
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(
                template,
                Map.of("verification_link", verificationUrl + authentication.getToken(),
                        "support_email", supportEmail)
        );
        helper.setText(html, true);
        mailSender.send(helper.getMimeMessage());
    }
}
