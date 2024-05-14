package com.amaap.books.entity;

import org.springframework.data.annotation.Id;

public class Book {
    @Id
    private String name;
    private String author;
    private String publication;
    private double price;

    public Book() {
    }

    public Book(String name, String author, String publication, double price) {
        this.name = name;
        this.author = author;
        this.publication = publication;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
