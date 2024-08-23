package app.blog.model.contact.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ContactRequest {
    private String name;
    private String email;
    private String subject;
    private String message;
}
