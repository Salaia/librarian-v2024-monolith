package com.puma.hope.librarian.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
//@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Author {
    @Positive
    Long id;

    @NotBlank(message = "Field \"name\" can't consist of space symbols.")
    @NotEmpty(message = "Field \"name\" can't be empty.")
    @NotNull(message = "Field \"name\" can't be null.")
    String name;


    public Author() {
    }

    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
