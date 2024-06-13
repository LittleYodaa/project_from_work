package pl.programisci.task_crud.dto;

import lombok.Builder;

@Builder
public record GenreDto(Integer genreId, String type) {
}
