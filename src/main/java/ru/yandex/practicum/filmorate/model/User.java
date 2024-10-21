package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    @Email (message = "Электронная почта должна содержать символ @!")
    @NotBlank (message = "Электронная почта не может быть пустой!")
    private String email;
    @NotBlank (message = "Логин не может быть пустым!")
    private String login;
    private String name;
    @NotBlank (message = "Дата рождения не может быть пустой!")
    @Future (message = "Дата рождения не может быть в будущем!")
    private LocalDate birthday;
}
