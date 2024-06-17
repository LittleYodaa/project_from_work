package pl.programisci.task_crud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.programisci.task_crud.file.writer.BookFileWriter;
import pl.programisci.task_crud.ftp.FtpDirectory;
import pl.programisci.task_crud.ftp.FtpFile;
import pl.programisci.task_crud.service.BookService;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {
    private BookService bookService;
    private BookFileWriter bookFileWriter;
    private FtpDirectory ftpDirectory;
    private FtpFile ftpFile;

    public BookController(BookService bookService, BookFileWriter bookFileWriter, FtpDirectory ftpDirectory, FtpFile ftpFile) {
        this.bookService = bookService;
        this.bookFileWriter = bookFileWriter;
        this.ftpDirectory = ftpDirectory;
        this.ftpFile = ftpFile;
    }

    @PostMapping("/createFtpDirectory")
    ResponseEntity<?> createDirectoryInFtp() {
        ftpDirectory.createDirectory("/input");
        ftpDirectory.createDirectory("/archive");
        ftpFile.downloadFile();
        ftpFile.moveFileToArchiveDirectory();
        bookService.fillTableWithBookFromFtpFile();
        return ResponseEntity.ok().build();
    }

    @PostMapping
    ResponseEntity<?> addBooksFromFile() {
        bookService.fillTableWithBook();
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/saveBookToFile")
//    ResponseEntity<?> saveGenreToFile() {
//        List<FullBookDto> allBooks = bookService.getAllBooks();
//        bookFileWriter.writeTextFile(allBooks);
//        log.info("All books written to file");
//        return ResponseEntity.ok().build();
//    }
}
