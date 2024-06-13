package pl.programisci.task_crud.exception;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(){
        super("Book not found.");
    }
}
