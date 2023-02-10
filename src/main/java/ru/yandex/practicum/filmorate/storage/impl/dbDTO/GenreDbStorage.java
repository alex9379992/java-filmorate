package ru.yandex.practicum.filmorate.storage.impl.dbDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeptions.SearchException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.Mappers.GenreMapper;

import java.util.List;

@Component()
@Slf4j
public class GenreDbStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Genre getGenre(int id) throws SearchException {
        Genre genre = jdbcTemplate.query("SELECT GENRES_ID, NAME FROM GENRES WHERE GENRES_ID = ?",
                        new Object[]{id}, new GenreMapper())
                .stream().findAny().orElse(null);
        if(genre == null) {
            throw  new SearchException("По id " + id + " Genre не найден");
        }
        return genre;
    }

    public List<Genre> getListGenre() {
        return jdbcTemplate.query("SELECT * FROM GENRES", new GenreMapper());
    }
    public List<Genre> getGenresFromId(int id) {
        return jdbcTemplate.query("SELECT DISTINCT gf.GENRES_ID , g.NAME " +
                "FROM GENRES_FILM gf " +
                "INNER JOIN  GENRES g ON gf.GENRES_ID = g.GENRES_ID " +
                "WHERE gf.FILM_ID = ?;", new GenreMapper(), id);
    }

    public void saveGenre(int idFilm, int idGenre) {
        jdbcTemplate.update("INSERT INTO GENRES_FILM (FILM_ID, GENRES_ID) VALUES (?,?);",
                idFilm, idGenre);
    }

    public void deleteGenre(int idFilm) {
        jdbcTemplate.update("DELETE FROM GENRES_FILM WHERE FILM_ID = ?", idFilm);
    }
}
