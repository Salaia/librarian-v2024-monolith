package com.puma.hope.librarian.storage.face;

import com.puma.hope.librarian.model.Book;

import java.util.List;

public interface BookStorage {
    Book create(Book book);

    Book update(Book book);

    List<Book> findAllBooks();

    Book findBookById(Long id);

    Book addLike(Long bookId, Long userId);

    Book removeLike(Long bookId, Long userId);

    void checkBookExistence(Long id);

}
