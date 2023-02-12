package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.SearchException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.impl.dbDTO.FilmDbStorage;
import ru.yandex.practicum.filmorate.validators.FilmValidator;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final FilmValidator validator = new FilmValidator();


    @Autowired
    public FilmService(@Qualifier("FilmDbStorage") FilmDbStorage filmStorage, @Qualifier("UserDbStorage") UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void putLike(int filmId, int userId) {
        if (userStorage.getUsers().containsKey(userId) || filmStorage.getFilms().containsKey(filmId)) {
            log.info("Пользователь " + userId + " поставил лайк фильму " + filmId);
            filmStorage.addLike(filmId, userId);
        } else {
            log.warn("error : пользователь/фильм не найден");
            throw new SearchException("Ошибка поиска по id");
        }
    }

    public void deleteLike(int filmId, int userId) {
        if (userStorage.getUsers().containsKey(userId) || filmStorage.getFilms().containsKey(filmId)) {
                log.info("Пользователь " + userId + " удалил лайк фильму " + filmId);
                filmStorage.deleteLike(filmId, userId);
        } else {
            log.warn("error : пользователь/фильм не найден");
            throw new SearchException("Ошибка поиска по id");
        }
    }

    public List<Film> getPopularFilms(Integer count) {
        if (count != null) {
            log.info("Получен запрос на список популярных фильмов размером" + count);
            return filmStorage.getFilms().values().stream().
                    sorted((f1, f2) -> {
                        Integer f1size = f1.getLikesList().size();
                        Integer f2size = f2.getLikesList().size();
                        int comp = f1size.compareTo(f2size);
                        return -1 * comp;
                    }).
                    limit(count).
                    collect(Collectors.toList());
        } else {
            log.info("Получен запрос на список популярных фильмов");
            return filmStorage.getFilms().values().stream().
                    sorted((f1, f2) -> {
                        Integer f1size = f1.getLikesList().size();
                        Integer f2size = f2.getLikesList().size();
                        int comp = f1size.compareTo(f2size);
                        return -1 * comp;
                    }).
                    collect(Collectors.toList());
        }
    }

    public Film getFilm(int id) {
        if (filmStorage.getFilms().containsKey(id)) {
            log.info("Получен запрос на фильм по id" + id);
            return filmStorage.getFilm(id);
        } else {
            log.warn("По id " + id + " фильм не найден!");
            throw new SearchException("По id " + id + " фильм не найден!");
        }
    }

    public List<Film> getFilms() {
        log.info("Получен запрос насписок фильмов");
        return filmStorage.getFilmsList();
    }

    public Film saveFilm(Film film) {
        log.info("Получен запрос на сохранение фильма.");
        if (validator.validate(film)) {
            return filmStorage.saveFilm(film);
        } else {
            log.warn("Ошибка валидации.");
            throw new ValidationException("Ошибка валидации.");
        }
    }

    public Film updateFilm(Film film) {
        log.info("Получен запрос на изменение о фильме под id " + film.getId());
        if (validator.validate(film)) {
             return filmStorage.updateFilm(film);
        } else {
            log.warn("Ошибка валидации.");
            throw new ValidationException("Ошибка валидации.");
        }
    }
}
