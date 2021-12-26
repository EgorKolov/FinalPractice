package com.company;

public final class Product {
    private final String name;
    private final String article;
    private final double price;
    private final int inStock;
    
    public Product(String name, String article, double price, int inStock) {
        this.name = name;
        this.article = article;
        this.price = price;
        this.inStock = inStock;
    }
    
    public String getName() {
        return name;
    }
    
    public String getArticle() {
        return article;
    }
    
    public double getPrice() {
        return price;
    }
    
    public int getInStock() {
        return inStock;
    }
    
    @Override
    public String toString() {
        return "('" + name + "', '" + article  + "', " + price + ", " + inStock + ")";
    }
}
