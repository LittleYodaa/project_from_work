package pl.programisci.task_crud.file.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pl.programisci.task_crud.dto.FullGenreDto;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class GenreFileWriter {
    public void writeTextFile(List<FullGenreDto> input, String fileName) {
            File file = new File(file());
            if (!file.exists()) {
                file.delete();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                objectMapper.writeValue(new File(file().concat(fileName)), input);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }

    protected String file() {
        return "C:\\Users\\patryk.kawula\\IdeaProjects\\task_crud\\";
    }
}
