package pl.programisci.task_crud.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record SaveGenreDto (String type) implements Serializable{
}
