package app.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse extends GenericResponse {
    private Map errors;

    public ErrorResponse(String message, Map errors) {
        super(message);
        this.errors = errors;
    }
}
