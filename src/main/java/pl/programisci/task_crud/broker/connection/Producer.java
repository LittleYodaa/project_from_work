package pl.programisci.task_crud.broker.connection;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import pl.programisci.task_crud.dto.SaveGenreDto;
import pl.programisci.task_crud.dto.mapper.GenreDtoMapper;
import pl.programisci.task_crud.model.Genre;

@Service
public class Producer {
    private final GenreDtoMapper genreDtoMapper;
    private final RabbitTemplate rabbitTemplate;

    public Producer(GenreDtoMapper genreDtoMapper, RabbitTemplate rabbitTemplate) {
        this.genreDtoMapper = genreDtoMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(SaveGenreDto saveGenreDto){
        Genre genreToSave = genreDtoMapper.map(saveGenreDto);
        rabbitTemplate.convertAndSend("defaultDirect", "orange", genreToSave);
    }
}
