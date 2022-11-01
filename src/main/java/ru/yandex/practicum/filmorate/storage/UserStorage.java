package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface UserStorage {
    Map<Integer, User> users = new HashMap<>();

    User getUser(int id);
    ArrayList<User> getUsersList();
    void saveUser(User user);
    void updateFilm(User user);
    int idGenerator();
    Map<Integer, User> getUsers();
}
