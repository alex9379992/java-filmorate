package ru.yandex.practicum.filmorate.storage.Mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Film(rs.getInt("ID"), rs.getString("NAME"), rs.getString("DESCRIPTION"),
                rs.getDate("RELEASE_DATE").toLocalDate(), rs.getInt("DURATION"));
//              return  Film.builder()
//                .id(rs.getInt("ID"))
//                .name()
//                .description(rs.getString("DESCRIPTION"))
//                .releaseDate(rs.getDate("RELEASE_DATE").toLocalDate())
//                .duration(rs.getInt("DURATION"))
//                .build();
    }
}
