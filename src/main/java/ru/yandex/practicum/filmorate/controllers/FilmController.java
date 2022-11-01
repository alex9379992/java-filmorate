package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(required = false) Integer count) {
         return filmService.getPopularFilms(count);
    }
    @PutMapping("{id}/like/{userId}")
    public void addLikes(@PathVariable int id, @PathVariable int userId) {
       filmService.putLike(id, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
         filmService.deleteLike(id, userId);
    }
    @GetMapping("/{id}")
    public Film getFilm(@PathVariable int id) {
        return filmService.getFilm(id);
    }

    @GetMapping
    public ArrayList<Film>  getFilms() {
       return filmService.getFilms();
    }

    @PostMapping
    public Film saveFilm(@Valid @RequestBody Film film) {
        filmService.saveFilm(film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        filmService.updateFilm(film);
        return film;
    }
}