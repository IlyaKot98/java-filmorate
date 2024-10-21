package ru.yandex.practicum.filmorate.customValidate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class BirthdayLetterValidator implements ConstraintValidator<BirthdayLetter, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value.isBefore(LocalDate.of(1895,12,28))) {
            return false;
        }
        return true;
    }
}