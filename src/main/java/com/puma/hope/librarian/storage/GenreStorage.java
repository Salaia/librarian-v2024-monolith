package com.puma.hope.librarian.storage;

import com.puma.hope.librarian.model.Genre;

import java.util.List;

public interface GenreStorage {
    List<Genre> findAll();

    Genre findGenreById(Long id);

    void checkGenreExistence(Long id);
}
