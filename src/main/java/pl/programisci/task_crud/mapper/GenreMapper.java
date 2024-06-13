package pl.programisci.task_crud.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import pl.programisci.task_crud.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

@Service
public class GenreMapper implements RowMapper<Genre> {
    public Genre mapRow(ResultSet rs, int rowColumn) throws SQLException {
        return Genre.builder()
                .genreId(rs.getInt("genre_id"))
                .type(rs.getString("type"))
                .dbCreationDate(rs.getObject("db_create_time", OffsetDateTime.class))
                .dbUpdateDate(rs.getObject("db_modify_time", OffsetDateTime.class))
                .version(rs.getInt("version"))
                .build();
    }
}
