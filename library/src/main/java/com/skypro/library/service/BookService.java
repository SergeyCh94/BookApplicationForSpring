package com.skypro.library.service;

import com.skypro.library.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book createBook(Book book);

    Book updateBook(String isbn, Book book);

    void deleteBook(String isbn);

    Book getBookByIsbn(String isbn);

}
