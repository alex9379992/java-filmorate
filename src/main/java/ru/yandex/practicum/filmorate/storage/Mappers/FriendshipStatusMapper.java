package ru.yandex.practicum.filmorate.storage.Mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.FriendshipStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendshipStatusMapper implements RowMapper<FriendshipStatus> {
    @Override
    public FriendshipStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
        return FriendshipStatus.builder().friend_id(rs.getInt("FRIEND_ID")).
                statusesId(rs.getInt("STATUSES_ID")).name(rs.getString("NAME")).
                build();
    }
}
