package com.puma.hope.librarian.storage.face;
import com.puma.hope.librarian.exception.ValidationExceptionCustom;
import com.puma.hope.librarian.model.Author;

import java.util.List;

public interface AuthorStorage {

    List<Author> findAll();

    Author findById(Long id);

    Author addAuthor(Author author) throws ValidationExceptionCustom;

    Author updateAuthor(Author author) throws ValidationExceptionCustom;

    void checkAuthorExistence(Long id);

    void deleteAuthor(Long id);

}
