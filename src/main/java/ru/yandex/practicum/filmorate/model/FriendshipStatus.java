package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FriendshipStatus {
    private  final int friend_id;
    private final int statusesId;
    private final String name;

    public FriendshipStatus(int friend_id, int statusesId, String name) {
        this.friend_id = friend_id;
        this.statusesId = statusesId;
        this.name = name;
    }
}
