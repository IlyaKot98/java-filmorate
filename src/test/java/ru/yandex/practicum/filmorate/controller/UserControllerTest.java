package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private UserController userController = new UserController();
    private User user = new User(null, "test@test.test", "LsdEESqRKu",
            "Mr. Lynn Johnston", LocalDate.parse("1966-03-27"));
    private User userTest;

    @BeforeEach
    void setUp() {
        userController.create(user);
        userTest = new User(null, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
    }

    @Test
    void shouldGetAllUser() {
        Collection<User> allUser = userController.getAllUsers();

        assertFalse(allUser.isEmpty(), "В списке отсутствуют клиенты!");
        assertTrue(allUser.contains(user), "В списке отсутствует тестовый клиент!");
    }

    @Test
    void shouldCreateUserWithNameIsEmpty() {
        userTest.setName("");
        User userCreate = userController.create(userTest);

        assertNotNull(userCreate, "Клиент не создан!");
        assertEquals(userCreate.getLogin(), user.getLogin(), "Логин не совпадает!");
        assertEquals(userCreate.getEmail(), user.getEmail(), "Email не совпадает!");
        assertEquals(userCreate.getBirthday(), user.getBirthday(), "Дата рождения не совпадает!");
        assertEquals(userCreate.getName(), userCreate.getLogin(), "Логин и имя не совпадают!");
    }

    @Test
    void shouldCreateUser() {
        User userCreate = userController.create(userTest);

        assertNotNull(userCreate, "Клиент не создан!");
        assertEquals(userCreate.getName(), user.getName(), "Имя не совпадает!");
        assertEquals(userCreate.getLogin(), user.getLogin(), "Логин не совпадает!");
        assertEquals(userCreate.getEmail(), user.getEmail(), "Email не совпадает!");
        assertEquals(userCreate.getBirthday(), user.getBirthday(), "Дата рождения не совпадает!");
    }

    @Test
    void shouldNotCreateUserWithEmailIsEmpty() {
        userTest.setEmail("");

        ValidationException exception = assertThrows(ValidationException.class, () -> userController.create(userTest),
                "Клиент с пустым email создан, ошибка не была получена!");
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @!",
                exception.getMessage(), "Ошибка создания клиента без email не совпадает с ожидаемой!");
    }

    @Test
    void shouldNotCreateUserWithLoginIsEmpty() {
        userTest.setLogin("");

        ValidationException exception = assertThrows(ValidationException.class, () -> userController.create(userTest),
                "Клиент с пустым login создан, ошибка не была получена!");
        assertEquals("Логин не может быть пустым и содержать пробелы!", exception.getMessage(),
                "Ошибка создания клиента без login не совпадает с ожидаемой!");
    }

    @Test
    void shouldNotCreateUserWithIncorrectEmail() {
        userTest.setEmail("Test");

        ValidationException exception = assertThrows(ValidationException.class, () -> userController.create(userTest),
                "Клиент с некорректным email создан, ошибка не была получена!");
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @!",
                exception.getMessage(), "Ошибка создания клиента с некорректным email не совпадает с ожидаемой!");
    }

    @Test
    void shouldNotCreateUserWithBirthdayInFuture() {
        userTest.setBirthday(LocalDate.parse("2446-08-20"));

        ValidationException exception = assertThrows(ValidationException.class, () -> userController.create(userTest),
                "Клиент с некорректной датой рождения создан, ошибка не была получена!");
        assertEquals("Дата рождения не может быть в будущем или пустой!", exception.getMessage(),
                "Ошибка создания клиента с некорректной датой рождения не совпадает с ожидаемой!");
    }

    @Test
    void shouldUpdateUser() {
        userTest.setId(user.getId());
        userTest.setEmail("mail@yandex.ru");
        userTest.setLogin("doloreUpdate");
        userTest.setName("est adipisicing");
        userTest.setBirthday(LocalDate.parse("1976-09-20"));

        User userUpdate = userController.update(userTest);

        assertNotNull(userUpdate, "Клиент не обновлен!");
        assertNotEquals(userUpdate.getLogin(), user.getLogin(), "Логин совпадает!");
        assertNotEquals(userUpdate.getEmail(), user.getEmail(), "Email совпадает!");
        assertNotEquals(userUpdate.getBirthday(), user.getBirthday(), "Дата рождения совпадает!");
        assertNotEquals(userUpdate.getName(), user.getName(), "Имя совпадают!");
    }

    @Test
    void shouldUpdateUserWithNameIsEmpty() {
        userTest.setId(user.getId());
        userTest.setName("");

        User userUpdate = userController.update(userTest);

        assertNotNull(userUpdate, "Клиент не обновлен!");
        assertEquals(userUpdate.getLogin(), user.getLogin(), "Логин не совпадает!");
        assertEquals(userUpdate.getEmail(), user.getEmail(), "Email не совпадает!");
        assertEquals(userUpdate.getBirthday(), user.getBirthday(), "Дата рождения не совпадает!");
        assertEquals(userUpdate.getName(), userUpdate.getLogin(), "Логин и имя не совпадают!");
    }

    @Test
    void shouldNotUpdateUserWithEmailIsEmpty() {
        userTest.setEmail("");

        ValidationException exception = assertThrows(ValidationException.class,
                () -> userController.update(userTest), "Обновление было выполнено, с пустым Email!");
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @!",
                exception.getMessage(), "Ошибка обновлениея клиента с пустым email, не соответствует ожидаемой!");
    }

    @Test
    void shouldNotUpdateUserWithLoginIsEmpty() {
        userTest.setLogin("");

        ValidationException exception = assertThrows(ValidationException.class,
                () -> userController.update(userTest), "Обновление было выполнено, с пустым Login!");
        assertEquals("Логин не может быть пустым и содержать пробелы!", exception.getMessage(),
                "Ошибка обновления клиента с пустым login, не соответствует ожидаемой!");
    }

    @Test
    void shouldNotUpdateUserWithIncorrectEmail() {
        userTest.setEmail("Test");

        ValidationException exception = assertThrows(ValidationException.class, () -> userController.update(userTest),
                "Клиент с некорректным email обновлен, ошибка не была получена!");
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @!",
                exception.getMessage(), "Ошибка обновления клиента с некорректным email, не совпадает с ожидаемой!");
    }

    @Test
    void shouldNotUpdateUserWithBirthdayInFuture() {
        userTest.setBirthday(LocalDate.parse("2446-08-20"));

        ValidationException exception = assertThrows(ValidationException.class, () -> userController.update(userTest),
                "Клиент с некорректной датой рождения обновлен, ошибка не была получена!");
        assertEquals("Дата рождения не может быть в будущем!", exception.getMessage(),
                "Ошибка обновления клиента с некорректной датой рождения, не совпадает с ожидаемой!");
    }
}