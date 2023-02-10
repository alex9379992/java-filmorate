package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Like {
    private int like_id;
    private int user_id;
    private int film_id;

    public Like(int like_id, int user_id, int film_id) {
        this.like_id = like_id;
        this.user_id = user_id;
        this.film_id = film_id;
    }
}
