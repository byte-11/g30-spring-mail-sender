package uz.pdp.g30springmailmanager.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import uz.pdp.g30springmailmanager.domain.Authentication;
import uz.pdp.g30springmailmanager.domain.User;
import uz.pdp.g30springmailmanager.dto.MailDto;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
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
        sendEmailVerificationMessage(new User());
    }

    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @SneakyThrows
    @Async
    public void sendEmailVerificationMessage(final User user) {
        TimeUnit.SECONDS.sleep(10);
        var helper = new MimeMessageHelper(mailSender.createMimeMessage());
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
        log.info("[{}] email sent", Thread.currentThread().getName());
    }
}
