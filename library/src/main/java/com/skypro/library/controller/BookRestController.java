package com.skypro.library.controller;

import com.skypro.library.entity.Book;
import com.skypro.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    @Autowired
    private BookService bookService;

    @GetMapping("")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }


    @PostMapping("")
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping("/{isbn}")
    public Book updateBook(@PathVariable("isbn") String isbn, @RequestBody Book book) {
        return bookService.updateBook(isbn, book);
    }

    @DeleteMapping("/{isbn}")
    public void deleteBook(@PathVariable("isbn") String isbn) {
        bookService.deleteBook(isbn);
    }
}
