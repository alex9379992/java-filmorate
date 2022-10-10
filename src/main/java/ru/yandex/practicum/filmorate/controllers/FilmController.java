package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validators.FilmValidator;
import ru.yandex.practicum.filmorate.validators.exeptions.ValidationException;

import java.time.Duration;
import java.time.LocalDate;
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
       return films;
    }

    @PostMapping
    public Film saveFilm(@RequestBody Film film) {
        try{
            if(validator.validate(film)) {
                film.setId(idGenerator());
               films.put(film.getId(), film);
            } else {
               throw new ValidationException("Ошибка валидации.");
            }
        }catch (ValidationException | NullPointerException exception) {
            System.out.println(exception.getMessage());
        }
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        try {
            if(validator.validate(film)) {
                if(films.containsKey(film.getId())) {
                    films.replace(film.getId(), film);
                } else {
                    films.put(film.getId(), film);
                }
            } else {
                throw new ValidationException("Ошибка валидации.");
            }
        }catch (ValidationException | NullPointerException exception) {
            System.out.println(exception.getMessage());
        }
        return film;
    }

    private int idGenerator() {
        return id++;
    }
}