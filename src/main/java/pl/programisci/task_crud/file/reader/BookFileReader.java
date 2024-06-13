package pl.programisci.task_crud.file.reader;

import org.springframework.stereotype.Service;

import pl.programisci.task_crud.model.Book;

import java.nio.file.Path;

@Service
public class BookFileReader extends TextFileReader<Book> {

    private final FileBookMapper bookMapper;

    public BookFileReader(FileBookMapper fileBookMapper) {
        this.bookMapper = fileBookMapper;
    }

    @Override
    protected Book map(String[] input) {
        return bookMapper.map(input);
    }

    @Override
    protected Path path() {
        return Path.of("C:/Users/patryk.kawula/IdeaProjects/task/książka.txt");
    }
}
