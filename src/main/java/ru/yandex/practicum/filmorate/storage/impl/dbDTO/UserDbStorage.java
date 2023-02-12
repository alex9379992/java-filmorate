package ru.yandex.practicum.filmorate.storage.impl.dbDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeptions.SearchException;
import ru.yandex.practicum.filmorate.model.FriendshipStatus;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Mappers.FriendshipStatusMapper;
import ru.yandex.practicum.filmorate.storage.Mappers.UserMapper;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Component("UserDbStorage")
@Slf4j
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void addFriends(int id, int friendId) {
        if (getUsers().containsKey(id) && getUsers().containsKey(friendId)) {
            jdbcTemplate.update("INSERT INTO FRIENDS(user_id, friend_id, statuses_id) VALUES (?,?,?)", id, friendId, 1);
        } else {
            log.warn("Ошибка поиска пользователя: не найден по id");
            throw new SearchException("Пользователь не найден");
        }
    }

    @Override
    public void deleteFriends(int id, int friendId) {
        jdbcTemplate.update("DELETE FROM FRIENDS WHERE USER_ID = ? AND FRIEND_ID = ?;", id, friendId);
        log.info("Дружба удалена между пользователями: " + id + " и " + friendId);
    }

    @Override
    public List<User> getCommonFriends(int id, int otherId) {
        if (getUsers().containsKey(id) && getUsers().containsKey(otherId)) {
            User user = getUser(id);
            user.getFriendshipStatuses().putAll(getFriendshipStatus(id));
            User userFriend = getUser(otherId);
            userFriend.getFriendshipStatuses().putAll(getFriendshipStatus(otherId));
            List<User> friendList = new ArrayList<>();
            for (int i : user.getFriendshipStatuses().keySet()) {
                for (int k : userFriend.getFriendshipStatuses().keySet()) {
                    if (i == k) {
                        friendList.add(getUser(i));
                    }
                }
            }
            log.info("Список общих друзей между пользователями " + id + " и " + otherId + " сформирован и отправлен.");
            return friendList;
        } else {
            log.warn("Ошибка поиска пользователя: не найден по id");
            throw new SearchException("Пользователь не найден");
        }
    }

    private Map<Integer, FriendshipStatus> getFriendshipStatus(int id) {
        List<FriendshipStatus> friendshipStatusList = jdbcTemplate.query("SELECT f.FRIEND_ID, f.STATUSES_ID,s.NAME " +
                "FROM FRIENDS f " +
                "INNER JOIN STATUSES s ON f.STATUSES_ID = s.STATUSES_ID " +
                "WHERE USER_ID = ?;", new FriendshipStatusMapper(), id);
        log.info("Сформирована Мапа друзей и статусов для пользователя id: " + id);
        return friendshipStatusList.stream().collect(Collectors.toMap(FriendshipStatus::getFriend_id, user -> user));
    }

    @Override
    public List<User> getFriends(int id) {
        log.info("Сформирован и отправлен список друзей для пользователя id: " + id);
        return jdbcTemplate.query("SELECT * FROM USERS u LEFT JOIN FRIENDS f ON u.ID = f.FRIEND_ID WHERE f.USER_ID = ?;",
                new UserMapper(), id);
    }

    @Override
    public User getUser(int id) {
        User user = jdbcTemplate.query("select * from USERS where ID = ?",
                new Object[]{id}, new UserMapper()).stream().findAny().orElse(null);
        if (user == null) {
            log.info("Пользователь с id " + id + " не найден");
            throw new SearchException("Пользователь с id " + id + " не найден");
        }
        log.info("Сформирован и отправлен пользователь под id: " + id);
        return user;
    }

    @Override
    public List<User> getUsersList() {
        log.info("Сформирован и отправлен список пользователей.");
        return jdbcTemplate.query("select * from users", new UserMapper());
    }

    @Override
    public User saveUser(User user) {
            jdbcTemplate.update("insert into USERS(EMAIL, LOGIN, NAME, BIRTHDAY) values (?, ?, ?, ?)",
                    user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
            Integer idLustUser = jdbcTemplate.queryForObject("SELECT id FROM USERS ORDER BY ID DESC LIMIT 1;",
                    Integer.class);
            log.info("Пользователь сохранен под id " + idLustUser);
            return getUser(idLustUser);
    }

    @Override
    public User updateUser(User user) {
        int isUpdate = jdbcTemplate.update("UPDATE USERS SET EMAIL=?, LOGIN=?, NAME=?, BIRTHDAY=? WHERE ID=? ",
                user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        if (isUpdate != 0) {
            log.info("Изменения для пользователя " + user.getId() + " сохранены.");
            return user;
        } else {
            log.warn("Ошибка поиска пользователя: не найден под id " + user.getId());
            throw new SearchException("Пользователь с id " + user.getId() + " не найден");
        }
    }

    @Override
    public Map<Integer, User> getUsers() {
        log.info("Сформирована Мапа пользователей.");
        return getUsersList().stream().collect(Collectors.toMap(User::getId, user -> user));
    }
}
