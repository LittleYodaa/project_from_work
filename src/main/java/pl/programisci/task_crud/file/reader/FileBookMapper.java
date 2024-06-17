package pl.programisci.task_crud.file.reader;

import org.springframework.stereotype.Service;
import pl.programisci.task_crud.model.Book;

@Service
public class FileBookMapper {
    public Book map(String[] input) {
        return new Book(input[0], Integer.valueOf(input[1]), Integer.valueOf(input[2]), Integer.valueOf(input[3]));
    }

    //sprawdzanie formatu
}
