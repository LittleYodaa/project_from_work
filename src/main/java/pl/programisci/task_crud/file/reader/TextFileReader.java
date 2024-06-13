package pl.programisci.task_crud.file.reader;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public abstract class TextFileReader<T> {
    public List<T> readFile() {
        try {
            return Files.readAllLines(path())
                    .stream()
                    .map(s -> s.split("[|]"))
                    .map(this::map)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract T map(String[] strings);

    protected abstract Path path();
}

