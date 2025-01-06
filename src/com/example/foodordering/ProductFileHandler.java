package com.example.foodordering;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductFileHandler {
    private static final String PRODUCT_FILE = "src/data/products.txt";

    public static void saveProduct(Product product) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCT_FILE, true))) {
        	writer.write(product.getName() + "," + product.getPrice() + "," + product.getStock() + "," + product.getCategory() + "," + product.getQuantity());
        	writer.newLine();
            System.out.println("Product saved successfully.");
        } catch (IOException e) {
            System.err.println("Error occurred while saving product: " + e.getMessage());
        }
    }

    public static void removeProduct(String productName) {
        List<Product> products = viewAllProducts(); 

        products.removeIf(product -> product.getName().equals(productName));

        writeProductsToFile(products);
        System.out.println("Product removed successfully.");
    }

    public static void updateProduct(String productName, double newPrice, int newStock, String newCategory, int quantityToAdd) {
        List<Product> products = viewAllProducts();
        boolean productFound = false;
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                product.setPrice(newPrice);
                product.setStock(newStock);
                if (newCategory != null && !newCategory.isEmpty()) {
                    product.setCategory(newCategory);
                }
                product.setQuantity(product.getQuantity() + quantityToAdd); 
                productFound = true;
                break;
            }
        }
        if (productFound) {
            writeProductsToFile(products);
            System.out.println("Product updated successfully.");
        } else {
            System.err.println("Product not found with name: " + productName);
        }
    }



    public static List<Product> viewAllProducts() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String name = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    int stock = Integer.parseInt(parts[2]);
                    String category = parts[3];
                    int quantity = Integer.parseInt(parts[4]);
                    Product product = new Product(name, price, stock, category, quantity);
                    products.add(product);
                } else {
                    System.err.println("Invalid product format: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error occurred while reading products: " + e.getMessage());
        }
        return products;
    }


    public static Product findProductByName(String productName) {
        List<Product> products = viewAllProducts(); 

        for (Product product : products) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null; 
    }

    private static void writeProductsToFile(List<Product> products) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCT_FILE))) {
            for (Product product : products) {
                writer.write(product.getName() + "," + product.getPrice() + "," + product.getStock() + "," + product.getCategory() + "," + product.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error occurred while writing products to file: " + e.getMessage());
        }
    }
}
