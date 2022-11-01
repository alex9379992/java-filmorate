package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("{id}/friends/{friendId}")
    public void addFriends(@PathVariable int id, @PathVariable int friendId) {
        userService.addFriends(id, friendId);

    }

    @DeleteMapping("{id}/friends/{friendId}")
    public void deleteFriends(@PathVariable int id, @PathVariable int friendId) {
        userService.deleteFriends(id, friendId);
    }

    @GetMapping("{id}/friends")
    public ArrayList<User> getFriends(@PathVariable int id){
        return userService.getFriends(id);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public ArrayList<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.getCommonFriends(id, otherId);
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable int id){
        return userService.getUser(id);
    }
    @GetMapping
    public ArrayList<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public User saveUser(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }
}