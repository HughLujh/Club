package app.blog.model.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInfoRequest {
    @NotNull(message="ID must not be null.")
    private Long id;

    @NotBlank(message="Email must not be blank.")
    @Size(max = 50)
    @Email(message = "Invalid email address.")
    private String email;

    @NotBlank(message="Username must not be blank.")
    @Size(min = 6, max = 50)
    private String username;

    @NotBlank(message="Image URL must not be blank.")
    private String imageUrl;
}
