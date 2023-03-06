package com.skypro.library.controller;

import com.skypro.library.entity.Book;
import com.skypro.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public String showBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books-list";
    }

    @GetMapping("")
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "dashboard";
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        return "add_book";
    }

    @PostMapping("/add")
    public String saveBook(@ModelAttribute("book") @Validated Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "add_book";
        }
        bookService.createBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{isbn}")
    public String editBook(@PathVariable("isbn") String isbn, Model model) {
        Book book = bookService.getBookByIsbn(isbn);
        if (book != null) {
            model.addAttribute("book", book);
            return "edit_book";
        }
        return "redirect:/books";
    }

    @PostMapping("/edit/{isbn}")
    public String updateBook(@PathVariable("isbn") String isbn, @ModelAttribute("book") @Validated Book book,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "edit_book";
        }
        bookService.updateBook(isbn, book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{isbn}")
    public String deleteBook(@PathVariable("isbn") String isbn) {
        bookService.deleteBook(isbn);
        return "redirect:/books";
    }
}
