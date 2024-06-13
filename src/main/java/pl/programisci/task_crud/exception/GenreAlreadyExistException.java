package pl.programisci.task_crud.exception;

import org.springframework.dao.DataAccessException;

public class GenreAlreadyExistException extends RuntimeException {
    public GenreAlreadyExistException(DataAccessException e){
        super("Genre with this type already exists.");
    }
}
