package com.riwi.final_assessment_logistics_warehouse.common.infrastructure.mails;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import jakarta.mail.Address;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendHtmlEmail(String[] recipients, String subject, String htmlTemplate)
            throws MessagingException, RuntimeException {
        MimeMessage message = this.javaMailSender.createMimeMessage();
        Address[] addresses = this.convertToAddresses(recipients);

        message.setFrom(this.mailUsername);
        message.setRecipients(MimeMessage.RecipientType.TO, addresses);
        message.setSubject(subject);
        message.setContent(htmlTemplate, "text/html; charset=UTF-8");

        this.logger.info("Sending email to {} with subject {}", recipients, subject);

        this.javaMailSender.send(message);
    }

    private Address[] convertToAddresses(String[] recipients) throws RuntimeException {
        return Arrays.stream(recipients).map(recipient -> {
            try {
                return new InternetAddress(recipient);
            } catch (AddressException e) {
                throw new RuntimeException(e);
            }
        }).toArray(Address[]::new);
    }
}