ALTER TABLE dbo.genre
    ADD [genre_create_time] [DATETIMEOFFSET] DEFAULT SYSDATETIMEOFFSET() NOT NULL;

ALTER TABLE dbo.genre
    ADD [genre_modify_time] [DATETIMEOFFSET] DEFAULT SYSDATETIMEOFFSET() NOT NULL;

ALTER TABLE dbo.genre
    ADD [version] [INT] DEFAULT 1 NOT NULL;

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[genre_update]
    @genreId INT,
    @type VARCHAR(255)
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE genre
    SET type = @type,
        version = version + 1
    WHERE @genreId = genre_id;
END;

    USE [taskdb]
GO
/****** Object:  StoredProcedure [dbo].[genre_add]    Script Date: 10.06.2024 11:38:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[genre_add] (
    @type VARCHAR(50)
)
AS
    IF NOT EXISTS (
        SELECT 1
        FROM dbo.genre
        WHERE type = @type
    )
        BEGIN
            INSERT INTO dbo.genre (type)
            VALUES (@type)
        END

    ALTER TABLE dbo.book
        ADD publishing_id INT;

    ALTER TABLE dbo.book
        ADD CONSTRAINT FK_book_publishing_house FOREIGN KEY (publishing_id)
            REFERENCES dbo.publishing_house(publishing_id)


