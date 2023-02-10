package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.SearchException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

        private final GenreService genreService;

        @GetMapping
        public List<Genre> getListGenre() {
            return genreService.getGenreList();
        }

        @GetMapping("/{id}")
        public Genre getGenre(@PathVariable int id) {
            return genreService.getGenre(id);
        }

        @ExceptionHandler
        public ResponseEntity<Map<String, String>> handleSearchException(final SearchException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
}
