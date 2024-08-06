package app.blog.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordUpperCaseValidator.class)
public @interface UpperCase {
    String message() default "Password must contain at least one uppercase letter";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
