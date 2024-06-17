package pl.programisci.task_crud.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import pl.programisci.task_crud.exception.GenreNotFoudException;
import pl.programisci.task_crud.repository.GenreDao;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


//@ExtendWith(MockitoExtension.class)
public class GenreDaoTest {
//private final GenreDao genreDao;

//    @InjectMocks
    private GenreDao genreDao;

//    @Mock
    SimpleJdbcCall simpleJdbcCall;

    JdbcTemplate jdbcTemplate;

//    @Mock
//    SimpleJdbcCall simpleJdbcCall;

//    @Mock
//    MapSqlParameterSource mapSqlParameterSource;

//    public GenreDaoTest(GenreDao genreDao) {
//        this.genreDao = genreDao;
//    }


    @BeforeEach
    public void init() {
        jdbcTemplate = mock(JdbcTemplate.class);
        genreDao = new GenreDao(jdbcTemplate);
    }


    @Test
    void shouldThrowGenreNotFoundExceptionWhenSimpleJdbcCallThrowDataAccessException() {
       // given
       when(simpleJdbcCall.withProcedureName("genre_find_by_id")).thenReturn(simpleJdbcCall);
       when(simpleJdbcCall.returningResultSet(eq("genres"), any(RowMapper.class))).thenReturn(simpleJdbcCall);
        when(simpleJdbcCall.execute(any(SqlParameterSource.class))).thenThrow(new DataAccessException("") {
        });

        //when - then
        assertThatThrownBy(() -> genreDao.getGenre(1)).isInstanceOf(GenreNotFoudException.class);
    }
}
