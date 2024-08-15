package com.puma.hope.librarian.model;

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
    Long id;

    @NotBlank(message = "Book name may not be only space symbols!")
    @NotEmpty(message = "Book name may not be empty!")
    String name;

    @Size(max = 200, message = "Book description max length is 200 symbols.")
    String description;

    LocalDate releaseDate;

    @Positive(message = "Book numberOfPages has to be positive!")
    int numberOfPages;

    final LinkedHashSet<Genre> genres = new LinkedHashSet<>();

    int rate; //TODO рейтинг сделать бы расчетным, а то "книга хорошая, Админ сказал!"

    final Set<Long> likes = new HashSet<>();
    final Set<Author> authors = new HashSet<>();

}
