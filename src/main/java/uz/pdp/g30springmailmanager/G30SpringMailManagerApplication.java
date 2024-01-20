package uz.pdp.g30springmailmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class G30SpringMailManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(G30SpringMailManagerApplication.class, args);
    }

}
