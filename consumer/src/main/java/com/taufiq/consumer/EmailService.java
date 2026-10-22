package com.taufiq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Mengirim email via SMTP. Jika app.mail.enabled=false (atau SMTP belum dikonfigurasi),
 * email hanya disimulasikan dengan mencetak isinya ke log.
 */
@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final ObjectProvider<JavaMailSender> mailSenderProvider;

    @Value("${app.mail.enabled:false}")
    private boolean mailEnabled;

    @Value("${app.mail.from:no-reply@taufiq-store.local}")
    private String from;

    public EmailService(ObjectProvider<JavaMailSender> mailSenderProvider) {
        this.mailSenderProvider = mailSenderProvider;
    }

    public void send(String to, String subject, String body) {
        JavaMailSender mailSender = mailSenderProvider.getIfAvailable();

        if (!mailEnabled || mailSender == null) {
            log.info("""

                    ========== [SIMULASI EMAIL] ==========
                    From    : {}
                    To      : {}
                    Subject : {}
                    --------------------------------------
                    {}
                    ======================================""", from, to, subject, body);
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message); // exception -> listener retry
        log.info("[EMAIL] Email '{}' terkirim ke {}", subject, to);
    }
}
