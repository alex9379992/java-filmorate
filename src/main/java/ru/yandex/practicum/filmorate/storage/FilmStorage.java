package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validators.FilmValidator;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface FilmStorage {
    final Map<Integer, Film> films = new HashMap<>();

    ArrayList<Film> getFilms();
    void saveFilm(Film film);
    void updateFilm(Film film);
    int idGenerator();
}
