package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;
    private final UserValidator validator = new UserValidator();

    public UserService(@Qualifier("UserDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriends(int id, int friendId) {
        log.info("Получен запрос на добавления в друзья " + id +  " и " + friendId);
        userStorage.addFriends(id, friendId);
    }

    public void deleteFriends(int id, int friendId) {
        log.info("Получен запрос на удаление из друзей " + id +  " и " + friendId);
         userStorage.deleteFriends(id, friendId);
    }

    public List<User> getFriends(int id) {
        log.info("Получен запрос на список поьзователей от id: " + id);
        return userStorage.getFriends(id);
    }
    
    public List<User> getCommonFriends(int id, int otherId) {
        log.info("Получен запрос на список общих друзей между пользователями " + id + " и " + otherId);
        return userStorage.getCommonFriends(id, otherId);
    }
    public User getUser(int id){
        log.info("Подучен запрос на пользователя " + id);
        return userStorage.getUser(id);
    }

    public List<User> getUsers() {
        log.info("Получен запрос на список поьзователей.");
        return userStorage.getUsersList();
    }

    public User saveUser(User user) {
        log.info("Получен запрос на сохранение пользователя.");
        if (validator.validate(user)) {
            return userStorage.saveUser(user);
        } else {
            log.warn("Ошибка валидации.");
            throw new ValidationException("Ошибка валидации.");
        }
    }

    public User updateUser(User user) {
        log.info("Получен запрос на изменение информации о пользователе под id" + user.getId());
        if (validator.validate(user)) {
            return userStorage.updateUser(user);
        } else {
            log.warn("Ошибка валидации.");
            throw new ValidationException("Ошибка валидации.");
        }
    }
}
