package com.example.foodordering;

public class Product {
    private String name;
    private double price;
    private int stock;
    private String category;
    private int quantity;

    public Product(String name, double price, int stock, String category, int quantity) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.quantity = quantity;
    }

    public Product(Product product) {
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void increaseQuantity(int quantityToAdd) {
        this.quantity += quantityToAdd;
    }


    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
