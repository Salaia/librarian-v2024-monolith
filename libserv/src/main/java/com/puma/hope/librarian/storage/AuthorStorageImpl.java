package com.puma.hope.librarian.storage;

import com.puma.hope.librarian.exception.EntityNotFoundException;
import com.puma.hope.librarian.model.Author;
import com.puma.hope.librarian.storage.face.AuthorStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AuthorStorageImpl implements AuthorStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorStorageImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Author> findAll() {
        String sql = "select * from librarian.authors";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeAuthor(rs));
    }

    @Override
    public Author findById(Long id) {
        String sql = "select * from librarian.authors where author_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeAuthor(rs), id)
                .stream().findAny().orElseThrow(() -> new EntityNotFoundException("Author with id " + id + " not found"));
    }

    @Override
    public Author addAuthor(Author author) {

        String sql = "insert into librarian.authors (name)" +
                "values (?) ";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"author_id"});
            stmt.setString(1, author.getName());
            return stmt;
        }, keyHolder);

        int key = keyHolder.getKey().intValue();

        return jdbcTemplate.query("select * from librarian.authors where author_id = ?", (rs, rowNum) -> makeAuthor(rs), key)
                .stream().findAny().orElse(null);

    }

    @Override
    public Author updateAuthor(Author author) {

        String sql = "update librarian.authors set " +
                "name = ? " +
                "where author_id = ?";

        int checkNum = jdbcTemplate.update(sql,
                author.getName(),
                author.getId());

        if (checkNum == 0) {
            throw new EntityNotFoundException("Author with id" + author.getId() + " not found");
        }

        return jdbcTemplate.query("select * from librarian.authors where author_id = ?", (rs, rowNum) -> makeAuthor(rs), author.getId())
                .stream().findAny().orElse(null);
    }

    @Override
    public void deleteAuthor(Long id) {
        String sqlDeleteLink = "delete from librarian.book_authors where author_id = ?";
        jdbcTemplate.update(sqlDeleteLink, id);
        String sqlDeleteAuthor = "delete from librarian.authors where author_id = ? ";
        jdbcTemplate.update(sqlDeleteAuthor, id);
    }

    @Override
    public void checkAuthorExistence(Long id) {
        final String sql = "select COUNT(d.author_id) from librarian.authors as d where author_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        if (count == null || count == 0) {
            throw new EntityNotFoundException("Author with id \"" + id + "\" not found.");
        }
    }

    private Author makeAuthor(ResultSet rs) throws SQLException {
        return Author.builder()
                .id(rs.getLong("author_id"))
                .name(rs.getString("name"))
                .build();
    }
}
