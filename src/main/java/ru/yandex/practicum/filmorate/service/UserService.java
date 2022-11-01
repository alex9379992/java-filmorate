package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.SearchException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Map;

@Service
@Slf4j
public class UserService {

    public void addFriends(User userFirst, User userSecond) {
        boolean isAddUserFirst =  userFirst.getFriendsId().add(userSecond.getId());
        boolean isAddUserSecond = userSecond.getFriendsId().add(userFirst.getId());
        if(isAddUserFirst || isAddUserSecond) {
            log.info("Польватели " + userFirst.getName() + " и " + userSecond.getName() + " добавились в друзья");
        } else {
            log.warn("Error : польватели " + userFirst.getName() + " и " + userSecond.getName() +
                    " не смогли добавиться в друзья. Возможно они уже состоят в друзьях .");
            throw new SearchException("Error : польватели " + userFirst.getName() + " и " + userSecond.getName() +
                    " не смогли добавиться в друзья. Возможно они уже состоят в друзьях .");
        }
    }

    public void deleteFriends(User userFirst, User userSecond) {
        boolean isDeleteFriendsUserFirst = userFirst.getFriendsId().remove(userSecond.getId());
        boolean isDeleteFriendsUserSecond = userSecond.getFriendsId().remove(userFirst.getId());
        if(isDeleteFriendsUserFirst || isDeleteFriendsUserSecond) {
            log.info("Польватели " + userFirst.getName() + " и " + userSecond.getName() + " удалились из друзей");
        } else {
            log.warn("Error : польватели " + userFirst.getName() + " и " + userSecond.getName() +
                    " не смогли Удалиться длуг у друга");
            throw new SearchException("Error : польватели " + userFirst.getName() + " и " + userSecond.getName() +
                    " не смогли Удалиться длуг у друга");
        }
    }

    public ArrayList<User> getFriends(User user, Map<Integer, User> users) {
        ArrayList<User> userFriends = new ArrayList<>();
        for (Integer userId : user.getFriendsId()) {
            if(users.containsKey(userId)) {
                userFriends.add(users.get(userId));
            }
        }
        log.info("Получен запрос на получение друзей по id" + user.getId());
        return userFriends;
    }
    
    public ArrayList<User> getCommonFriends(User userFirst, User userSecond, Map<Integer, User> users) {
        log.info("Получен запрос на список общих друзей");
        ArrayList<User> friendsList = new ArrayList<>();
        for(Integer friendIdFirst : userFirst.getFriendsId()) {
            for(Integer friendIdSecond : userSecond.getFriendsId()) {
                if(friendIdFirst.equals(friendIdSecond)) {
                    friendsList.add(users.get(friendIdFirst));
                }
            }
        }
        return friendsList;
    }
}
