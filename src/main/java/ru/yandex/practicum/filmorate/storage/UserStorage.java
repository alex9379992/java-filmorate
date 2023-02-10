package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;

public interface UserStorage {

    User getUser(int id);
    List<User> getUsersList();
    User saveUser(User user);
    User updateUser(User user);
    Map<Integer, User> getUsers();
    void addFriends(int id, int friendId);
    List<User> getFriends(int id);
    void deleteFriends(int id, int friendId);
    List<User> getCommonFriends(int id, int otherId);
}
