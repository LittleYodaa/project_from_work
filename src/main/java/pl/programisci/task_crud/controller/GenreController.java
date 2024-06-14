package pl.programisci.task_crud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.programisci.task_crud.broker.connection.Producer;
import pl.programisci.task_crud.dto.BookDto;
import pl.programisci.task_crud.dto.FullGenreDto;
import pl.programisci.task_crud.dto.SaveGenreDto;
import pl.programisci.task_crud.model.Genre;
import pl.programisci.task_crud.service.GenreService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genre")
public class GenreController {
    private final GenreService genreService;
    private final Producer producer;

    public GenreController(GenreService genreService, Producer producer) {
        this.genreService = genreService;
        this.producer = producer;
    }

    @GetMapping("/{genreId}")
    ResponseEntity<FullGenreDto> getGenreById(@PathVariable Integer genreId) {
        return ResponseEntity.ok(genreService.findGenreById(genreId));
    }

    @PostMapping("/addByRabbit")
    ResponseEntity<?> addGenreByRabbit(@RequestBody SaveGenreDto genreDto) {
        producer.sendMessage(genreDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    ResponseEntity<?> addGenre(@RequestBody SaveGenreDto saveGenreDto) {
        genreService.addGenre(saveGenreDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{genreId}")
    ResponseEntity<?> deleteGenre(@PathVariable Integer genreId) {
        genreService.deleteGenre(genreId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update/{genreId}")
    ResponseEntity<Genre> updateGenre(@PathVariable Integer genreId, @RequestBody SaveGenreDto saveGenreDto) {
        genreService.updateGenre(genreId, saveGenreDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/allGenreBook/{name}")
    ResponseEntity<List<BookDto>> getAllGenreBook(@PathVariable String name) {
        return ResponseEntity.ok(genreService.getBooksByGenre(name));
    }

//    @GetMapping("/saveGenreToFile")
//    ResponseEntity<?> saveGenreToFile() {
//        List<FullGenreDto> allGenres = genreService.getAllGenres();
//        genreFileWriter.writeTextFile(allGenres);
//        log.info("All genres written to file");
//        return ResponseEntity.ok().build();
//    }

//    @GetMapping("/saveGenreToExcel")
//    ResponseEntity<?> saveGenreToExcel() {
//        List<FullGenreDto> allGenres = genreService.getAllGenres();
//        excelFileWriter.saveExcelFile(allGenres);
//        log.info("All genres written to excel");
//        return ResponseEntity.ok().build();
//    }
}
