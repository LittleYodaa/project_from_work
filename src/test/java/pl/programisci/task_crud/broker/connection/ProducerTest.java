package pl.programisci.task_crud.broker.connection;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import pl.programisci.task_crud.dto.SaveGenreDto;
import pl.programisci.task_crud.dto.mapper.GenreDtoMapper;
import pl.programisci.task_crud.model.Genre;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProducerTest {

    @Mock
    GenreDtoMapper genreDtoMapper;

    @Mock
    RabbitTemplate rabbitTemplate;

    @Captor
    ArgumentCaptor<String> exchangeCaptor;

    @Captor
    ArgumentCaptor<String> routingKeyCaptor;

    @Captor
    ArgumentCaptor<Genre> genreCaptor;

    @InjectMocks
    Producer producer;

    OffsetDateTime offsetDateTimeNow = OffsetDateTime.now();

    SaveGenreDto saveGenreDto = new SaveGenreDto("Kryminał");
    Genre genre = new Genre(1, "Kryminał", offsetDateTimeNow, offsetDateTimeNow, 1);

    @Test
    public void shouldInvokeSendingMethodFromRabbitTemplate() {
        //given
        when(genreDtoMapper.map(saveGenreDto)).thenReturn(genre);

        //when
        producer.sendMessage(saveGenreDto);

        //then
        verify(rabbitTemplate, times(1)).convertAndSend("defaultDirect", "orange", genre);
        verify(rabbitTemplate, times(1)).convertAndSend(exchangeCaptor.capture(), routingKeyCaptor.capture(), genreCaptor.capture());
        String exchangeCaptorValue = exchangeCaptor.getValue();
        String routingKeyCaptorValue = routingKeyCaptor.getValue();
        Genre genreCaptorValue = genreCaptor.getValue();

        assertThat(exchangeCaptorValue).isEqualTo("defaultDirect");
        assertThat(routingKeyCaptorValue).isEqualTo("orange");
        assertThat(genreCaptorValue).isEqualTo(genre);
    }
}
