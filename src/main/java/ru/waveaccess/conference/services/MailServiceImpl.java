package ru.waveaccess.conference.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {
    @Value("${mail.from.username}")
    private String FROM;
    @Value("${confirm.root}")
    private String CONFIRM_ROOT;

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final JavaMailSender sender;

    @Override
    public boolean sendEmailConfirmationLink(String firstName, String email, String uniqueId) {
        String mailText = CONFIRM_ROOT + uniqueId;
        return execSendText(email, "email confirmation", mailText);
    }

    private boolean execSendText(String email, String subject, String text) {
        Future<?> future = executorService.submit(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(subject);
            message.setText(text);
            message.setFrom(FROM);

            sender.send(message);
        });
        return future.isDone();
    }
}
