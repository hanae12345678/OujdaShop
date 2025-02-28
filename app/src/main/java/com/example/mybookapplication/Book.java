package com.example.mybookapplication;

public class Book {
    private int id;
    private String name;
    private String author;
    private String description;
    private double price;
    private String image; // Conserver le champ "image"
    private int categoryId;

    // Constructeur
    public Book(int id, String name, String author, String description, double price, String image, int categoryId) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.price = price;
        this.image = image; // Conserver l'image
        this.categoryId = categoryId;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}