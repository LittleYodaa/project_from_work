package pl.programisci.task_crud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import pl.programisci.task_crud.dto.FullBookDto;
import pl.programisci.task_crud.dto.mapper.BookDtoMapper;
import pl.programisci.task_crud.file.reader.BookFileReader;
import pl.programisci.task_crud.model.Book;
import pl.programisci.task_crud.repository.BookDao;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Configuration
@EnableScheduling
public class BookService {
    private final BookDao bookDao;;
    private final JdbcTemplate jdbcTemplate;
    private final BookFileReader bookFileReader;
    private final BookDtoMapper bookDtoMapper;

    public BookService(BookDao bookDao, JdbcTemplate jdbcTemplate, BookFileReader bookFileReader, BookDtoMapper bookDtoMapper) {
        this.bookDao = bookDao;
        this.jdbcTemplate = jdbcTemplate;
        this.bookFileReader = bookFileReader;
        this.bookDtoMapper = bookDtoMapper;
    }

    private SimpleJdbcCall getSimpleJdbcTemplate() {
        return new SimpleJdbcCall(jdbcTemplate);
    }

//    @Scheduled(cron = "0 * * * * *")
//    public void deleteAuthorAndBookOlderThan6Hours() {
//        SimpleJdbcCall simpleJdbcCall = getSimpleJdbcTemplate();
//        simpleJdbcCall.withProcedureName("book_delete_older_then_2minutes");
//        simpleJdbcCall.execute();
//        log.info("Delete books older than 2 minutes");
//    }

    public void fillTableWithBook() {
        List<Book> books = bookFileReader.readFile();
        bookDao.fillBookTabel(books);
    }

    public List<FullBookDto> getAllBooks() {
        List<Book> books = bookDao.getAllBooks();
        List<FullBookDto> booksDto = new ArrayList<>();
        for (Book book : books) {
            FullBookDto bookDto = bookDtoMapper.mapToFullBookDto(book);
            booksDto.add(bookDto);
        }
        return booksDto;
    }
}
