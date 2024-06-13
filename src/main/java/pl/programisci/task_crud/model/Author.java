package pl.programisci.task_crud.model;

public record Author (Integer authorId, String firstname, String lastname) {
    public Author(String firstname, String lastname){
        this(null, firstname, lastname);
    }
}
