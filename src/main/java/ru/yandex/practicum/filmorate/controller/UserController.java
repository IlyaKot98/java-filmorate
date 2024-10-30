package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private int generatorUserId = 1;
    private final HashMap<Integer, User> users = new HashMap();

    @GetMapping
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Начало создания клиента");
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        log.info("Добавляем клиента в мапу");
        user.setId(generatorUserId);
        users.put(generatorUserId, user);
        generatorUserId++;
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        log.info("Началось обновление данных клиента");
        if (newUser.getName().isEmpty()) {
            log.warn("Клиент не имеет имени");
            newUser.setName(newUser.getLogin());
        }
        if (users.containsKey(newUser.getId())) {
            users.put(newUser.getId(), newUser);
            return newUser;
        }
        log.warn("Клиент с id = {} не найден", newUser.getId());
        throw new ValidationException("Клиент с id = " + newUser.getId() + " не найден!");
    }
}