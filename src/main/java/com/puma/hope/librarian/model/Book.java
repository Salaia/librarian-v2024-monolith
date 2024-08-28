package com.puma.hope.librarian.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {

    @Positive
    @Schema(example = "1")
    Long id;

    @NotBlank(message = "Book name may not be only space symbols!")
    @NotEmpty(message = "Book name may not be empty!")
    @Schema(example = "Poetriana", description = "Book name may not be empty or blank")
    String name;

    @Size(max = 200, message = "Book description max length is 200 symbols.")
    @Schema(example = "My own poetry book")
    String description;

    @Schema(example = "2007-05-27")
    LocalDate releaseDate;

    @Positive(message = "Book numberOfPages has to be positive!")
    @Schema(example = "100")
    int numberOfPages;

    final LinkedHashSet<Genre> genres = new LinkedHashSet<>();

    int rate; //TODO рейтинг сделать бы расчетным, а то "книга хорошая, Админ сказал!"
    // пока единственная мысль, как это сделать - ввести ДТО на вход(без рейтинга) и выход (с ним)

    final Set<Long> likes = new HashSet<>();
    final Set<Author> authors = new HashSet<>();

}
