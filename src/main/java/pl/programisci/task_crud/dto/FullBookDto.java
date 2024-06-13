package pl.programisci.task_crud.dto;

import lombok.Builder;

import java.io.Serializable;

//change OffsetDateTime because of invalid format for Apache Commons Lang

@Builder
public record FullBookDto(Integer bookId,
                          String name,
                          Integer isbn,
                          Integer authorId,
                          Integer genreId,
                          String dbCreateTime,
                          String dbUpdateTime,
                          Integer version) implements Serializable {
}
