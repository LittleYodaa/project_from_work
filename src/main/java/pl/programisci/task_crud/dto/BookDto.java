package pl.programisci.task_crud.dto;

import lombok.Builder;

@Builder
public record BookDto(Integer bookId, String name, Integer isbn, Integer authorId, Integer genreId) {
}
