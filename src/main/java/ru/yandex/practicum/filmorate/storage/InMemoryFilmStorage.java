package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validators.FilmValidator;
import ru.yandex.practicum.filmorate.validators.exeptions.ValidationException;

import java.util.ArrayList;
@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage{
    private final FilmValidator validator = new FilmValidator();
    private int id = 1;

    @Override
    public ArrayList<Film> getFilms() {
        log.info("Получен запрос на список фильмов");
        return new ArrayList<Film>(films.values());
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
                log.warn("Ошибка валидации.");
                throw new ValidationException("Ошибка валидации.");
            }
        } else {
            throw new ValidationException("Ошибка валидации.");
        }
    }

    @Override
    public int idGenerator() {
        return id++;
    }
}
