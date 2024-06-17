package pl.programisci.task_crud.dto.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.programisci.task_crud.dto.BookDto;
import pl.programisci.task_crud.model.Book;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class BookDtoMapperTest {

    @InjectMocks
    BookDtoMapper bookDtoMapper;

    OffsetDateTime offsetDateTimeNow = OffsetDateTime.now();

    Book book = new Book(1, "Książka1", 12345, 1, 1, offsetDateTimeNow,
            offsetDateTimeNow, 1, 1);
    BookDto bookDto = new BookDto(1, "Książka1", 12345, 1, 1);

    @Test
    public void shouldGetBookAndReturnFullBookDto() {
        //when
        BookDto bookDto1 = bookDtoMapper.mapToBookDto(book);

        //then
        Assertions.assertThat(bookDto1).isExactlyInstanceOf(BookDto.class);
    }

}
