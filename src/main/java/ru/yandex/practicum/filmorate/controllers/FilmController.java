package ru.yandex.practicum.filmorate.controllers;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();

    @GetMapping
    public ArrayList<Film> getFilms() {
       return filmStorage.getFilms();
    }

    @PostMapping
    public Film saveFilm(@Valid @RequestBody Film film) {
            filmStorage.saveFilm(film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        filmStorage.updateFilm(film);
        return film;
    }
}