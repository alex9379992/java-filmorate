package ru.yandex.practicum.filmorate.storage.impl.dbDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeptions.SearchException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.Mappers.MpaMapper;

import java.util.List;

@Component()
@Slf4j
 public class MpaDbStorage {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Mpa getMPA(int id) throws SearchException {
        Mpa mpa = jdbcTemplate.query("select * from MPA where MPA_ID = ?",new Object[]{id}, new MpaMapper())
                .stream().findAny().orElse(null);
        if(mpa == null) {
            throw  new SearchException("По id " + id + " mpa не найдено");
        }
        return mpa;
    }

    public List<Mpa> getListMpa() {
        return jdbcTemplate.query("SELECT * FROM MPA", new MpaMapper());
    }

    public Mpa getMpaFromId(int id) {
        return jdbcTemplate.queryForObject(  "SELECT f.MPA_ID , m.NAME " +
                "FROM FILMS f " +
                "INNER JOIN MPA m ON f.MPA_ID = m.MPA_ID " +
                "WHERE f.ID = ?;" , new MpaMapper(), id);
    }
}
