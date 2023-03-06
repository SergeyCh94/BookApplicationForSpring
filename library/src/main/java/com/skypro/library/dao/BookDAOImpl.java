package com.skypro.library.dao;

import com.skypro.library.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        return jdbcTemplate.query(query, new BookMapper());
    }

    @Override
    public Book findByIsbn(String isbn) {
        String query = "SELECT * FROM books WHERE isbn=?";
        return jdbcTemplate.queryForObject(query, new Object[]{isbn}, new BookMapper());
    }

    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (isbn, title, author, publication_year) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(query, book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPublication_year());
        return book;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title=?, author=?, publication_year=? WHERE isbn=?";
        jdbcTemplate.update(query, book.getTitle(), book.getAuthor(), book.getPublication_year(), book.getIsbn());
        return book;
    }

    @Override
    public void delete(String isbn) {
        String query = "DELETE FROM books WHERE isbn=?";
        jdbcTemplate.update(query, isbn);
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            String isbn = rs.getString("isbn");
            String title = rs.getString("title");
            String author = rs.getString("author");
            int publicationYear = rs.getInt("publication_year");
            return new Book(isbn, title, author, publicationYear);
        }
    }
}

