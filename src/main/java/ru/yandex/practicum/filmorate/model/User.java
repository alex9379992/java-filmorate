package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.enums.FriendshipStatusesType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@Builder
public class User {
    private int id;
    private final Set<Integer> friendsId = new HashSet<>();
    private final Map<Integer, FriendshipStatusesType> friendshipStatuses = new HashMap<>();
    @NotNull
    @Email
    private  String email;
    @NotNull
    private  String login;
    private String name;
    @NotNull
    private  LocalDate birthday;
}
