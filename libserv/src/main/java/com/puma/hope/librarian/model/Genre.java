package com.puma.hope.librarian.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
/**
 *  Жанры на данный момент загружаются файлом data.sql
 */
public class Genre {
    @Schema(example = "1")
    Long id;
    @Schema(example = "Поэзия")
    String name;

    public Genre() {
    }

    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
