package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface UserStorage {
    final Map<Integer, User> users = new HashMap<>();

    ArrayList<User> getUsers();
    void saveFilm(User user);
    void updateFilm(User user);
    int idGenerator();
}
