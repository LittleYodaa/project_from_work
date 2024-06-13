package pl.programisci.task_crud.dto.mapper;
import org.springframework.stereotype.Service;
import pl.programisci.task_crud.dto.FullGenreDto;
import pl.programisci.task_crud.dto.SaveGenreDto;
import pl.programisci.task_crud.model.Genre;

@Service
public class GenreDtoMapper {
    public Genre map(SaveGenreDto saveGenreDto) {
        return Genre.builder()
                .type(saveGenreDto.type())
                .build();
    }

    public FullGenreDto mapToFullGenreDto(Genre genre) {
        return FullGenreDto.builder()
                .genreId(genre.getGenreId())
                .type(genre.getType())
                .dbCreationDate(genre.getDbCreationDate().toString())
                .dbUpdateDate(genre.getDbUpdateDate().toString())
                .version(genre.getVersion())
                .build();
    }
}
