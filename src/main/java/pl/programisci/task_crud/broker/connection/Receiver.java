package pl.programisci.task_crud.broker.connection;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import pl.programisci.task_crud.dto.SaveGenreDto;
import pl.programisci.task_crud.service.GenreService;

@Configuration
public class Receiver {
    private final GenreService genreService;

    public Receiver(GenreService genreService) {
        this.genreService = genreService;
    }

    @RabbitListener(queues = "myQueue")
    public void listen(SaveGenreDto saveGenreDto) {
        genreService.addGenre(saveGenreDto);
    }
}
