package ru.waveaccess.conference.services;

public interface MailService {
    boolean sendEmailConfirmationLink(String firstName, String email, String confirmLink);
}
