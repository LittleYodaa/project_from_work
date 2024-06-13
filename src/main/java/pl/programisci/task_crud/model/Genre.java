package pl.programisci.task_crud.model;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
public class Genre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer genreId;
    private String type;
    private OffsetDateTime dbCreationDate;
    private OffsetDateTime dbUpdateDate;
    private Integer version;

    public Genre(Integer genreId, String type, OffsetDateTime dbCreationDate, OffsetDateTime dbUpdateDate, Integer version) {
        this.genreId = genreId;
        this.type = type;
        this.dbCreationDate = dbCreationDate;
        this.dbUpdateDate = dbUpdateDate;
        this.version = version;
    }

    public Genre(String type) {
        this.type = type;
    }

    public Genre(Integer genreId, String type) {
        this.genreId = genreId;
        this.type = type;
    }
}
