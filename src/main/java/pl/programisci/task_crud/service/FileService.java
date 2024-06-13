package pl.programisci.task_crud.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import pl.programisci.task_crud.dto.FullBookDto;
import pl.programisci.task_crud.dto.FullGenreDto;
import pl.programisci.task_crud.file.writer.BookExcelWriter;
import pl.programisci.task_crud.file.writer.BookFileWriter;
import pl.programisci.task_crud.file.writer.GenreExcelWriter;
import pl.programisci.task_crud.file.writer.GenreFileWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class FileService {
    private final MenuService menuService;
    private final BookFileWriter bookFileWriter;
    private final GenreFileWriter genreFileWriter;
    private final BookService bookService;
    private final GenreService genreService;
    private final BookExcelWriter bookExcelWriter;
    private final GenreExcelWriter genreExcelWriter;

    public FileService(MenuService menuService, BookFileWriter bookFileWriter, GenreFileWriter genreFileWriter, BookService bookService, GenreService genreService, BookExcelWriter bookExcelWriter, GenreExcelWriter genreExcelWriter) {
        this.menuService = menuService;
        this.bookFileWriter = bookFileWriter;
        this.genreFileWriter = genreFileWriter;
        this.bookService = bookService;
        this.genreService = genreService;
        this.bookExcelWriter = bookExcelWriter;
        this.genreExcelWriter = genreExcelWriter;
    }

    public void writeToFile() {
        Map<String, List<String>> fileTypeAndName = menuService.runMenu();
        List<FullGenreDto> allGenres = genreService.getAllGenres();
        List<FullBookDto> allBooks = bookService.getAllBooks();
        if (fileTypeAndName.containsKey("File")) {
            genreFileWriter.writeTextFile(allGenres, fileTypeAndName.get("File").get(0));
            bookFileWriter.writeTextFile(allBooks, fileTypeAndName.get("File").get(1));
        } else if (fileTypeAndName.containsKey("Excel")) {
            Workbook workbook = new XSSFWorkbook();
            genreExcelWriter.saveExcelFile(workbook, allGenres, fileTypeAndName.get("Excel").get(0));
            bookExcelWriter.saveExcelFile(workbook, allBooks, fileTypeAndName.get("Excel").get(1));

            String fileLocation = "C:\\Users\\patryk.kawula\\IdeaProjects\\task_crud\\temp.xlsx";
            try {
                FileOutputStream outputStream = new FileOutputStream(fileLocation);
                workbook.write(outputStream);
                workbook.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
