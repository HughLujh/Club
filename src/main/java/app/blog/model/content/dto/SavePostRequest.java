package app.blog.model.content.dto;

import app.blog.annotation.UpperCase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
public class SavePostRequest {
    @NotBlank(message= "This field should not be empty.")
    private String title;

    @NotBlank(message= "This field should not be empty.")
    private String summary;

    @NotBlank(message= "This field should not be empty.")
    private String content;

    @NotBlank(message= "This field should not be empty.")
    private String tags;
}
