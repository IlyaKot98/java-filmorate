package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
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
    void shouldNotCreateFilmWithNameIsEmpty() {
        filmTest.setName("");

        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.create(filmTest),
                "Фильм с пустым названием создан, ошибка не была получена!");
        assertEquals("Название не может быть пустым!", exception.getMessage(),
                "Ошибка создания фильма без наименования, не совпадает с ожидаемым!");
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
    void shouldNotCreateFilmWithDescription201Char() {
        filmTest.setDescription("JDvFXu2yi34sJREY3Ts8WNysjxGPoACjSLzBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjxGPoACj" +
                "SLzBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjxGPoACjSLzBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjxGPoAC" +
                "jSLzBhu8Unqg0oHcHbi3");

        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.create(filmTest),
                "Фильм с описанием в 201 символ создан, ошибка не была получена!");
        assertEquals("Максимальная длина описания — 200 символов!", exception.getMessage(),
                "Ошибка создания фильма с описанием в 201 символ, не совпадает с ожидаемым!");
    }

    @Test
    void shouldNotCreateFilmWithMaxDescription250Char() {
        filmTest.setDescription("JDvFXu2yi34sJREY3Ts8WNysjxGPoACjSLzBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjxGPoACjS" +
                "LzBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjxGPoACjSLzBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjxGPoACjSL" +
                "zBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjxGPoACjSLzBhu8Unqg0oHcHbi");

        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.create(filmTest),
                "Фильм с описанием в 250 символов создан, ошибка не была получена!");
        assertEquals("Максимальная длина описания — 200 символов!", exception.getMessage(),
                "Ошибка создания фильма с описанием в 250 символов, не совпадает с ожидаемым!");
    }

    @Test
    void shouldNotCreateFilmWithRealeaseDateBefore28December1895() {
        filmTest.setReleaseDate(LocalDate.parse("1800-06-16"));

        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.create(filmTest),
                "Фильм с датой релиза ранее 28 декабря 1895 года создан, ошибка не была получена!");
        assertEquals("Дата релиза, должна быть не раньше 28 декабря 1895 года!", exception.getMessage(),
                "Ошибка создания фильма с датой релиза ранее 28 декабря 1895 года, не совпадает с ожидаемым!");
    }

    @Test
    void shouldNotCreateFilmWithDurationIsLessZero() {
        filmTest.setDuration(-1);

        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.create(filmTest),
                "Фильм с продолжительностью меньше 0 создан, ошибка не была получена!");
        assertEquals("Продолжительность фильма должна быть положительным числом!", exception.getMessage(),
                "Ошибка создания фильма продолжительностью меньше 0, не совпадает с ожидаемым!");
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
    void shouldNotUpdateFilmWithNameIsEmpty() {
        filmTest.setId(film.getId());
        filmTest.setName("");

        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.update(filmTest),
                "Фильм с пустым названием обновлен, ошибка не была получена!");
        assertEquals("Название не может быть пустым!", exception.getMessage(),
                "Ошибка обновления фильма без наименования, не совпадает с ожидаемым!");
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

    @Test
    void shouldUpdateFilmWithDescription201Char() {
        filmTest.setDescription("JDvFXu2yi34sJREY3Ts8WNysjxGPoACjSLzBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjxGPoACj" +
                "SLzBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjxGPoACjSLzBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjxGPoAC" +
                "jSLzBhu8Unqg0oHcHbi3");

        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.update(filmTest),
                "Фильм с описанием в 201 символ обновлен, ошибка не была получена!");
        assertEquals("Максимальная длина описания — 200 символов!", exception.getMessage(),
                "Ошибка обновления фильма с описанием в 201 символ, не совпадает с ожидаемым!");
    }

    @Test
    void shouldUpdateFilmWithMaxDescription250Char() {
        filmTest.setId(film.getId());
        filmTest.setDescription("JDvFXu2yi34sJREY3Ts8WNysjxGPoACjSLzBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjxGPoACjS" +
                "LzBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjxGPoACjSLzBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjxGPoACjSL" +
                "zBhu8Unqg0oHcHbiJDvFXu2yi34sJREY3Ts8WNysjxGPoACjSLzBhu8Unqg0oHcHbi");

        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.update(filmTest),
                "Фильм с описанием в 250 символов обновлен, ошибка не была получена!");
        assertEquals("Максимальная длина описания — 200 символов!", exception.getMessage(),
                "Ошибка обновления фильма с описанием в 250 символов, не совпадает с ожидаемым!");

    }

    @Test
    void shouldNotUpdateFilmWithRealeaseDateBefore28December1895() {
        filmTest.setId(film.getId());
        filmTest.setReleaseDate(LocalDate.parse("1800-06-16"));

        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.update(filmTest),
                "Фильм с датой релиза ранее 28 декабря 1895 года обновлен, ошибка не была получена!");
        assertEquals("Дата релиза, должна быть не раньше 28 декабря 1895 года!", exception.getMessage(),
                "Ошибка обновления фильма с датой релиза ранее 28 декабря 1895 года, не совпадает с ожидаемым!");
    }

    @Test
    void shouldNotUpdateFilmWithDurationIsLessZero() {
        filmTest.setId(film.getId());
        filmTest.setDuration(-1);

        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.update(filmTest),
                "Фильм с продолжительностью меньше 0 обновлен, ошибка не была получена!");
        assertEquals("Продолжительность фильма должна быть положительным числом!", exception.getMessage(),
                "Ошибка обновления фильма продолжительностью меньше 0, не совпадает с ожидаемым!");
    }

}