package com.puma.hope.librarian.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.puma.hope.librarian.model.Genre;
import com.puma.hope.librarian.storage.GenreStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreStorage genreStorage;

    public List<Genre> findAll() {
        return genreStorage.findAll();
    }

    public Genre findGenreById(Long id) {
        genreStorage.checkGenreExistence(id);
        return genreStorage.findGenreById(id);
    }
}
