package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserValidator;
import ru.yandex.practicum.filmorate.validators.exeptions.ValidationException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private final UserValidator validator = new UserValidator();
    private int id = 1;

    @GetMapping
    public Map<Integer, User> getUsers() {
        log.info("Получен запрос на список поьзователей.");
        return users;
    }

    @PostMapping
    public User saveFilm(@Valid @RequestBody User user) {
        if (validator.validate(user)) {
            user.setId(idGenerator());
            users.put(user.getId(), user);
            log.info("Пользователь сохранен.");
        } else {
            log.warn("Ошибка валидации.");
            throw new ValidationException("Ошибка валидации.");
        }
        return user;
    }


    @PutMapping
    public User updateFilm(@Valid @RequestBody User user) {
        if (validator.validate(user)) {
            if (users.containsKey(user.getId())) {
                users.replace(user.getId(), user);
                log.info("Информация о пользователе изменена.");
            } else {
                log.warn("Ошибка валидации.");
                throw new ValidationException("Ошибка валидации.");
            }
        } else {
            throw new ValidationException("Ошибка валидации.");
        }
        return user;
    }

    private int idGenerator() {
        return id++;
    }
}
