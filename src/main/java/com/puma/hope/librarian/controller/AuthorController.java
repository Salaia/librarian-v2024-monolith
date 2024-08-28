package com.puma.hope.librarian.controller;

import com.puma.hope.librarian.model.Author;
import com.puma.hope.librarian.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "Authors", description = "Requests for authors")
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    @Operation(summary = "Get all authors")
    public List<Author> getAuthors() {
        List<Author> authors = authorService.getAuthors();
        log.info("Get authors list");
        return authors;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an author by ID")
    public Author getAuthorById(@PathVariable Long id) {
        Author author = authorService.getAuthorById(id);
        log.info("Get author with id{}", id);
        return author;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new author to the DataBase")
    public Author addAuthor(@Valid @RequestBody Author author) {
        Author newAuthor = authorService.addAuthor(author);
        log.info("Add author with id{}", newAuthor.getId());
        return newAuthor;
    }

    @PutMapping
    @Operation(summary = "Update author")
    public Author updateAuthor(@Valid @RequestBody Author author) {
        Author updatedAuthor = authorService.updateAuthor(author);

        log.info("Update author with id{}", author.getId());
        return updatedAuthor;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an author from the DataBase by author's ID")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        log.info("Remove author with id{}", id);
    }

}
