package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.SearchException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage1) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage1;
    }


    public void putLike(int id , int userId) {
        if(userStorage.getUsers().containsKey(userId) || filmStorage.getFilms().containsKey(id)) {
            log.info("Пользователь " + userStorage.getUser(userId).getId() + " поставил лайк фильму " +
                    filmStorage.getFilm(id).getId());
            filmStorage.getFilm(id).getLikesList().add(userStorage.getUser(userId).getId());
        } else {
            log.warn("error : пользователь/фильм не найден");
            throw new SearchException("Ошибка поиска по id");
        }
    }

    public void deleteLike(int id, int userId) {
        if(userStorage.getUsers().containsKey(userId) || filmStorage.getFilms().containsKey(id)) {
            if (filmStorage.getFilm(id).getLikesList().contains(userStorage.getUser(userId).getId())) {
                log.info("Пользователь " + userStorage.getUser(userId).getId() + " удалил лайк фильму " +
                        filmStorage.getFilm(id).getId());
                filmStorage.getFilm(id).getLikesList().remove(userStorage.getUser(userId).getId());
            } else {
                log.warn("Пользователь " + userStorage.getUser(userId).getId() + " не смог удалить лайк фильму " +
                        filmStorage.getFilm(id).getId());
                throw new SearchException("Пользователь " + userStorage.getUser(userId).getId() +
                        " не смог удалить лайк фильму " + filmStorage.getFilm(id).getId());
            }
        } else {
            log.warn("error : пользователь/фильм не найден");
            throw new SearchException("Ошибка поиска по id");
        }
    }

    public List<Film> getPopularFilms( Integer count) {
        List<Film> allFilms = new ArrayList<>(filmStorage.getFilms().values());
        if (count != null) {
            log.info("Получен запрос на список популярных фильмов размером" + count);
            return allFilms.stream().
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
            return allFilms.stream().
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
        if(filmStorage.getFilms().containsKey(id)) {
            log.info("Получен запрос на фильм по id" + id);
            return filmStorage.getFilm(id);
        } else {
            log.warn("По id " + id + " фильм не найден!");
            throw new SearchException("По id " + id + " фильм не найден!");
        }
    }
    public ArrayList<Film>  getFilms() {
        log.info("Получен запрос насписок фильмов");
        return filmStorage.getFilmsList();
    }

    public Film saveFilm(Film film) {
        log.info("Получен запрос на сохранение фильма.");
        filmStorage.saveFilm(film);
        return film;
    }

    public Film updateFilm(Film film) {
        log.info("Получен запрос на изменение о фильме под id " + film.getId());
        filmStorage.updateFilm(film);
        return film;
    }
}
