package com.hoaxify.webservice.user;

import java.util.Properties;
import java.util.UUID;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserService {
  @Autowired
  UserRepository userRepository;

  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
@Transactional(rollbackOn = MailException.class)
  public void save(User user) {
    try {
      String encodedPassword = passwordEncoder.encode(user.getPassword());
      user.setPassword(encodedPassword);
      user.setActivationToken(UUID.randomUUID().toString());
      userRepository.saveAndFlush(user);
      sendActivationEmail(user);
    } catch (DataIntegrityViolationException ex) {
      throw new NotUniqueEmailException();
    }
  }
  private void sendActivationEmail(User user) {
SimpleMailMessage message = new SimpleMailMessage();
message.setFrom("noreply@my-app.com");
message.setTo(user.getEmail());
message.setSubject("Activate your account");
message.setText("To activate your account, click the link below:\n"
        + "http://localhost:5173/activation/" + user.getActivationToken());
        getJavaMailSender().send(message);
  }

  public JavaMailSender getJavaMailSender(){
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.ethereal.email");
    mailSender.setPort(587);
    mailSender.setUsername("maymie.cartwright@ethereal.email");
    mailSender.setPassword("Cn4NJES2CMvyZ2rTrS");
    Properties properties = mailSender.getJavaMailProperties();
    properties.put("mail.smtp.starttls.enable", true);
    return mailSender;
  }
}
