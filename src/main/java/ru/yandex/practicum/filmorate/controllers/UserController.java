package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.SearchException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserStorage userStorage;
    private final UserService userService;
    @Autowired
    public UserController(InMemoryUserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }
    @PutMapping("{id}/friends/{friendId}")
    public void addFriends(@PathVariable int id,
                           @PathVariable int friendId) {
        if(userStorage.getUsers().containsKey(id) || userStorage.getUsers().containsKey(friendId)) {
            userService.addFriends(userStorage.getUser(id), userStorage.getUser(friendId));
        } else {
            log.warn("error : по id " + id + "/" + friendId + " пользователь не найден." );
            throw new SearchException("error : по id " + id + "/" + friendId + " пользователь не найден.");
        }

    }

    @DeleteMapping("{id}/friends/{friendId}")
    public void deleteFriends(@PathVariable int id,
                              @PathVariable int friendId) {
            userService.deleteFriends(userStorage.getUser(id), userStorage.getUser(friendId));
    }

    @GetMapping("{id}/friends")
    public ArrayList<User> getFriends(@PathVariable int id){
        if(userStorage.getUsers().containsKey(id)) {
            return  userService.getFriends(userStorage.getUser(id), userStorage.getUsers());
        }else {
            throw new SearchException("По данному id пользователь не найден");
        }
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public ArrayList<User> getCommonFriends(@PathVariable int id,
                                            @PathVariable int otherId) {
        if(userStorage.getUsers().containsKey(id) || userStorage.getUsers().containsKey(otherId)) {
            return userService.getCommonFriends(userStorage.getUser(id), userStorage.getUser(otherId),
                    userStorage.getUsers());
        } else {
            throw new SearchException("По данным id пользователи не найдены");
        }
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable int id){
        return userStorage.getUser(id);
    }
    @GetMapping
    public ArrayList<User> getUsers() {
        return userStorage.getUsersList();
    }

    @PostMapping
    public User saveUser(@Valid @RequestBody User user) {
        userStorage.saveUser(user);
        return user;
    }

    @PutMapping
    public User updateFilm(@Valid @RequestBody User user) {
        userStorage.updateFilm(user);
        return user;
    }
}
