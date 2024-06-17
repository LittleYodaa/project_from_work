package pl.programisci.task_crud.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.programisci.task_crud.dto.FullBookDto;
import pl.programisci.task_crud.dto.mapper.BookDtoMapper;
import pl.programisci.task_crud.file.reader.BookFileReader;
import pl.programisci.task_crud.file.reader.FtpFileReader;
import pl.programisci.task_crud.model.Book;
import pl.programisci.task_crud.repository.BookDao;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    BookFileReader bookFileReader;

    @Mock
    BookDao bookDao;

    @Mock
    BookDtoMapper bookDtoMapper;

    @Mock
    FtpFileReader ftpFileReader;

    @InjectMocks
    BookService bookService;

    @Captor
    ArgumentCaptor<List<Book>> listOfBooksCaptor;

    OffsetDateTime offsetDateTimeNow = OffsetDateTime.now();

    Book book1 = new Book(1, "Książka1", 12345, 1, 1, offsetDateTimeNow,
            offsetDateTimeNow, 1, 1);
    Book book2 = new Book(2, "Książka2", 54321, 2, 2, offsetDateTimeNow,
            offsetDateTimeNow, 2, 2);

    FullBookDto fullBookDto1 = new FullBookDto(1, "Książka1", 12345, 1, 1, offsetDateTimeNow.toString(),
            offsetDateTimeNow.toString(), 1);
    FullBookDto fullBookDto2 = new FullBookDto(2, "Książka2", 12345, 2, 2, offsetDateTimeNow.toString(),
            offsetDateTimeNow.toString(), 2);

    @Test
    public void shouldPassTwoBooksInList() {
        //given
        when(bookFileReader.readFile()).thenReturn(Arrays.asList(book1, book2));

        //when
        bookService.fillTableWithBook();

        //then
        verify(bookDao).fillBookTabel(listOfBooksCaptor.capture());
        List<Book> books = listOfBooksCaptor.getValue();
        assertThat(books).containsExactlyInAnyOrder(book1, book2);
        assertThat(books).hasSize(2);
    }

    @Test
    public void shouldGetTwoBookAndReturnListOfTwoObjects() {
        //given
        when(bookDao.getAllBooks()).thenReturn(Arrays.asList(book1, book2));

        //when
        List<FullBookDto> allBooks = bookService.getAllBooks();

        //then
        assertThat(allBooks).hasSize(2);
    }

    @Test
    public void shouldGetBookAndReturnFullBookDto() {
        //given
        when(bookDao.getAllBooks()).thenReturn(Arrays.asList(book1));
        when(bookDtoMapper.mapToFullBookDto(book1)).thenReturn(fullBookDto1);

        //when
        List<FullBookDto> allBooks = bookService.getAllBooks();

        //then
        assertThat(allBooks.get(0)).isEqualTo(fullBookDto1);
    }
}
