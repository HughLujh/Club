package app.blog.config;

import app.blog.model.ErrorResponse;
import app.blog.model.exceptions.GenericException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(org.springframework.web.bind.annotation.ExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach( fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        ErrorResponse errorResponse = new ErrorResponse(errorMap);
        errorResponse.setMessage("invalid request");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse>handleClientErrorException(HttpClientErrorException exception){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(exception.getCause().toString(),exception.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(errorMap);
        errorResponse.setMessage("http client error");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<Object> generalException(GenericException generalException){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(generalException.getSpecificCause(), generalException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(generalException.getGeneralMessage(),errorMap);
        return new ResponseEntity<>(errorResponse,generalException.getHttpStatus());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, String>> handleBindException(BindException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
