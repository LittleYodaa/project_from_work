package pl.programisci.task_crud.file.reader;

import org.springframework.stereotype.Service;
import pl.programisci.task_crud.model.Book;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class FtpFileReader extends TextFileReader<Book>{
    private final FileBookMapper fileBookMapper;

    public FtpFileReader(FileBookMapper fileBookMapper) {
        this.fileBookMapper = fileBookMapper;
    }

    @Override
    protected Book map(String[] strings) {
            return fileBookMapper.map(strings);
    }

    @Override
    protected Path path() {
        return Path.of("C:\\Users\\patryk.kawula\\IdeaProjects\\task_crud\\temporary");
    }
}
