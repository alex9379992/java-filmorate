package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.enums.GenresType;
import ru.yandex.practicum.filmorate.model.enums.RatingType;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class Film {
    private final Set<Integer> likesList = new HashSet<>();
    private final Set<GenresType> genresList = new HashSet<>();
    private int id;
    @NotNull
    private  String  name;
    @NotNull
    private  String description;
    @NotNull
    private  LocalDate releaseDate;
    @NotNull
    private  int duration;
    private RatingType rating;

}
