package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private int generatorFilmId = 1;

    @GetMapping
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Начало создания фильма");
        log.info("Добавляем фильм в мапу");
        film.setId(generatorFilmId);
        films.put(generatorFilmId, film);
        generatorFilmId++;
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        log.info("Началось обновление данных фильма");
        if (films.containsKey(newFilm.getId())) {
            log.info("Обновляем фильм в мапе");
            films.put(newFilm.getId(), newFilm);
            return newFilm;
        }
        log.warn("Фильм с id = {} не найден", newFilm.getId());
        throw new ValidationException("Фильм с id = " + newFilm.getId() + " не найден!");
    }
}
