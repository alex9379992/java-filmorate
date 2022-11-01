package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.SearchException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final FilmStorage filmStorage;
    private final FilmService filmService;
    private final UserStorage userStorage;

    @Autowired
    public FilmController(InMemoryFilmStorage filmStorage, FilmService filmService, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
        this.userStorage = userStorage;
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(required = false) Integer count) {
         return filmService.getPopularFilms(filmStorage.getFilms(), count);
    }
    @PutMapping("{id}/like/{userId}")
    public void addLikes(@PathVariable int id, @PathVariable int userId) {
         if(userStorage.getUsers().containsKey(userId) || filmStorage.getFilms().containsKey(id)) {
              filmService.putLike(filmStorage.getFilm(id), userStorage.getUser(userId));
        } else {
            log.warn("error : пользователь/фильм не найден");
            throw new SearchException("Ошибка поиска по id");
        }
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
         if(userStorage.getUsers().containsKey(userId) || filmStorage.getFilms().containsKey(id)) {
            filmService.deleteLike(filmStorage.getFilm(id), userStorage.getUser(userId));
        } else {
            log.warn("error : пользователь/фильм не найден");
            throw new SearchException("Ошибка поиска по id");
        }
    }
    @GetMapping("/{id}")
    public Film getFilm(@PathVariable int id) {
        return filmStorage.getFilm(id);
    }



    @GetMapping
    public ArrayList<Film>  getFilms() {
       return filmStorage.getFilmsList();
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