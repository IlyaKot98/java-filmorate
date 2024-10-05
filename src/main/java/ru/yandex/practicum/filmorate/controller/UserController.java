package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private int generatorIdUser = 1;
    private final HashMap<Integer, User> users = new HashMap();

    @GetMapping
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @PostMapping
    public User create (@Valid @RequestBody User user) {
        log.info("Начало создания клиента");
        if (!user.getEmail().contains("@") && user.getEmail().isEmpty()) {
            log.error("Клиент прислал неверную электронную почту");
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @!");
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            log.error("Логин клиента пустой или содержит пробелы");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы!");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            log.warn("Клиент не имеет имени");
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Некорректная дата рождения клиента");
            throw new ValidationException("Дата рождения не может быть в будущем!");
        }
        log.info("Добавляем клиента в мапу");
        user.setId(generatorIdUser);
        users.put(generatorIdUser, user);
        generatorIdUser++;
        return user;
    }

    @PutMapping
    public User update (@Valid @RequestBody User newUser) {
        log.info("Началось обновление данных клиента");
        if (!newUser.getEmail().contains("@") && newUser.getEmail().isEmpty()) {
            log.error("Клиент прислал неверную электронную почту");
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @!");
        }
        if (newUser.getLogin().isEmpty() || newUser.getLogin().contains(" ")) {
            log.error("Логин клиента пустой или содержит пробелы");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы!");
        }
        if (newUser.getName().isEmpty()) {
            log.warn("Клиент не имеет имени");
            newUser.setName(newUser.getLogin());
        }
        if (newUser.getBirthday().isAfter(LocalDate.now())) {
            log.error("Некорректная дата рождения клиента");
            throw new ValidationException("Дата рождения не может быть в будущем!");
        }

        if (users.containsKey(newUser.getId())) {
            users.put(newUser.getId(), newUser);
            return newUser;
        }
        log.warn("Клиент с id = {} не найден", newUser.getId());
        throw new ValidationException("Клиент с id = " + newUser.getId() + " не найден!");
    }

}
