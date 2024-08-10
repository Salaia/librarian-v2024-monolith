package com.puma.hope.librarian.controller;

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
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    BookService bookService;

    @PostMapping
    public Book create(@Valid @RequestBody Book book) {
        return bookService.create(book);
    }

    @PutMapping
    public Book update(@Valid @RequestBody Book book) {
        return bookService.update(book);
    }

    @GetMapping
    public List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    public Book findBookById(@PathVariable("id") Long id) {
        return bookService.findBookById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Book addLike(@PathVariable("id") @Positive Long bookId,
                        @PathVariable("userId") @Positive Long userId) {
        return bookService.addLike(bookId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Book removeLike(
            @PathVariable("id") @Positive Long bookId,
            @PathVariable("userId") Long userId) {
        return bookService.removeLike(bookId, userId);
    }

    @GetMapping("/popular")
    public List<Book> findPopularBooks(
            @RequestParam(defaultValue = "10", required = false) @PositiveOrZero Integer count) {
        return bookService.findPopularBooks(count);
    }
}