package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;
import java.util.ArrayList;
import java.util.Map;

public interface UserStorage {

    User getUser(int id);
    ArrayList<User> getUsersList();
    void saveUser(User user);
    void updateUser(User user);
    int idGenerator();
    Map<Integer, User> getUsers();
}
