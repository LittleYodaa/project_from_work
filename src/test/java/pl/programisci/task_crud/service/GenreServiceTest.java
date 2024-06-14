package pl.programisci.task_crud.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.programisci.task_crud.dto.FullGenreDto;
import pl.programisci.task_crud.dto.SaveGenreDto;
import pl.programisci.task_crud.dto.mapper.GenreDtoMapper;
import pl.programisci.task_crud.model.Genre;
import pl.programisci.task_crud.repository.GenreDao;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {

    @Mock
    GenreDao genreDao;
    @Mock
    GenreDtoMapper genreDtoMapper;
    @Mock
    SaveGenreDto saveGenreDto;
    @Captor
    ArgumentCaptor<Genre> genreArgumentCaptor;
    @Captor
    ArgumentCaptor<String> typeArgumentCaptor;

    @InjectMocks
    GenreService genreService;

    OffsetDateTime offsetDateTimeNow = OffsetDateTime.now();
    String offsetDateTimeNowString = offsetDateTimeNow.toString();

    Genre genre1 = new Genre(1, "Opowiadanie", offsetDateTimeNow, offsetDateTimeNow, 1);
    Genre genre2 = new Genre(2, "Romans", offsetDateTimeNow, offsetDateTimeNow, 1);

    FullGenreDto fullGenreDto1 = new FullGenreDto(1, "Opowiadanie", offsetDateTimeNowString, offsetDateTimeNowString, 1);
    FullGenreDto fullGenreDto2 = new FullGenreDto(2, "Romans", offsetDateTimeNowString, offsetDateTimeNowString, 1);
    SaveGenreDto saveGenreDto1 = new SaveGenreDto("Opowiadanie");

    private Genre genre;


    @Test
    void getAllGenres_shouldReturnListOfFullGenreDto() {
        //given
        when(genreDao.getGenres()).thenReturn(Arrays.asList(genre1, genre2));
        when(genreDtoMapper.mapToFullGenreDto(genre1)).thenReturn(fullGenreDto1);
        when(genreDtoMapper.mapToFullGenreDto(genre2)).thenReturn(fullGenreDto2);

        //when
        List<FullGenreDto> result = genreService.getAllGenres();

        //then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(fullGenreDto1, fullGenreDto2);

        verify(genreDao, times(1)).getGenres();
        verify(genreDtoMapper, times(1)).mapToFullGenreDto(genre1);
        verify(genreDtoMapper, times(1)).mapToFullGenreDto(genre2);
    }

    @Test
    void getAllGenres_shouldReturnEmptyListWhenNoGenres() {
        //given
        when(genreDao.getGenres()).thenReturn(Arrays.asList());

        //when
        List<FullGenreDto> result = genreService.getAllGenres();

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void genreAdd_shouldMapSaveGenreDtoToGenre() {
        //given
        when(genreDtoMapper.map(saveGenreDto1)).thenReturn(genre);

        //when
        genreService.addGenre(saveGenreDto1);

        //then
        verify(genreDao).addGenre(genreArgumentCaptor.capture());
        Genre capturedGenre = genreArgumentCaptor.getValue();
        assertThat(capturedGenre).isSameAs(genre);

    }

    @ParameterizedTest
    @ValueSource(ints = {1,2})
    void updateGenre_shouldUpdateGenreById(int number) {
        // Given
        when(saveGenreDto.type()).thenReturn("Rock");

        // When
        genreService.updateGenre(number, saveGenreDto);

        // Then
        verify(genreDao, times(1)).updateGenre(eq(number), typeArgumentCaptor.capture());
        String value = typeArgumentCaptor.getValue();
        assertThat(value).isEqualTo("Rock");
    }

    @Test
    void deleteGenre_shouldDeleteGenreById() {
        // Given
        Integer genreId = 1;

        // When
        genreService.deleteGenre(genreId);

        // Then
        verify(genreDao, times(1)).deleteGenre(genreId);
    }

    @Test
    void findGenreById_shouldMapGenreToFullGenreDto() {
        //given
        Integer genreId = 1;
        when(genreDao.getGenre(genreId)).thenReturn(genre1);
        when(genreDtoMapper.mapToFullGenreDto(genre1)).thenReturn(fullGenreDto1);

        //when
        FullGenreDto fullGenreDto = genreService.findGenreById(genreId);

        //then
        verify(genreDtoMapper).mapToFullGenreDto(genreArgumentCaptor.capture());
        Genre capturedGenre = genreArgumentCaptor.getValue();
        assertThat(genre1).isSameAs(capturedGenre);

        verify(genreDao, times(1)).getGenre(genreId);
    }

}
