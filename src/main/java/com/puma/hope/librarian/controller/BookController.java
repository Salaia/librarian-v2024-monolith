package com.puma.hope.librarian.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.puma.hope.librarian.model.Book;
import com.puma.hope.librarian.service.BookService;

import java.util.List;

@RestController
@Tag(name = "Books", description = "Requests for books")
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    BookService bookService;

    @PostMapping
    @Operation(summary = "Add a new book to the DataBase")
    public Book create(@Valid @RequestBody Book book) {
        return bookService.create(book);
    }

    @PutMapping
    @Operation(summary = "Update a book")
    public Book update(@Valid @RequestBody Book book) {
        return bookService.update(book);
    }

    @GetMapping
    @Operation(summary = "Get all books")
    public List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book by ID")
    public Book findBookById(@PathVariable("id") Long id) {
        return bookService.findBookById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    @Operation(summary = "Add like to the book (using book ID and user ID")
    public Book addLike(@PathVariable("id") @Positive Long bookId,
                        @PathVariable("userId") @Positive Long userId) {
        return bookService.addLike(bookId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    @Operation(summary = "Remove like from the book (using book ID and user ID")
    public Book removeLike(
            @PathVariable("id") @Positive Long bookId,
            @PathVariable("userId") Long userId) {
        return bookService.removeLike(bookId, userId);
    }

    @GetMapping("/popular")
    @Operation(summary = "Get most popular books. Returns 10 if other isn't specified")
    public List<Book> findPopularBooks(
            @RequestParam(defaultValue = "10", required = false) @PositiveOrZero Integer count) {
        return bookService.findPopularBooks(count);
    }
}