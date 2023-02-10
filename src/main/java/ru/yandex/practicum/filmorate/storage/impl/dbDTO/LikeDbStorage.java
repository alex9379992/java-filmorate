package ru.yandex.practicum.filmorate.storage.impl.dbDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeptions.SearchException;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.Mappers.LikeMapper;

import java.util.List;

@Component()
@Slf4j
public class LikeDbStorage {
    private final JdbcTemplate jdbcTemplate;
    @Autowired

    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addLike(int filmId, int userId) {
      jdbcTemplate.update("INSERT INTO LIKES(USER_ID, FILM_ID) VALUES ( ?,? )", userId, filmId);
    }

    public List<Like> getLikeListFromId(int id) {
        return jdbcTemplate.query("SELECT * FROM LIKES WHERE FILM_ID = ?", new LikeMapper(), id);
    }

    public void deleteLike(int filmId, int userId) {
        int isDelete = jdbcTemplate.update("DELETE from LIKES WHERE FILM_ID = ? AND USER_ID = ?;" ,filmId, userId);
        if (isDelete != 0) {
            log.info("Лайк у фильма " + filmId + " от пользователя " +userId + " был удален.");
        } else {
            log.warn("Ошибка поиска лайка: не найден под id у фильма " + filmId + " от пользователя " +userId);
            throw new SearchException("Ошибка поиска лайка: не найден под id у фильма " + filmId + " от пользователя " +userId);
        }
    }
}
