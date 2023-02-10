package ru.yandex.practicum.filmorate.storage.impl.inMemory;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exeptions.SearchException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.validators.FilmValidator;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final FilmValidator validator = new FilmValidator();
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @Override
    public void addLike(int idFilm, int idUser) {
    }

    @Override
    public ArrayList<Film> getFilmsList() {
        log.info("Получен запрос на список фильмов");
        return new ArrayList<>(films.values());
    }


    @Override
    public Film getFilm(int id) {
        if(films.containsKey(id)) {
            log.info("Подучен запрос на фильм с id " + id);
            return films.get(id);
        }
        throw new SearchException("Ошибка поиска фильма: не найден под id " + id);
    }
    @Override
    public Film saveFilm(Film film) {
        if(validator.validate(film)) {
            film.setId(idGenerator());
            films.put(film.getId(), film);
            log.info("Фильм сохранен");
            return film;
        } else {
            log.warn("Ошибка валидации.");
            throw new ValidationException("Ошибка валидации.");
        }
    }

    @Override
    public Film updateFilm(Film film) {
        if(validator.validate(film)) {
            if(films.containsKey(film.getId())) {
                films.replace(film.getId(), film);
                log.info("Информация о фильме изменена");
                return film;
            } else {
                log.warn("error : фильм с id" + film.getId() + " не найден");
                throw new SearchException("error : фильм с id" + film.getId() + " не найден");
            }
        } else {
            log.warn("Ошибка валидации");
            throw new ValidationException("Ошибка валидации.");
        }
    }

    public int idGenerator() {
        log.info("Фильму присвоен id " + id + 1);
        return id++;
    }

    @Override
    public Map<Integer, Film> getFilms() {
        return films;
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        getFilm(filmId).getLikesList().remove(userId);
    }
}
