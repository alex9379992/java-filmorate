package ru.yandex.practicum.filmorate.storage.Mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Like;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeMapper implements RowMapper<Like> {
    @Override
    public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Like.builder().like_id(rs.getInt("LIKE_ID")).
                user_id(rs.getInt("USER_ID")).
                film_id(rs.getInt("FILM_ID")).
                build();
    }
}
