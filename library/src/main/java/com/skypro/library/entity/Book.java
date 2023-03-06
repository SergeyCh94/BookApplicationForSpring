package com.skypro.library.entity;


public class Book {
    private String isbn;
    private String title;
    private String author;
    private int publication_year;

    public Book() {
    }

    public Book(String isbn, String title, String author, int publication_year) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publication_year = publication_year;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(int publication_year) {
        this.publication_year = publication_year;
    }
}
