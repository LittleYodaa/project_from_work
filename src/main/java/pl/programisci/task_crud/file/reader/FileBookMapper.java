package pl.programisci.task_crud.file.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.programisci.task_crud.exception.InvalidBookParametersException;
import pl.programisci.task_crud.model.Book;

@Slf4j
@Service
public class FileBookMapper {
    private final static int BOOK_VALID_NUMBER_OF_PARAMETERS = 4;

    public Book map(String[] input) {
        if (input.length == BOOK_VALID_NUMBER_OF_PARAMETERS) {
            return new Book(input[0], Integer.valueOf(input[1]), Integer.valueOf(input[2]), Integer.valueOf(input[3]));
        } else {
            throw new InvalidBookParametersException();
        }
    }
}
