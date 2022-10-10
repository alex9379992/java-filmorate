package ru.yandex.practicum.filmorate.controllers;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserValidator;
import ru.yandex.practicum.filmorate.validators.exeptions.ValidationException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private final UserValidator validator = new UserValidator();
    private int id = 1;

    @GetMapping
    public Map<Integer, User> getUsers() {
        return users;
    }

    @PostMapping
    public User saveFilm(@RequestBody User user) {
        try{
            if(validator.validate(user)) {
                user.setId(idGenerator());
                users.put(user.getId(), user);
            } else {
                throw new ValidationException("Ошибка валидации.");
            }
        }catch (ValidationException | NullPointerException exception) {
            System.out.println(exception.getMessage());
        }
        return user;
    }


    @PutMapping
    public User updateFilm(@RequestBody User user) {
        try {
            if(validator.validate(user)) {
                if(users.containsKey(user.getId())) {
                    users.replace(user.getId(), user);
                } else {
                    users.put(user.getId(), user);
                }
            } else {
                throw new ValidationException("Ошибка валидации.");
            }
        }catch (ValidationException | NullPointerException exception) {
            System.out.println(exception.getMessage());
        }
        return user;
    }
    private int idGenerator() {
        return id++;
    }
}
