package ru.yandex.practicum.filmorate.storage.impl.dbDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeptions.SearchException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.Mappers.FilmMapper;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component("FilmDbStorage")
@Slf4j
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreDbStorage genreDbStorage;
    private final MpaDbStorage mpaDbStorage;
    private final LikeDbStorage likeDbStorage;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, GenreDbStorage genreDbStorage, MpaDbStorage mpaDbStorage, LikeDbStorage likeDbStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreDbStorage = genreDbStorage;
        this.mpaDbStorage = mpaDbStorage;
        this.likeDbStorage = likeDbStorage;
    }

    @Override
    public Film getFilm(int id) {
        Film film = jdbcTemplate.queryForObject("SELECT * FROM films WHERE id = ?;", new FilmMapper(), id);
        Objects.requireNonNull(film).setMpa(mpaDbStorage.getMpaFromId(id));
        film.getGenres().addAll(genreDbStorage.getGenresFromId(id));
        film.getLikesList().addAll(likeDbStorage.getLikeListFromId(id));
        return film;
    }

    @Override
    public List<Film> getFilmsList() {
        log.info("Сформирован и отправлен список фильмов.");
        List<Film> filmList = jdbcTemplate.query("select * from FILMS", new FilmMapper());
         for (Film film: filmList) {
            film.setMpa(mpaDbStorage.getMpaFromId(film.getId()));
            film.getGenres().addAll(genreDbStorage.getGenresFromId(film.getId()));
             film.getLikesList().addAll(likeDbStorage.getLikeListFromId(film.getId()));
        }
         return filmList;
    }

    @Override
    public Film saveFilm(Film film) {
        jdbcTemplate.update("INSERT INTO films(NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID) values (?,?,?,?,?)",
                    film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId());
        Integer idLustFilm = jdbcTemplate.queryForObject("SELECT id FROM FILMS ORDER BY ID DESC LIMIT 1;", Integer.class);
        if(!film.getGenres().isEmpty()) {
            for (Genre genre: film.getGenres()) {
                  genreDbStorage.saveGenre(idLustFilm, genre.getId());
            }
        }
        log.info("Пользователь сохранен под id " + idLustFilm);
        return getFilm(Objects.requireNonNull(idLustFilm));
    }

    @Override
    public Film updateFilm(Film film) {
        if(foundFilm(film)) {
            jdbcTemplate.update("UPDATE FILMS SET NAME=?, DESCRIPTION=?, RELEASE_DATE=?, DURATION=?, MPA_ID=?  WHERE ID=?;",
                  film.getName(), film.getDescription(), film.getReleaseDate(),
                  film.getDuration(), film.getMpa().getId(), film.getId());
            log.info("Изменения для фильма " + film.getId() + " сохранены.");
            genreDbStorage.deleteGenre(film.getId());
            if(!film.getGenres().isEmpty()) {
                for (Genre genre: film.getGenres()) {
                    genreDbStorage.saveGenre(film.getId(), genre.getId());
                }
            }
            return getFilm(film.getId());
        } else {
            log.warn("Ошибка поиска фильма: не найден под id " + film.getId());
            throw new SearchException("Фильм с id " + film.getId() + " не найден");
        }
    }

    @Override
    public Map<Integer, Film> getFilms() {
        log.info("Сформирована Мапа фильмов.");
        return getFilmsList().stream().collect(Collectors.toMap(Film::getId, film -> film));
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        likeDbStorage.deleteLike(filmId, userId);
    }

    @Override
    public void addLike(int filmId, int userId) {
        likeDbStorage.addLike(filmId, userId);
    }

    private boolean foundFilm(Film film) {
        return getFilms().containsKey(film.getId());
    }
}