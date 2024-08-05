package app.blog.model.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateObjectExceptions extends GenericException{
    public DuplicateObjectExceptions(String message, String generalMessage, HttpStatus httpStatus, String specificCause) {
        super(message, generalMessage, httpStatus, specificCause);
    }
}
