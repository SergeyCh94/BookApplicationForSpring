package com.skypro.library.dao;

import com.skypro.library.entity.Book;

import java.util.List;

public interface BookDAO {
    Book create(Book book);
    Book update(Book book);
    void delete(String isbn);
    List<Book> findAll();
    Book findByIsbn(String isbn);
}
