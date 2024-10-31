package ru.yandex.practicum.filmorate.customValidate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BirthdayLetterValidator.class)
@Documented
public @interface BirthdayLetter {
    String message() default "{BirthdayLetter.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}