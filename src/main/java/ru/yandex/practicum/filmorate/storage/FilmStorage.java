package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public interface FilmStorage {
    Map<Integer, Film> films = new HashMap<>();

    Film getFilm(int id);
    ArrayList<Film> getFilmsList();
    void saveFilm(Film film);
    void updateFilm(Film film);
    int idGenerator();
    Map<Integer, Film> getFilms();
}
