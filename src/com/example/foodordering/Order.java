package com.example.foodordering;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId; 
    private String customerUsername;
    private List<Product> products;
    private Address deliveryAddress;
    private double totalPrice;
   
    public Order(String customerUsername, List<Product> products, Address deliveryAddress) {
        this.customerUsername = customerUsername;
        this.products = products;
        this.deliveryAddress = deliveryAddress;
        this.totalPrice = calculateTotalPrice();
        
    }

    
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCustomerUsername() {
        return this.customerUsername;
    }

    public Address getDeliveryAddress() {
        return this.deliveryAddress;
    }

    public void addProduct(Product product) {
        if (products == null) {
            products = new ArrayList<>();
        }
        products.add(product);
    }

    public void setDeliveryAddress(Address address) {
        this.deliveryAddress = address;
    }

    public double calculateTotalPrice() {
        double totalPrice = 0;
        if (products != null) {
            for (Product product : products) {
                totalPrice += product.getPrice() * product.getQuantity();
            }
        }
        return totalPrice;
    }



    public List<Product> getProducts() {
        return this.products;
    }
    
    public String getProductListAsString() {
        StringBuilder productListString = new StringBuilder();
        for (Product product : products) {
            productListString.append(product.getName()).append(" - ").append(product.getPrice()).append(" X ").append(product.getQuantity()).append("\n");
        }
        return productListString.toString();
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Order{");
        stringBuilder.append("orderId=").append(orderId).append(", ");
        stringBuilder.append("customerUsername='").append(customerUsername).append('\'').append(", ");
        stringBuilder.append("products=[");
        if (products != null) {
            for (Product product : products) {
                stringBuilder.append(product).append(", ");
            }
        }
        stringBuilder.append("]").append(", ");
        stringBuilder.append("deliveryAddress=").append(deliveryAddress);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
