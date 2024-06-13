package pl.programisci.task_crud.exception;

public class GenreForeignKeyReferenceException extends RuntimeException{
    public GenreForeignKeyReferenceException() {
        super("Genre has foreign key reference. Unable to delete.");
    }
}
