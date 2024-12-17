package app.blog.model.user.dto;

import app.blog.annotation.UpperCase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class SignInRequest {

    @NotBlank(message="This field should not be empty.")
    @Size(max = 50)
    @Email(message = "Invalid email address.")
    String email;

    @NotBlank(message= "This field should not be empty.")
    @UpperCase(message = "Password should have minimal 8 alphanumeric character and at leash 1 upper letter.")
    @Size(min = 9, max = 120, message = "Password should have minimal 8 alphanumeric character, at leash 1 upper letter.")
    String password;
}
