package pl.programisci.task_crud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.programisci.task_crud.service.FileService;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/saveDataToFile")
    ResponseEntity<?> saveGenreToFile() {
        fileService.writeToFile();
        log.info("All data written to file");
        return ResponseEntity.ok().build();
    }
}
