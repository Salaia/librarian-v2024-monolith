package com.puma.hope.librarian.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puma.hope.librarian.exception.EntityNotFoundException;
import com.puma.hope.librarian.exception.ValidationExceptionCustom;
import com.puma.hope.librarian.storage.face.BookStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import com.puma.hope.librarian.model.Book;
import com.puma.hope.librarian.model.Genre;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component("bookStorageImpl")
@RequiredArgsConstructor
public class BookStorageImpl implements BookStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Book create(Book book) throws ValidationExceptionCustom {
        final String sqlBook = "insert into librarian.books(name, description, release_date, duration, rate) " +
                "values(?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlBook, new String[]{"book_id"});
            stmt.setString(1, book.getName());
            stmt.setString(2, book.getDescription());
            stmt.setDate(3, Date.valueOf(book.getReleaseDate()));
            stmt.setInt(4, book.getNumberOfPages());
            stmt.setInt(5, book.getRate());
            return stmt;
        }, keyHolder);

        Long key = Objects.requireNonNull(keyHolder.getKey()).longValue();

        if (!book.getGenres().isEmpty()) {
            for (Genre genre : book.getGenres()) {
                final String sqlGenres = "insert into librarian.books_genre_link(book_id, genre_id) " +
                        "values(?,?);";
                jdbcTemplate.update(connection -> {
                    PreparedStatement stmt = connection.prepareStatement((sqlGenres));
                    stmt.setInt(1, key.intValue());
                    stmt.setInt(2, genre.getId().intValue());
                    return stmt;

                });
            }
        }
        return findBookById(key);
    }

    @Override
    public Book update(Book book) {
        String sqlBookUpdate = "update librarian.books set name = ?, " +
                "description = ?, release_date = ?, duration = ?, " +
                "rate = ?" +
                " where book_id = ?";

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlBookUpdate);
            stmt.setString(1, book.getName());
            stmt.setString(2, book.getDescription());
            stmt.setDate(3, Date.valueOf(book.getReleaseDate()));
            stmt.setInt(4, book.getNumberOfPages());
            stmt.setInt(5, book.getRate());
            stmt.setLong(6, book.getId());
            return stmt;
        });


        // удаление жанров этого фильма
        final String sqlGenresDelete = "delete from librarian.books_genre_link " +
                "where book_id = " + book.getId();
        jdbcTemplate.update(sqlGenresDelete);

        // Запись полученного списка жанров
        if (!book.getGenres().isEmpty()) {
            for (Genre genre : book.getGenres()) {
                final String sqlGenres = "insert into librarian.books_genre_link(book_id, genre_id) " +
                        "values(?,?);";
                jdbcTemplate.update(connection -> {
                    PreparedStatement stmt = connection.prepareStatement((sqlGenres));
                    stmt.setInt(1, book.getId().intValue());
                    stmt.setInt(2, genre.getId().intValue());
                    return stmt;
                });
            }
        }
        return findBookById(book.getId());
    }

    @Override
    public List<Book> findAllBooks() {

        final String sql = "select f.book_id, f.name as book_name, f.description, f.release_date, f.duration, " +
                "json_agg(json_build_object('id', g.genre_id, 'name', g.name)) as genres," +
                " COUNT(lk.user_id) as rate " +
                "from librarian.books as f " +
                "left join librarian.books_genre_link as fgl on f.book_id = fgl.book_id " +
                "left join librarian.genre as g on fgl.genre_id = g.genre_id " +
                "left join librarian.likes_books_users_link as lk on lk.book_id = f.book_id " +
                "group by f.book_id " +
                "order by f.book_id";

        return jdbcTemplate.query(sql, this::mapRowToBook);
    }

    @Override
    public Book findBookById(Long id) {

        final String sql = "select f.book_id, f.name as book_name, f.description, f.release_date, f.duration, " +
                "json_agg(json_build_object('id', g.genre_id, 'name', g.name)) as genres," +
                " COUNT(lk.user_id) as rate " +
                "from librarian.books as f " +
                "left join librarian.books_genre_link as fgl on f.book_id = fgl.book_id " +
                "left join librarian.genre as g on fgl.genre_id = g.genre_id " +
                "left join librarian.likes_books_users_link as lk on lk.book_id = f.book_id " +
                "where f.book_id = ? " +
                "group by f.book_id ";

        return jdbcTemplate.queryForObject(sql, this::mapRowToBook, id);
    }

    @Override
    public Book addLike(Long bookId, Long userId) {
        String sql = "insert into librarian.likes_books_users_link(book_id, user_id) " +
                "values(?,?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, bookId.intValue());
            stmt.setInt(2, userId.intValue());
            return stmt;
        });
        Book book = findBookById(bookId);
        book.setRate(book.getRate() + 1);
        return book;
    }

    @Override
    public Book removeLike(Long bookId, Long userId) {
        final String sql = "delete from librarian.likes_books_users_link " +
                "where book_id = ? and user_id = ?";
        jdbcTemplate.update(sql, bookId, userId);

        Book book = findBookById(bookId);
        book.setRate(book.getRate() - 1);
        return book;
    }

    @Override
    public void checkBookExistence(Long id) {
        final String sql = "select COUNT(f.book_id) " +
                "from librarian.books as f " +
                "where f.book_id = ? ";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        if (count == null || count == 0) {
            throw new EntityNotFoundException("Book with id \"" + id + "\" not found.");
        }
    }

    private Book mapRowToBook(ResultSet resultSet, int rowNum) throws SQLException {
        Book book = Book.builder()
                .id(resultSet.getLong("book_id"))
                .name(resultSet.getString("book_name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .numberOfPages(resultSet.getInt("duration"))
                .rate(resultSet.getInt("rate"))
                .build();

        String genresString = resultSet.getString("genres");

        final ObjectMapper objectMapper = new ObjectMapper();
        Genre[] genres = new Genre[10];
        try {
            genres = objectMapper.readValue(genresString, Genre[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Genre genre : genres) {
            if (genre != null) {
                if (genre.getId() != null)
                    book.getGenres().add(genre);
            }
        }
        return book;
    }

}
