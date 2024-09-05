package com.puma.hope.librarian.service;

import com.puma.hope.librarian.model.Author;
import com.puma.hope.librarian.storage.face.AuthorStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorStorage authorStorage;

    @Autowired
    public AuthorService(AuthorStorage authorStorage) {
        this.authorStorage = authorStorage;
    }

    public Author addAuthor(Author author) {
        return authorStorage.addAuthor(author);
    }

    public Author updateAuthor(Author author) {
        return authorStorage.updateAuthor(author);
    }

    public List<Author> getAuthors() {
        return authorStorage.findAll();
    }

    public Author getAuthorById(Long id) {
        return authorStorage.findById(id);
    }

    public void deleteAuthor(Long id) {
        authorStorage.deleteAuthor(id);
    }

}
