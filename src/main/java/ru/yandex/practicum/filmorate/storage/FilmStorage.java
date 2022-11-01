package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.Map;

public interface FilmStorage {

    Film getFilm(int id);
    ArrayList<Film> getFilmsList();
    void saveFilm(Film film);
    void updateFilm(Film film);
    int idGenerator();
    Map<Integer, Film> getFilms();
}
