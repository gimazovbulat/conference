package ru.waveaccess.conference.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class ApplicationConfig {
    @Value("${mail.from.username}")
    private String MAIL_USERNAME;
    @Value("${mail.from.password}")
    private String MAIL_PASSWORD;
    @Value("${mail.debug}")
    private String MAIL_DEBUG;
    @Value("${mail.port}")
    private String MAIL_PORT;
    @Value("${mail.host}")
    private String MAIL_HOST;

    @Bean
    JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(MAIL_HOST);
        mailSender.setPort(Integer.parseInt(MAIL_PORT));
        mailSender.setUsername(MAIL_USERNAME);
        mailSender.setPassword(MAIL_PASSWORD);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", MAIL_DEBUG);

        return mailSender;
    }
}
