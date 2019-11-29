package ar.edu.itba.paw.services;


import ar.edu.itba.paw.interfaces.services.EmailService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Async
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

	/*  https://github.com/thymeleaf/thymeleafexamples-springmail */

    @Override
    public void sendEmail(String to, String subject, String htmlContent) {

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            message.setSubject(subject);
            message.setTo(to);
            message.setText(htmlContent, true /* isHtml */);
        } catch (MessagingException e) {
            LOGGER.error("Messaging Exception occurred.");
        }
        mailSender.send(mimeMessage);
    }
}