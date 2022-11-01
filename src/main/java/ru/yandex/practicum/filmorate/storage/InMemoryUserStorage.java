package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exeptions.SearchException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserValidator;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;

import java.util.ArrayList;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage{

    private final UserValidator validator = new UserValidator();
    private int id = 1;

    @Override
    public ArrayList<User> getUsersList() {

        return new ArrayList<>(users.values());
    }

    @Override
    public User getUser(int id) {
       if(users.containsKey(id)) {
           return users.get(id);
       } else {
           log.warn("Ошибка поиска пользователя: не найден под id " + id);
          throw new SearchException("Пользователь с id " + id + " не найден");
       }
    }

    @Override
    public void saveUser(User user) {
        if (validator.validate(user)) {
            user.setId(idGenerator());
            users.put(user.getId(), user);
            log.info("Пользователь сохранен под id " + user.getId());
        } else {
            log.warn("Ошибка валидации.");
            throw new ValidationException("Ошибка валидации.");
        }
    }

    @Override
    public void updateUser(User user) {
        if (validator.validate(user)) {
            if (users.containsKey(user.getId())) {
                users.replace(user.getId(), user);
                log.info("Информация о пользователе изменена.");
            } else {
                log.warn("Пользователь " + user.getId() + " не найден.");
                throw new SearchException("Пользователь " + user.getId() + " не найден.");
            }
        } else {
            throw new ValidationException("Ошибка валидации.");
        }
    }

    @Override
    public int idGenerator() {
        return id++;
    }

    @Override
    public Map<Integer, User> getUsers() {
       return users;
    }

}
