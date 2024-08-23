package app.blog.service.contact;

import app.blog.model.contact.Contact;
import app.blog.service.BaseService;

public interface EmailService<T> extends BaseService<T> {
    void sendEmail(Contact contact);
}

