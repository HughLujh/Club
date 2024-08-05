package app.blog.model.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class GenericException extends RuntimeException{
    private String generalMessage;
    private HttpStatus httpStatus;
    private String specificCause;


    public GenericException(String message, String generalMessage, HttpStatus httpStatus, String specificCause) {
        super(message);
        this.generalMessage = generalMessage;
        this.httpStatus = httpStatus;
        this.specificCause = specificCause;
    }
}
