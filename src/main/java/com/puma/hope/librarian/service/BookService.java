package com.puma.hope.librarian.service;

import com.puma.hope.librarian.exception.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.puma.hope.librarian.model.Book;
import com.puma.hope.librarian.storage.BookStorage;
import com.puma.hope.librarian.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookService {
    final BookStorage bookStorage;
    final UserStorage userStorage;

    public BookService(@Qualifier("bookStorageImpl") BookStorage bookStorage, @Qualifier("userStorageImpl") UserStorage userStorage) {
        this.bookStorage = bookStorage;
        this.userStorage = userStorage;
    }

    public Book addLike(Long bookId, Long userId) {
        userStorage.checkUserExistence(userId);
        bookStorage.checkBookExistence(bookId);

        bookStorage.addLike(bookId, userId);
        log.info("Like was added to book " + bookId);
        return bookStorage.findBookById(bookId);
    }

    public Book removeLike(Long bookId, Long userId) {
        userStorage.checkUserExistence(userId);
        bookStorage.checkBookExistence(bookId);
        bookStorage.removeLike(bookId, userId);
        log.info("Like was removed from book " + bookId);
        return bookStorage.findBookById(bookId);
    }

    public List<Book> findPopularBooks(Integer count) {
        List<Book> books = bookStorage.findAllBooks();
        if (books.isEmpty()) {
            String message = "No books in our DataBase yet.";
            log.debug(message);
            throw new EntityNotFoundException(message);
        }
        return books.stream()
                .sorted((book1, book2) -> (book2.getRate() - book1.getRate()))
                .limit(count)
                .collect(Collectors.toList());
    }

    public Book create(Book book) {
        log.info("Book " + book.getName() + " was successfully saved!");
        return bookStorage.create(book);
    }

    public Book update(Book book) {
        bookStorage.checkBookExistence(book.getId()); // NotFoundException
        log.info("Book " + book.getName() + " was successfully updated!");
        return bookStorage.update(book);
    }

    public List<Book> findAllBooks() {
        return bookStorage.findAllBooks();
    }

    public Book findBookById(Long id) {
        bookStorage.checkBookExistence(id);
        return bookStorage.findBookById(id);
    }
}