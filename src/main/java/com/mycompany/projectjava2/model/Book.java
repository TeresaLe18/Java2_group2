/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectjava2.model;

/**
 *
 * @author WINDOWS
 */

public class Book {
    private int bookId;
    private String title;
    private String author;
    private String category;
    private int quantity;
    private int publishYear;

    public Book() {}

    public Book(int bookId, String title, String author,
                String category, int quantity, int publishYear) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.quantity = quantity;
        this.publishYear = publishYear;
    }

    public Book(String title, String author,
                String category, int quantity, int publishYear) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.quantity = quantity;
        this.publishYear = publishYear;
    }

    // Getters & Setters
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getPublishYear() { return publishYear; }
    public void setPublishYear(int publishYear) { this.publishYear = publishYear; }
}

