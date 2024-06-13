
package pl.programisci.task_crud.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import pl.programisci.task_crud.exception.GenreAlreadyExistException;
import pl.programisci.task_crud.exception.GenreForeignKeyReferenceException;
import pl.programisci.task_crud.exception.GenreNotFoudException;
import pl.programisci.task_crud.mapper.BookMapper;
import pl.programisci.task_crud.mapper.GenreMapper;
import pl.programisci.task_crud.model.Book;
import pl.programisci.task_crud.model.Genre;

import java.util.List;
import java.util.Map;


@Repository
public class GenreDao {
    private final JdbcTemplate jdbcTemplate;

    public GenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private SimpleJdbcCall getSimpleJdbcTemplate() {
        return new SimpleJdbcCall(jdbcTemplate);
    }

    @SuppressWarnings("unchecked")
    public Genre getGenre(Integer genreId) {
        SimpleJdbcCall simpleJdbcCall = getSimpleJdbcTemplate();
        simpleJdbcCall.withProcedureName("genre_find_by_id")
                .returningResultSet("genres", new GenreMapper());
        SqlParameterSource in = new MapSqlParameterSource().addValue("genreId", genreId);
        try {
            Map<String, Object> out = simpleJdbcCall.execute(in);
            List<Genre> genres = (List<Genre>) out.get("genres");

            if (genres == null || genres.isEmpty()) {
                throw new GenreNotFoudException();
            }
            return genres.stream().findFirst().orElseThrow(GenreNotFoudException::new);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new GenreNotFoudException();
        }
    }

    public void addGenre(Genre genre) {
        SimpleJdbcCall simpleJdbcCall = getSimpleJdbcTemplate();
        simpleJdbcCall.withProcedureName("genre_add");
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("type", genre.getType());
        try {
            simpleJdbcCall.execute(in);
        } catch (DataAccessException e) {
            throw new GenreAlreadyExistException(e);
        }
    }


    public void deleteGenre(Integer genreId) {
        SimpleJdbcCall simpleJdbcCall = getSimpleJdbcTemplate();
        simpleJdbcCall.withProcedureName("genre_delete");
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("genreId", genreId);
        try {
            simpleJdbcCall.execute(in);
        } catch (DataAccessException e) {
            throw new GenreForeignKeyReferenceException();
        }
    }

    public void updateGenre(Integer genreId, String type) {
        SimpleJdbcCall simpleJdbcCall = getSimpleJdbcTemplate();
        simpleJdbcCall.withProcedureName("genre_update");
        MapSqlParameterSource in = new MapSqlParameterSource()
                .addValue("genreId", genreId)
                .addValue("type", type);
        try {
            simpleJdbcCall.execute(in);
        } catch (DataAccessException e) {
            throw new GenreNotFoudException();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Book> getBooksByGenre(String name) {
            SimpleJdbcCall simpleJdbcTemplate = getSimpleJdbcTemplate();
            simpleJdbcTemplate.withProcedureName("find_book_by_genre")
                    .returningResultSet("books", new BookMapper());
            MapSqlParameterSource in = new MapSqlParameterSource()
                    .addValue("name", name);

            List<Book> books = (List<Book>) simpleJdbcTemplate.execute(in).get("books");
            return books;
    }


    @SuppressWarnings("unchecked")
    public List<Genre> getGenres() {
        SimpleJdbcCall simpleJdbcCall = getSimpleJdbcTemplate();
        simpleJdbcCall.withProcedureName("genre_get_all")
                .returningResultSet("genres", new GenreMapper());
        try {
            List<Genre> genres = (List<Genre>) simpleJdbcCall.execute().get("genres");
            return genres;
        } catch (DataAccessException e) {
            throw new GenreNotFoudException();
        }
    }
}





