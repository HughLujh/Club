package app.blog.model.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends GenericException {
    public BadRequestException(String message, String generalMessage, HttpStatus httpStatus, String specificCause) {
        super(message, generalMessage, httpStatus, specificCause);
    }
}
