package pl.programisci.task_crud.service;

import org.springframework.stereotype.Service;
import pl.programisci.task_crud.dto.BookDto;
import pl.programisci.task_crud.dto.FullGenreDto;
import pl.programisci.task_crud.dto.SaveGenreDto;
import pl.programisci.task_crud.dto.mapper.BookDtoMapper;
import pl.programisci.task_crud.dto.mapper.GenreDtoMapper;
import pl.programisci.task_crud.model.Book;
import pl.programisci.task_crud.model.Genre;
import pl.programisci.task_crud.repository.GenreDao;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {
    private final BookDtoMapper bookDtoMapper;
    private final GenreDtoMapper genreDtoMapper;
    private final GenreDao genreDao;

    public GenreService(BookDtoMapper bookDtoMapper, GenreDtoMapper saveGenreDtoMapper, GenreDao genreDao) {
        this.bookDtoMapper = bookDtoMapper;
        this.genreDtoMapper = saveGenreDtoMapper;
        this.genreDao = genreDao;
    }

    public void addGenre(SaveGenreDto saveGenreDto) {
        Genre genreToAdd = genreDtoMapper.map(saveGenreDto);
        genreDao.addGenre(genreToAdd);
    }

    public FullGenreDto findGenreById(Integer genreId) {
        Genre genreToFind = genreDao.getGenre(genreId);
        return genreDtoMapper.mapToFullGenreDto(genreToFind);
    }


    public void deleteGenre(Integer genreId) {
        genreDao.deleteGenre(genreId);
    }

    public void updateGenre(Integer genreId, SaveGenreDto saveGenreDto) {
        String type = saveGenreDto.type();
        genreDao.updateGenre(genreId, type);
    }

    public List<BookDto> getBooksByGenre(String name) {
        List<Book> books = genreDao.getBooksByGenre(name);
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books) {
            bookDtos.add(bookDtoMapper.mapToBookDto(book));
        }
        return bookDtos;
    }

    public List<FullGenreDto> getAllGenres() {
        List<Genre> genres = genreDao.getGenres();
        List<FullGenreDto> genresDto = new ArrayList<>();
        for (Genre genre : genres) {
            FullGenreDto fullGenreDto = genreDtoMapper.mapToFullGenreDto(genre);
            genresDto.add(fullGenreDto);
        }
        return genresDto;
    }
}
