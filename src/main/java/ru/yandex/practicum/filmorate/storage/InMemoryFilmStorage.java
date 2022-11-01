package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeptions.SearchException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validators.FilmValidator;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;

import java.util.ArrayList;
import java.util.Map;


@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage{
    private final FilmValidator validator = new FilmValidator();
    private int id = 1;

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
    public void saveFilm(Film film) {
        if(validator.validate(film)) {
            film.setId(idGenerator());
            films.put(film.getId(), film);
            log.info("Фильм сохранен");
        } else {
            log.warn("Ошибка валидации.");
            throw new ValidationException("Ошибка валидации.");
        }
    }

    @Override
    public void updateFilm(Film film) {
        if(validator.validate(film)) {
            if(films.containsKey(film.getId())) {
                films.replace(film.getId(), film);
                log.info("Информация о фильме изменена");
            } else {
                log.warn("error : фильм с id" + film.getId() + " не найден");
                throw new SearchException("error : фильм с id" + film.getId() + " не найден");
            }
        } else {
            throw new ValidationException("Ошибка валидации.");
        }
    }

    @Override
    public int idGenerator() {
        log.info("Присвоен id " + id + 1);
        return id++;
    }

    @Override
    public Map<Integer, Film> getFilms() {
        return films;
    }
}
