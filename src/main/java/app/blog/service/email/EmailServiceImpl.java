package app.blog.service.email;

import app.blog.model.contact.Contact;
import app.blog.model.contact.EmailProperties;
import app.blog.model.contact.dto.ContactRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.StringJoiner;

@Service
public class EmailServiceImpl implements EmailService<Contact>{
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String adminEmail;

    @Autowired
    private EmailProperties emailProperties;
    @Override
    public void save(Contact contact) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void sendEmail(Contact contact) {
        SimpleMailMessage message = new SimpleMailMessage();
        List<String> techEmails = emailProperties.getClassifications().get("tech");

        if (techEmails != null && !techEmails.isEmpty()) {
            StringJoiner joiner = new StringJoiner(", ");
            for (String email : techEmails) {
                joiner.add(email);
            }
            String toEmailString = joiner.toString();
            message.setTo(toEmailString);

        } else {
            System.out.println("No tech recipients found.");
        }
        message.setFrom(adminEmail);
        message.setSubject(contact.getSubject());
        message.setText(contact.getMessage());
        mailSender.send(message);
    }
}
