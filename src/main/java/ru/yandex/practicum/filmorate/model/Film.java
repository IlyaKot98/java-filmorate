package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.customValidate.BirthdayLetter;

import java.time.LocalDate;

/**
 * Film.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private Integer id;
    @NotBlank (message = "Название не может быть пустым!")
    private String name;
    @Size (max = 200, message = "Максимальная длина описания — 200 символов!")
    private String description;
    @BirthdayLetter(message = "Дата релиза, должна быть не раньше 28 декабря 1895 года!")
    private LocalDate releaseDate;
    @Positive (message = "Продолжительность фильма должна быть положительным числом!")
    private int duration;
}
