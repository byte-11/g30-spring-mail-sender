package uz.pdp.g30springmailmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class MailConfig {

    @Bean
    @Primary
    public FreeMarkerConfigurationFactoryBean configurationFactoryBean() {
        var factoryBean = new FreeMarkerConfigurationFactoryBean();
        factoryBean.setTemplateLoaderPath("classpath:/templates");
        return factoryBean;
    }
}
