package pl.programisci.task_crud.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import pl.programisci.task_crud.exception.BookNotFoundException;
import pl.programisci.task_crud.mapper.BookMapper;
import pl.programisci.task_crud.model.Book;

import java.util.List;

@Repository
public class BookDao {
    private final JdbcTemplate jdbcTemplate;

    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private SimpleJdbcCall getSimpleJdbcTemplate() {
        return new SimpleJdbcCall(jdbcTemplate);
    }

    public void fillBookTabel(List<Book> books) {
        String insertBooks = "INSERT INTO book(name, isbn, author_id, genre_id) VALUES (?, ?, ?, ?)";
        for (Book book : books) {
            jdbcTemplate.update(insertBooks, book.getName(), book.getIsbn(), book.getAuthorId(), book.getGenreId());
        }
    }

    @SuppressWarnings("unchecked")
    public List<Book> getAllBooks() {
        SimpleJdbcCall simpleJdbcCall = getSimpleJdbcTemplate();
        simpleJdbcCall.withProcedureName("book_get_all").returningResultSet("books", new BookMapper());
        try {
            List<Book> books = (List<Book>) simpleJdbcCall.execute().get("books");
            return books;
        } catch (DataAccessException e) {
            throw new BookNotFoundException();
        }
    }
}
