package com.hoaxify.webservice.email;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.hoaxify.webservice.shared.Messages;
import com.hoaxify.webservice.configuration.HoaxifyProperties;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    private JavaMailSenderImpl mailSender;

    @Autowired
    HoaxifyProperties hoaxifyProperties;

    @Autowired
    MessageSource messageSource;

    @PostConstruct
    public void initialize() {
        this.mailSender = new JavaMailSenderImpl();
        mailSender.setHost(hoaxifyProperties.getEmail().host());
        mailSender.setPort(hoaxifyProperties.getEmail().port());
        mailSender.setUsername(hoaxifyProperties.getEmail().username());
        mailSender.setPassword(hoaxifyProperties.getEmail().password());
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", true);
    }

    String activationEmail = """
            <html>
            <body>
            <h1> ${title} </h1>
            <a href = "${url}"> ${clickHere} </a>
            </body>
            </html>
            """;

    public void sendActivationEmail(String email, String activationToken) {
        var activationUrl = hoaxifyProperties.getClient().host() +  activationToken;
        var title = Messages.getMessageForLocale("hoaxify.mail.user.created.title",LocaleContextHolder.getLocale());
        var clickHere = Messages.getMessageForLocale("hoaxify.mail.click.here",LocaleContextHolder.getLocale());
        var mailBody = activationEmail
        .replace("${url}", activationUrl)
        .replace("${title}", title)
        .replace("${clickHere}", clickHere);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

        try {
            message.setFrom(hoaxifyProperties.getEmail().from());
            message.setTo(email);
            message.setSubject(title);
            message.setText(mailBody, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        this.mailSender.send(mimeMessage);
    }

}
