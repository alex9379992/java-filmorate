package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.impl.dbDTO.GenreDbStorage;

import java.util.List;

@Service
@Slf4j
public class GenreService {

    private final GenreDbStorage genreDbStorage;
    @Autowired
    public GenreService(GenreDbStorage genreDbStorage) {
        this.genreDbStorage = genreDbStorage;
    }

    public Genre getGenre(int id) {
        log.info("Получен запрос на получение жанра по id" + id);
        return genreDbStorage.getGenre(id);
    }

    public List<Genre> getGenreList() {
        log.info("Получен запрос на получение списка жанров");
        return genreDbStorage.getListGenre();
    }
}
