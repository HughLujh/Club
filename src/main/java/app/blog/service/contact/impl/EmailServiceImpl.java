package app.blog.service.contact.impl;

import app.blog.config.property.EmailProperty;
import app.blog.model.contact.Contact;
import app.blog.service.contact.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Service
public class EmailServiceImpl implements EmailService<Contact> {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String adminEmail;

    @Autowired
    private EmailProperty emailProperty;
    @Override
    public void save(Contact contact) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void sendEmail(Contact contact) {
        SimpleMailMessage message = new SimpleMailMessage();
        Map<String, String> classifications = emailProperty.getClassifications();
        if (classifications != null) {
            String techEmail = classifications.get("tech");

            if (techEmail != null && !techEmail.isEmpty()) {
                message.setTo(techEmail);
            } else {
                System.out.println("No tech recipients found.");
            }
        } else {
            System.out.println("Email classifications map is null.");
        }
        String messageBody = "From " + contact.getEmail() + " \n\n"+contact.getMessage();

        message.setFrom(adminEmail);
        message.setSubject(contact.getSubject());
        message.setText(messageBody);
        mailSender.send(message);
    }
}
