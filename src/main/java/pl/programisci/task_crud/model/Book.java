package pl.programisci.task_crud.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;
    private String name;
    private Integer isbn;
    private Integer authorId;
    private Integer genreId;
    private OffsetDateTime dbCreationTime;
    private OffsetDateTime dbModifyTime;
    private Integer version;
    private Integer publishingId;

    public Book(String name, Integer isbn, Integer authorId, Integer genreId) {
        this.name = name;
        this.isbn = isbn;
        this.authorId = authorId;
        this.genreId = genreId;
    }

    public Book(Integer bookId, String name, Integer isbn, Integer authorId, Integer genreId) {
        this.bookId = bookId;
        this.name = name;
        this.isbn = isbn;
        this.authorId = authorId;
        this.genreId = genreId;
    }

    public Book(Integer bookId, String name, Integer isbn, Integer authorId, Integer genreId, OffsetDateTime dbCreationTime, OffsetDateTime dbModifyTime, Integer version, Integer publishingId) {
        this.bookId = bookId;
        this.name = name;
        this.isbn = isbn;
        this.authorId = authorId;
        this.genreId = genreId;
        this.dbCreationTime = dbCreationTime;
        this.dbModifyTime = dbModifyTime;
        this.version = version;
        this.publishingId = publishingId;
    }
}
