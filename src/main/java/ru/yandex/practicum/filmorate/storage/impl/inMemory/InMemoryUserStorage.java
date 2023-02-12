package ru.yandex.practicum.filmorate.storage.impl.inMemory;

import lombok.extern.slf4j.Slf4j;

import ru.yandex.practicum.filmorate.exeptions.SearchException;
import ru.yandex.practicum.filmorate.model.FriendshipStatus;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validators.UserValidator;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final UserValidator validator = new UserValidator();
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    @Override
    public List<User> getUsersList() {

        return new ArrayList<>(users.values());
    }

    public void addFriends(int id, int friendId) {
        if (getUsers().containsKey(id) || getUsers().containsKey(friendId)) {
            if (getUser(friendId).getFriendshipStatuses().containsKey(id) &&
                    getUser(id).getFriendshipStatuses().containsKey(friendId)) {
                if (getUser(friendId).getFriendshipStatuses().get(id).getName().equals("NOT_CONFIRMED") &&
                        getUser(id).getFriendshipStatuses().get(friendId).getName().equals("NOT_CONFIRMED")) {
                    getUser(id).getFriendshipStatuses().put(friendId, new FriendshipStatus(getUser(friendId).getId(), 1,  "CONFIRMED"));
                    getUser(friendId).getFriendshipStatuses().put(id, new FriendshipStatus(getUser(id).getId(),1, "CONFIRMED"));
                    log.info("Польватели " + getUser(id).getName() + " и " +
                            getUser(friendId).getName() + " добавились в друзья");
                }
            } else {
                getUser(id).getFriendshipStatuses().put(friendId, new FriendshipStatus(getUser(friendId).getId(),2, "NOT_CONFIRMED"));
                getUser(friendId).getFriendshipStatuses().put(id, new FriendshipStatus(getUser(id).getId(),2, "NOT_CONFIRMED"));
                log.info("Польватель " + getUser(id).getId() + " отправиль заявку в друзья пользователю " +
                        getUser(friendId).getId());
            }
        } else {
            log.warn("error : по id " + id + "/" + friendId + " пользователь не найден.");
            throw new SearchException("error : по id " + id + "/" + friendId + " пользователь не найден.");
        }
    }
     @Override
    public List<User> getFriends(int id) {
        if(getUsers().containsKey(id)) {
            ArrayList<User> userFriends = new ArrayList<>();
            for (Integer userId : getUser(id).getFriendshipStatuses().keySet()) {
                if(getUsers().containsKey(userId)) {
                    userFriends.add(getUser(userId));
                }
            }
            log.info("Получен запрос на получение друзей по id" + id);
            return userFriends;
        }else {
            log.warn("error : По данному id: " +id+ "пользователь не найден");
            throw new SearchException("По данному id: " +id+ "пользователь не найден");
        }
    }
    @Override
    public void deleteFriends(int id, int friendId) {
        if(getUsers().containsKey(id) || getUsers().containsKey(friendId)) {
            if (getUser(friendId).getFriendshipStatuses().containsKey(id) &&
                    getUser(id).getFriendshipStatuses().containsKey(friendId)) {
                getUser(id).getFriendshipStatuses().remove(friendId);
               getUser(friendId).getFriendshipStatuses().remove(friendId);
                log.info("Польватели " + getUser(id).getId() + " и "
                        + getUser(friendId).getId() + " удалились из друзей");
            } else {
                log.warn("Польватели " + getUser(id).getId() + " и "
                        + getUser(friendId).getId() + " не смогли удалиться друг у друга. " +
                        "Возможно они не состояли в дружбе");
                throw new SearchException("Error: Ошибка удаления из друзей");
            }
        } else {
            log.warn("Польватели " + getUser(id).getId() + " и "
                    + getUser(friendId).getId() + " не найдены." +
                    "Возможно они не состояли в дружбе");
            throw new SearchException("Error: Ошибка удаления из друзей");
        }
    }
    @Override
    public List<User> getCommonFriends(int id, int otherId) {
        if(getUsers().containsKey(id) || getUsers().containsKey(otherId)) {
            log.info("Получен запрос на список общих друзей");
            ArrayList<User> friendsList = new ArrayList<>();
            for(Integer friendIdFirst : getUser(id).getFriendshipStatuses().keySet()) {
                for(Integer friendIdSecond : getUser(otherId).getFriendshipStatuses().keySet()) {
                    if(friendIdFirst.equals(friendIdSecond)) {
                        friendsList.add(getUser(friendIdFirst));
                    }
                }
            }
            return friendsList;
        } else {
            throw new SearchException("По данным id пользователи не найдены");
        }
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
    public User saveUser(User user) {
        if (validator.validate(user)) {
            user.setId(idGenerator());
            users.put(user.getId(), user);
            log.info("Пользователь сохранен под id " + user.getId());
        } else {
            log.warn("Ошибка валидации.");
            throw new ValidationException("Ошибка валидации.");
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (validator.validate(user)) {
            if (users.containsKey(user.getId())) {
                users.replace(user.getId(), user);
                log.info("Информация о пользователе изменена.");
                return user;
            } else {
                log.warn("Пользователь " + user.getId() + " не найден.");
                throw new SearchException("Пользователь " + user.getId() + " не найден.");
            }
        } else {
            throw new ValidationException("Ошибка валидации.");
        }
    }
    public int idGenerator() {
        log.info("Пользователю присвоен id" + id + 1);
        return id++;
    }

     @Override
     public Map<Integer, User> getUsers() {
       return users;
    }

}
