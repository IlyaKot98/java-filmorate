package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    HashMap<Integer, Film> films = new HashMap<>();
    private int generatorIdFilm = 1;

    @GetMapping
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    @PostMapping
    public Film create (@Valid @RequestBody Film film) {
        log.info("Начало создания фильма");
        if (film.getName().isEmpty()) {
            log.error("Фильм не имеет названия");
            throw new ValidationException("Название не может быть пустым!");
        }
        if (film.getDescription().length() > 200) {
            log.error("Фильм имеет длинну описания более 200 символов");
            throw new ValidationException("Максимальная длина описания — 200 символов!");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            log.error("Дата релиза фильма раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза, должна быть не раньше 28 декабря 1895 года!");
        }
        if (film.getDuration() < 0) {
            log.error("Фильм имеет отрицательную продолжительность");
            throw new ValidationException("Продолжительность фильма должна быть положительным числом!");
        }
        log.info("Добавляем фильм в мапу");
        film.setId(generatorIdFilm);
        films.put(generatorIdFilm, film);
        generatorIdFilm++;
        return film;
    }

    @PutMapping
    public Film update (@Valid @RequestBody Film newFilm) {
        log.info("Началось обновление данных фильма");
        if (newFilm.getName() == null || newFilm.getName().isEmpty()) {
            log.error("Фильм не имеет названия");
            throw new ValidationException("Название не может быть пустым!");
        }
        if (newFilm.getDescription().length() > 200) {
            log.error("Фильм имеет длинну описания более 200 символов");
            throw new ValidationException("Максимальная длина описания — 200 символов!");
        }
        if (newFilm.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            log.error("Дата релиза фильма раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза, должна быть не раньше 28 декабря 1895 года!");
        }
        if (newFilm.getDuration() < 0) {
            log.error("Фильм имеет отрицательную продолжительность");
            throw new ValidationException("Продолжительность фильма должна быть положительным числом!");
        }

        if (films.containsKey(newFilm.getId())) {
            log.info("Обновляем фильм в мапе");
            films.put(newFilm.getId(), newFilm);
            return newFilm;
        }
        log.warn("Фильм с id = {} не найден", newFilm.getId());
        throw new ValidationException("Фильм с id = " + newFilm.getId() + " не найден!");
    }
}
