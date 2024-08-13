package com.puma.hope.librarian.storage;

import com.puma.hope.librarian.exception.EntityNotFoundException;
import com.puma.hope.librarian.storage.face.GenreStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.puma.hope.librarian.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreStorageImpl implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> findAll() {
        String sql = "select * from librarian.genre";
        return jdbcTemplate.query(sql, this::mapRowToGenre);
    }

    @Override
    public Genre findGenreById(Long id) {
        final String sql = "select * from librarian.genre where genre_id = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToGenre, id);
    }

    private Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getLong("genre_id"))
                .name(resultSet.getString("name"))
                .build();
    }

    @Override
    public void checkGenreExistence(Long id) {
        final String sql = "select COUNT(g.genre_id) " +
                "from librarian.genre as g " +
                "where g.genre_id = ? ";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        if (count == null || count == 0) {
            throw new EntityNotFoundException("Genre with id \"" + id + "\" not found.");
        }
    }
}
