package pl.programisci.task_crud.file.reader;

import pl.programisci.task_crud.model.Author;

import java.nio.file.Path;

public class AuthorFileReader extends TextFileReader<Author> {
    private final FileAuthorMapper authorMapper;

    public AuthorFileReader(FileAuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }

    @Override
    protected Author map(String[] strings) {
        return authorMapper.map(strings);
    }

    @Override
    protected Path path() {
        return Path.of("C:/Users/patryk.kawula/IdeaProjects/task/autor.txt");
    }
}
