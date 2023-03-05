package com.skypro.library.service;

import com.skypro.library.dao.BookDAO;
import com.skypro.library.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    private final BookDAO bookDAO;

    @Autowired
    public BookServiceImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDAO.findAll();
    }

    @Override
    public Book createBook(Book book) {
        validateBook(book);
        return bookDAO.create(book);
    }

    @Override
    public Book updateBook(String isbn, Book book) {
        Book editableBook = this.bookDAO.findByIsbn(book.getIsbn());
        editableBook.setAuthor(book.getAuthor());
        editableBook.setTitle(book.getTitle());
        editableBook.setPublication_year(book.getPublication_year());
        validateBook(editableBook);
        bookDAO.update(book);
        return book;
    }

    @Override
    public void deleteBook(String isbn) {
        bookDAO.delete(isbn);
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return bookDAO.findByIsbn(isbn);
    }

    private void validateBook(Book book) {
        // Check that all fields are filled in
        if (book.getAuthor().isEmpty() || book.getTitle().isEmpty() || book.getIsbn().isEmpty()) {
            throw new IllegalArgumentException("All book fields must be filled in");
        }

        // Check that the year is not negative
        if (book.getPublication_year() < 0) {
            throw new IllegalArgumentException("The year cannot be negative");
        }

        // Check the validity of the ISBN using the checksum calculation algorithm
        String isbn = book.getIsbn().replaceAll("-", "");
        if (isbn.length() != 13) {
            throw new IllegalArgumentException("ISBN must be 13 digits long");
        }

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            sum += (i % 2 == 0) ? digit * 3 : digit;
        }
        int checksum = (10 - (sum % 10)) % 10;
        int lastDigit = Character.getNumericValue(isbn.charAt(12));
        if (checksum != lastDigit) {
            throw new IllegalArgumentException("Invalid ISBN");
        }
    }
}
