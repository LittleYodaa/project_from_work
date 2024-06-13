package pl.programisci.task_crud.exception;

public class GenreNotFoudException extends RuntimeException{
    public GenreNotFoudException() {
        super("Genre not found.");
    }
}
