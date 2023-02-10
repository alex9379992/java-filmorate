package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.impl.dbDTO.FilmDbStorage;
import ru.yandex.practicum.filmorate.validators.FilmValidator;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmServiceTest {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final FilmValidator validator = new FilmValidator();

    @Autowired
    public FilmServiceTest(@Qualifier("FilmDbStorage") FilmDbStorage filmStorage, @Qualifier("UserDbStorage") UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }


    @Test
    void putLike() {

    }

    @Test
    void deleteLike() {
    }

    @Test
    void getPopularFilms() {
    }

    @Test
    void getFilm() {
    }

    @Test
    void getFilms() {
    }

    @Test
    void saveFilm() {
    }

    @Test
    void updateFilm() {
    }
}