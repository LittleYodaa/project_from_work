package pl.programisci.task_crud.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import pl.programisci.task_crud.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

@Service
public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Book.builder()
                .bookId(rs.getInt("book_id"))
                .name(rs.getString("name"))
                .isbn(rs.getInt("isbn"))
                .authorId(rs.getInt("author_id"))
                .genreId(rs.getInt("genre_id"))
                .dbCreationTime(rs.getObject("db_create_time", OffsetDateTime.class))
                .dbModifyTime(rs.getObject("db_modify_time", OffsetDateTime.class))
                .version(rs.getInt("version"))
                .build();
    }
}
