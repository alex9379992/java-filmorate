package ru.yandex.practicum.filmorate.storage.Mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder().
                id(rs.getInt("GENRES_ID")).
                name(rs.getString("name")).
                build();
    }
}
