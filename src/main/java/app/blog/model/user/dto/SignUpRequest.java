package app.blog.model.user.dto;

import app.blog.annotation.UpperCase;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank(message="This field should not be empty.")
    @Size(max = 50)
    @Email(message = "Invalid email address.")
    private String email;


    @NotBlank(message="This field should not be empty.")
    @Size(min = 6, max = 50)
    private String username;


    @NotBlank(message= "This field should not be empty.")
    @UpperCase(message = "Password should have minimal 8 alphanumeric character and at leash 1 upper letter.")
    @Size(min = 9, max = 120, message = "Password should have minimal 8 alphanumeric character and at leash 1 upper letter.")
    private String password;
}
