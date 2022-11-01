package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.SearchException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    public void putLike(Film film, User user) {
        log.info("Пользователь " + user.getId() + " поставил лайк фильму " + film.getId());
        film.getLikesList().add(user.getId());
    }

    public void deleteLike(Film film, User user) {
        if (film.getLikesList().contains(user.getId())) {
            log.info("Пользователь " + user.getId() + " удалил лайк фильму " + film.getId());
            film.getLikesList().remove(user.getId());
        } else {
            log.warn("Пользователь " + user.getId() + " не смог удалить лайк фильму " + film.getId());
            throw new SearchException("Пользователь " + user.getId() + " не смог удалить лайк фильму " + film.getId());
        }
    }

    public List<Film> getPopularFilms(Map<Integer, Film> films, Integer count) {
        List<Film> allFilms = new ArrayList<>(films.values());
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
}
