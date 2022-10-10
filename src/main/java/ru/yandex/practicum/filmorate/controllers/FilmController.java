package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validators.FilmValidator;
import ru.yandex.practicum.filmorate.validators.exeptions.ValidationException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final FilmValidator validator = new FilmValidator();
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @GetMapping
    public Map<Integer, Film> getFilms() {
        log.info("Получен запрос на список фильмов");
       return films;
    }

    @PostMapping
    public Film saveFilm(@Valid @RequestBody Film film) {
            if(validator.validate(film)) {
                    film.setId(idGenerator());
                    films.put(film.getId(), film);
                    log.info("Фильм сохранен");
            } else {
                log.warn("Ошибка валидации.");
               throw new ValidationException("Ошибка валидации.");
            }
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
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
        return film;
    }

    private int idGenerator() {
        return id++;
    }
}