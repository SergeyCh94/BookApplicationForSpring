package com.skypro.library.dao;

import com.skypro.library.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (isbn, title, author, year) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPublication_year());
        return book;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, year = ? WHERE isbn = ?";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublication_year(), book.getIsbn());
        return book;
    }

    @Override
    public void delete(String isbn) {
        String sql = "DELETE FROM books WHERE isbn = ?";
        jdbcTemplate.update(sql, isbn);
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, new BookRowMapper());
    }

    @Override
    public Book findByIsbn(String isbn) {
        String sql = "SELECT * FROM books WHERE isbn = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{isbn}, new BookRowMapper());
    }

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setIsbn(rs.getString("isbn"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setPublication_year(rs.getInt("publication_year"));
            return book;
        }
    }
}
