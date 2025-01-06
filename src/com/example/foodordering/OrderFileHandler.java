package com.example.foodordering;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrderFileHandler {
    private static final String ORDER_FILE = "src/data/orders.txt";

    public static List<Order> findOrdersByUsername(String username) {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String orderUsername = parts[0];
                if (orderUsername.equals(username)) {
                    Order order = createOrderFromLine(parts);
                    orders.add(order);
                }
            }
        } catch (IOException e) {
            System.err.println("Error occurred while reading orders: " + e.getMessage());
            e.printStackTrace();
        }
        return orders;
    }

    public static List<Order> getAllOrders() {
        List<Order> allOrders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Order order = createOrderFromLine(line.split(","));
                allOrders.add(order);
            }
        } catch (IOException e) {
            System.err.println("Error occurred while reading orders: " + e.getMessage());
        }
        return allOrders;
    }

    public static void saveOrder(Order order) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDER_FILE, true))) {
            String orderData = getOrderData(order);
            writer.write(orderData);
            writer.newLine();
            System.out.println("Order saved successfully.");
        } catch (IOException e) {
            System.err.println("Error occurred while saving order: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void deleteOrder(Order orderToDelete) {
        List<Order> allOrders = getAllOrders();
        Iterator<Order> iterator = allOrders.iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            if (order.getCustomerUsername().equals(orderToDelete.getCustomerUsername()) &&
                order.getDeliveryAddress().equals(orderToDelete.getDeliveryAddress())) {
                System.out.println("Found order to delete: " + order);
                iterator.remove();
                break;
            }
        }
        saveOrders(allOrders);
    }

    private static void saveOrders(List<Order> orders) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDER_FILE))) {
            for (Order order : orders) {
                String orderData = getOrderData(order);
                writer.write(orderData);
                writer.newLine();
            }
            System.out.println("Orders saved successfully.");
        } catch (IOException e) {
            System.err.println("Error occurred while saving orders: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Order createOrderFromLine(String[] parts) {
        String customerUsername = parts[0];
        List<Product> products = new ArrayList<>();
        for (int i = 1; i < parts.length - 4; i += 3) {
            String productName = parts[i];
            double productPrice = Double.parseDouble(parts[i + 1]);
            int productQuantity = Integer.parseInt(parts[i + 2]);
            products.add(new Product(productName, productPrice, productQuantity, productName, productQuantity));
        }
        String street = parts[parts.length - 3];
        String district = parts[parts.length - 2];
        String postalCode = parts[parts.length - 1];
        Address deliveryAddress = new Address(street, district, postalCode);
        return new Order(customerUsername, products, deliveryAddress);
    }

    private static String getOrderData(Order order) {
        StringBuilder orderData = new StringBuilder();
        orderData.append(order.getCustomerUsername()).append(",");
        for (Product product : order.getProducts()) {
            orderData.append(product.getName()).append(",").append(product.getPrice()).append(",").append(product.getQuantity()).append(",");
        }
        orderData.append(order.getTotalPrice()).append(",");

        orderData.append(order.getDeliveryAddress().getStreet()).append(",");
        orderData.append(order.getDeliveryAddress().getdistrict()).append(",");
        orderData.append(order.getDeliveryAddress().getPostalCode());
        return orderData.toString();
    }
}

