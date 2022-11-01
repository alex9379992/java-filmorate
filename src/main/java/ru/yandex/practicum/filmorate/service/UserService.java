package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.SearchException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriends(int id, int friendId) {
        if(userStorage.getUsers().containsKey(id) || userStorage.getUsers().containsKey(friendId)) {
            boolean isAddUserFirst = userStorage.getUser(id).getFriendsId().add(userStorage.getUser(friendId).getId());
            boolean isAddUserSecond = userStorage.getUser(friendId).getFriendsId().add(userStorage.getUser(id).getId());
            if(isAddUserFirst || isAddUserSecond) {
                log.info("Польватели " + userStorage.getUser(id).getName() + " и " +
                        userStorage.getUser(friendId).getName() + " добавились в друзья");
            } else {
                log.warn("Error : польватели " + userStorage.getUser(id).getName() + " и " +
                        userStorage.getUser(friendId).getName() +
                        " не смогли добавиться в друзья. Возможно они уже состоят в друзьях .");
                throw new SearchException("Error : польватели " +userStorage.getUser(id).getName() + " и "
                        + userStorage.getUser(friendId).getName() +
                        " не смогли добавиться в друзья. Возможно они уже состоят в друзьях .");
            }
        } else {
            log.warn("error : по id " + id + "/" + friendId + " пользователь не найден." );
            throw new SearchException("error : по id " + id + "/" + friendId + " пользователь не найден.");
        }

    }

    public void deleteFriends(int id, int friendId) {
        boolean isDeleteFriendsUserFirst = userStorage.getUser(id).getFriendsId().
                remove(userStorage.getUser(friendId).getId());
        boolean isDeleteFriendsUserSecond = userStorage.getUser(friendId).getFriendsId().
                remove(userStorage.getUser(id).getId());
        if(isDeleteFriendsUserFirst || isDeleteFriendsUserSecond) {
            log.info("Польватели " + userStorage.getUser(id).getName() + " и "
                    + userStorage.getUser(friendId).getName() + " удалились из друзей");
        } else {
            log.warn("Error : польватели " + userStorage.getUser(id).getName() + " и "
                    + userStorage.getUser(friendId).getName() +
                    " не смогли Удалиться длуг у друга");
            throw new SearchException("Error : польватели " + userStorage.getUser(id).getName() + " и "
                    + userStorage.getUser(friendId).getName() +
                    " не смогли Удалиться длуг у друга");
        }
    }

    public ArrayList<User> getFriends(int id) {
        if(userStorage.getUsers().containsKey(id)) {
            ArrayList<User> userFriends = new ArrayList<>();
            for (Integer userId : userStorage.getUser(id).getFriendsId()) {
                if(userStorage.getUsers().containsKey(userId)) {
                    userFriends.add(userStorage.getUser(userId));
                }
            }
            log.info("Получен запрос на получение друзей по id" + id);
            return userFriends;
        }else {
            log.warn("error : По данному id: " +id+ "пользователь не найден");
            throw new SearchException("По данному id: " +id+ "пользователь не найден");
        }

    }
    
    public ArrayList<User> getCommonFriends(int id, int otherId) {
        if(userStorage.getUsers().containsKey(id) || userStorage.getUsers().containsKey(otherId)) {
            log.info("Получен запрос на список общих друзей");
            ArrayList<User> friendsList = new ArrayList<>();
            for(Integer friendIdFirst : userStorage.getUser(id).getFriendsId()) {
                for(Integer friendIdSecond : userStorage.getUser(otherId).getFriendsId()) {
                    if(friendIdFirst.equals(friendIdSecond)) {
                        friendsList.add(userStorage.getUser(friendIdFirst));
                    }
                }
            }
            return friendsList;
        } else {
            throw new SearchException("По данным id пользователи не найдены");
        }
    }
    public User getUser(int id){
        log.info("Подучен запрос на пользователя " + id);
        return userStorage.getUser(id);
    }

    public ArrayList<User> getUsers() {
        log.info("Получен запрос на список поьзователей.");
        return userStorage.getUsersList();
    }

    public User saveUser(User user) {
        log.info("Получен запрос на сохранение пользователя");
        userStorage.saveUser(user);
        return user;
    }

    public User updateUser(User user) {
        log.info("Получен запрос на изменение информации о пользователе под id" + user.getId());
        userStorage.updateUser(user);
        return user;
    }
}
