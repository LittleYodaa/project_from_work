package pl.programisci.task_crud.exception;

public class InvalidBookParametersException extends RuntimeException{
    public InvalidBookParametersException(){
        super("Book should have exactly 4 parameters");
    }
}