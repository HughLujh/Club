package app.blog.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


public class PasswordUpperCaseValidator implements ConstraintValidator<UpperCase, String> {

    @Override
    public void initialize(UpperCase constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !value.isEmpty() && containsUppercase(value);
    }

    private boolean containsUppercase(String value) {
        for (char c : value.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }
}
