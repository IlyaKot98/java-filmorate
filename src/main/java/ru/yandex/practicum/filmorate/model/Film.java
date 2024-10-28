package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Film.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private Integer id;
    @NotBlank
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
}