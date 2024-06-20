package ru.mp.service;

public interface MailService {

    void send(String to, String subject, String text);
}
