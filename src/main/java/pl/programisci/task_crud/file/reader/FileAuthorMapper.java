package pl.programisci.task_crud.file.reader;

import pl.programisci.task_crud.model.Author;

public class FileAuthorMapper {
    public Author map(String[] input) {
        return new Author(input[0], input[1]);
    }
}
