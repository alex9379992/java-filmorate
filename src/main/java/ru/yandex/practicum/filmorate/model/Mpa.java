package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Mpa {
    private final int id;
    private final String name;

    public Mpa(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
