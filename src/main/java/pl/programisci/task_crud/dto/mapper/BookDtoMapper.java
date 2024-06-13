package pl.programisci.task_crud.dto.mapper;

import org.springframework.stereotype.Service;
import pl.programisci.task_crud.dto.BookDto;
import pl.programisci.task_crud.dto.FullBookDto;
import pl.programisci.task_crud.model.Book;

@Service
public class BookDtoMapper {
    public BookDto mapToBookDto(Book book) {
        return BookDto.builder()
                .bookId(book.getBookId())
                .name(book.getName())
                .isbn(book.getIsbn())
                .authorId(book.getAuthorId())
                .genreId(book.getGenreId())
                .build();

    }

    public FullBookDto mapToFullBookDto(Book book) {
        return FullBookDto.builder()
                .bookId(book.getBookId())
                .name(book.getName())
                .isbn(book.getIsbn())
                .authorId(book.getAuthorId())
                .genreId(book.getGenreId())
                .dbCreateTime(book.getDbCreationTime().toString())
                .dbUpdateTime(book.getDbModifyTime().toString())
                .version(book.getVersion())
                .build();
    }
}
