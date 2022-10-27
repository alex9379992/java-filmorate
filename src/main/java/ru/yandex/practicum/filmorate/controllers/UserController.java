package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.ArrayList;
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final InMemoryUserStorage userStorage = new InMemoryUserStorage();

    @GetMapping
    public ArrayList<User> getUsers() {
        return userStorage.getUsers();
    }

    @PostMapping
    public User saveFilm(@Valid @RequestBody User user) {
        userStorage.saveFilm(user);
        return user;
    }


    @PutMapping
    public User updateFilm(@Valid @RequestBody User user) {
        userStorage.updateFilm(user);
        return user;
    }
}
