package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    private FilmController filmController = new FilmController();
    private Film film = new Film(null, "qbA5d8j6HtY9tPp",
            "JDvFXu2yi34sJREY3Ts8WNysjxGPoACjSLzBhu8Unqg0oHcHbi", LocalDate.parse("2003-06-16"),
            132);
    private Film filmTest;

    @BeforeEach
    void setUp() {
        filmController.create(film);
        filmTest = new Film(null, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());
    }

    @Test
    void shouldGetAllFilm() {
        Collection<Film> allFilm = filmController.getAllFilms();

        assertFalse(allFilm.isEmpty(), "В списке отсутствуют фильмы!");
        assertTrue(allFilm.contains(film), "В списке отсутствует тестовый фильм!");
    }

    @Test
    void shouldCreateFilm() {
        Film filmCreate = filmController.create(filmTest);

        assertNotNull(filmCreate, "Фильм не создан!");
        assertEquals(film.getName(), filmCreate.getName(), "Название фильма не совпадает!");
        assertEquals(film.getDescription(), filmCreate.getDescription(), "Описание фильма не совпадает!");
        assertEquals(film.getReleaseDate(), filmCreate.getReleaseDate(), "Дата релиза не совпадает!");
        assertEquals(film.getDuration(), filmCreate.getDuration(),"Продолжительность фильма не совпадает!");
    }

    @Test
    void shouldCreateFilmWithDescription100Char() {
        filmTest.setDescription("JDvFXu2yi34sJREY3Ts8WNysjxGPoACjSLzBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjxGPoACjS" +
                "LzBhu8Unqg0oHcHbi");

        Film filmCreate = filmController.create(filmTest);

        assertNotNull(filmCreate, "Фильм не создан!");
        assertEquals(film.getName(), filmCreate.getName(), "Название фильма не совпадает!");
        assertNotEquals(film.getDescription(), filmCreate.getDescription(), "Описание фильма совпадает с оригиналом!");
        assertEquals(film.getReleaseDate(), filmCreate.getReleaseDate(), "Дата релиза не совпадает!");
        assertEquals(film.getDuration(), filmCreate.getDuration(),"Продолжительность фильма не совпадает!");
    }

    @Test
    void shouldCreateFilmWithDescription200Char() {
        filmTest.setDescription("JDvFXu2yi34sJREY3Ts8WNysjxGPoACjSLzBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjxGPo" +
                "ACjSLzBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjxGPoACjSLzBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjx" +
                "GPoACjSLzBhu8Unqg0oHcHbi");

        Film filmCreate = filmController.create(filmTest);

        assertNotNull(filmCreate, "Фильм не создан!");
        assertEquals(film.getName(), filmCreate.getName(), "Название фильма не совпадает!");
        assertNotEquals(film.getDescription(), filmCreate.getDescription(), "Описание фильма совпадает с оригиналом!");
        assertEquals(film.getReleaseDate(), filmCreate.getReleaseDate(), "Дата релиза не совпадает!");
        assertEquals(film.getDuration(), filmCreate.getDuration(),"Продолжительность фильма не совпадает!");
    }

    @Test
    void shouldUpdateFilm() {
        filmTest.setId(film.getId());
        filmTest.setName("Film Updated");
        filmTest.setDescription("New film update decription");
        filmTest.setReleaseDate(LocalDate.parse("1989-04-17"));
        filmTest.setDuration(190);

        Film filmUpdate = filmController.update(filmTest);

        assertNotNull(filmUpdate, "Фильм не обновлен!");
        assertNotEquals(filmUpdate.getName(), film.getName(), "Название совпадает!");
        assertNotEquals(filmUpdate.getDescription(), film.getDescription(), "Описание совпадает!");
        assertNotEquals(filmUpdate.getReleaseDate(), film.getReleaseDate(), "Дата релиза совпадает!");
        assertNotEquals(filmUpdate.getDuration(), film.getDuration(), "Продолжительность совпадают!");
    }

    @Test
    void shouldUpdateFilmWithDescription100Char() {
        filmTest.setId(film.getId());
        filmTest.setDescription("JDvFXu2yi34sJREY3Ts8WNysjxGPoACjSLzBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjxGPoACjS" +
                "LzBhu8Unqg0oHcHbi");

        Film filmCreate = filmController.update(filmTest);

        assertNotNull(filmCreate, "Фильм не обновлен!");
        assertEquals(film.getName(), filmCreate.getName(), "Название фильма не совпадает!");
        assertNotEquals(film.getDescription(), filmCreate.getDescription(), "Описание фильма не изменилось!");
        assertEquals(film.getReleaseDate(), filmCreate.getReleaseDate(), "Дата релиза не совпадает!");
        assertEquals(film.getDuration(), filmCreate.getDuration(),"Продолжительность фильма не совпадает!");

    }

    @Test
    void shouldUpdateFilmWithDescription200Char() {
        filmTest.setId(film.getId());
        filmTest.setDescription("JDvFXu2yi34sJREY3Ts8WNysjxGPoACjSLzBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjxGPo" +
                "ACjSLzBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjxGPoACjSLzBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjx" +
                "GPoACjSLzBhu8Unqg0oHcHbi");

        Film filmCreate = filmController.update(filmTest);

        assertNotNull(filmCreate, "Фильм не обновлен!");
        assertEquals(film.getName(), filmCreate.getName(), "Название фильма не совпадает!");
        assertNotEquals(film.getDescription(), filmCreate.getDescription(), "Описание фильма не изменилось!");
        assertEquals(film.getReleaseDate(), filmCreate.getReleaseDate(), "Дата релиза не совпадает!");
        assertEquals(film.getDuration(), filmCreate.getDuration(),"Продолжительность фильма не совпадает!");

    }
}