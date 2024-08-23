package app.blog.model.contact;

import app.blog.model.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contact", indexes = @Index(name = "idx_contact_id", columnList = "id"))
public class Contact extends BaseEntity {
    private String name;
    private String email;
    private String subject;
    private String message;
}
