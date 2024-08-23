package app.blog.controller.contact;

import app.blog.controller.base.BaseController;
import app.blog.model.contact.Contact;
import app.blog.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/contact")
public class ContactController implements BaseController<Contact> {
    @Autowired
    private EmailService emailService;

    @Override
    @PostMapping
    public ResponseEntity<Map> save(@RequestBody Contact contact) {
        emailService.sendEmail(contact);
        return null;
    }
}
