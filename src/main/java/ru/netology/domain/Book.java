package ru.netology.domain;

public class Book extends Product {
    private String author;

    public Book(int id, String title, String author, int price) {
        super(id, title, price);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return getName();
    }
}
