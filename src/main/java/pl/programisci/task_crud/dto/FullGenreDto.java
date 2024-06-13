package pl.programisci.task_crud.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record FullGenreDto(Integer genreId,
                           String type,
                           String dbCreationDate,
                           String dbUpdateDate,
                           Integer version) implements Serializable {
}
