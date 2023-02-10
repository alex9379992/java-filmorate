package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmStorage {

    Film getFilm(int id);
    List<Film> getFilmsList();
    Film saveFilm(Film film);
    Film updateFilm(Film film);
    Map<Integer, Film> getFilms();
    void deleteLike(int filmId, int userId);
    void addLike(int filmId, int userId);
}
