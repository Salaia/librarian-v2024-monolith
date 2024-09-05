package com.puma.hope.librarian.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.puma.hope.librarian.model.Genre;
import com.puma.hope.librarian.service.GenreService;

import java.util.List;

@RestController
@Tag(name = "Genres", description = "Requests for genres")
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    @Operation(summary = "Get all genres")
    public List<Genre> findAll() {
        return genreService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a genre by ID")
    public Genre findGenreById(@PathVariable("id") @Positive Long id) {
        return genreService.findGenreById(id);
    }

}
