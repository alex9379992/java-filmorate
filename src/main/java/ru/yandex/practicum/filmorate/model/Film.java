package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Data
@Builder
public class Film {
    private int id;
    @NotNull
    private  String  name;
    @NotNull
    private  String description;
    @NotNull
    private  LocalDate releaseDate;
    @NotNull
    private  int duration;
}
