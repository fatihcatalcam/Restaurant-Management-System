package com.example.foodordering.GUI;

import javax.swing.*;

import com.example.foodordering.Address;
import com.example.foodordering.Order;
import com.example.foodordering.OrderFileHandler;
import com.example.foodordering.Product;
import com.example.foodordering.ProductFileHandler;
import com.example.foodordering.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainMenuGUI extends JFrame {
    private User currentUser;
    private static final long serialVersionUID = 1L;
    private List<Product> selectedProducts = new ArrayList<>();

    public MainMenuGUI(User user) {
        this.currentUser = user;

        setTitle("Doy Doy - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        JLabel titleLabel = new JLabel("Doy Doy");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);

        JButton viewProductsButton = new JButton("View Products");
        customizeMainMenuButton(viewProductsButton);
        viewProductsButton.addActionListener(e -> openProductCategoriesWindow());

        JButton placeOrderButton = new JButton("Place Order");
        customizeMainMenuButton(placeOrderButton);
        placeOrderButton.addActionListener(e -> placeOrder());

        JButton viewOrdersButton = new JButton("View Orders");
        customizeMainMenuButton(viewOrdersButton);
        viewOrdersButton.addActionListener(e -> viewOrders());

        JButton adminOptionsButton = new JButton("Admin Options");
        customizeMainMenuButton(adminOptionsButton);
        adminOptionsButton.addActionListener(e -> {
            if (currentUser != null && currentUser.isAdmin()) {
                adminOptions();
            } else {
                JOptionPane.showMessageDialog(MainMenuGUI.this, "You are not authorized to access admin options.", "Access Denied", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton logoutButton = new JButton("Logout");
        customizeMainMenuButton(logoutButton);
        logoutButton.addActionListener(e -> {
            currentUser = null;
            openLoginGUI();
        });

        panel.add(viewProductsButton);
        panel.add(placeOrderButton);
        panel.add(viewOrdersButton);
        panel.add(adminOptionsButton);
        panel.add(logoutButton);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panel);

        setLocationRelativeTo(null);
    }

    private void customizeMainMenuButton(JButton button) {
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 40));
    }

    private void openProductCategoriesWindow() {
        JFrame categoryFrame = new JFrame("Product Categories");
        categoryFrame.setSize(550, 400);
        categoryFrame.setLayout(new GridLayout(5, 1));

        JButton mainCourseButton = new JButton("Main Course");
        customizeSubMenuButton(mainCourseButton);
        mainCourseButton.addActionListener(e -> openProductListWindow("Main Course"));

        JButton appetizerButton = new JButton("Appetizer");
        customizeSubMenuButton(appetizerButton);
        appetizerButton.addActionListener(e -> openProductListWindow("Appetizer"));

        JButton soupButton = new JButton("Soup");
        customizeSubMenuButton(soupButton);
        soupButton.addActionListener(e -> openProductListWindow("Soup"));

        JButton beverageButton = new JButton("Beverage");
        customizeSubMenuButton(beverageButton);
        beverageButton.addActionListener(e -> openProductListWindow("Beverage"));

        JButton dessertButton = new JButton("Dessert");
        customizeSubMenuButton(dessertButton);
        dessertButton.addActionListener(e -> openProductListWindow("Dessert"));

        categoryFrame.add(mainCourseButton);
        categoryFrame.add(appetizerButton);
        categoryFrame.add(soupButton);
        categoryFrame.add(beverageButton);
        categoryFrame.add(dessertButton);

        categoryFrame.setLocationRelativeTo(null);
        categoryFrame.setVisible(true);
    }

    private void customizeSubMenuButton(JButton button) {
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 40));
    }

    private void openProductListWindow(String category) {
        JFrame productListFrame = new JFrame(category + " Products");
        productListFrame.setSize(400, 300);

        JPanel productListPanel = new JPanel();
        productListPanel.setLayout(new GridLayout(0, 1));

        List<Product> products = ProductFileHandler.viewAllProducts();
        for (Product product : products) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                JPanel productPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JCheckBox checkBox = new JCheckBox(product.getName() + " - Price: $" + product.getPrice());
                JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
                productPanel.add(checkBox);
                productPanel.add(new JLabel("Quantity:"));
                productPanel.add(quantitySpinner);
                productListPanel.add(productPanel);
            }
        }

        JButton placeOrderButton = new JButton("Add to Basket");
        placeOrderButton.addActionListener(e -> placeOrderFromCheckBoxes(productListPanel, products));

        productListFrame.add(new JScrollPane(productListPanel), BorderLayout.CENTER);
        productListFrame.add(placeOrderButton, BorderLayout.SOUTH);

        productListFrame.setLocationRelativeTo(null);
        productListFrame.setVisible(true);
    }

    private void placeOrderFromCheckBoxes(JPanel productListPanel, List<Product> products) {
        Component[] components = productListPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                for (Component innerComponent : panel.getComponents()) {
                    if (innerComponent instanceof JCheckBox) {
                        JCheckBox checkBox = (JCheckBox) innerComponent;
                        if (checkBox.isSelected()) {
                            String productName = checkBox.getText().split(" - Price: ")[0];
                            for (Product product : products) {
                                if (product.getName().equals(productName)) {
                                    JSpinner quantitySpinner = (JSpinner) panel.getComponent(panel.getComponentCount() - 1);
                                    int quantity = (int) quantitySpinner.getValue();
                                    product.setQuantity(quantity);
                                    selectedProducts.add(product);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (selectedProducts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No products selected!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Products added to basket successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void placeOrder() {
        if (selectedProducts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No products in the basket!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String street = JOptionPane.showInputDialog("Enter street:");
            String district = JOptionPane.showInputDialog("Enter district:");
            String postalCode = JOptionPane.showInputDialog("Enter postal code:");

            if (street == null || street.isEmpty() || district == null || district.isEmpty() || postalCode == null || postalCode.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Street, district, or postal code cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Address deliveryAddress = new Address(street, district, postalCode);
            Order order = new Order(currentUser.getUsername(), selectedProducts, deliveryAddress);

            double totalPrice = 0;
            for (Product product : selectedProducts) {
                totalPrice += product.getPrice() * product.getQuantity();
            }

            JOptionPane.showMessageDialog(this, "Total Price: $" + totalPrice, "Order Summary", JOptionPane.INFORMATION_MESSAGE);
            OrderFileHandler.saveOrder(order);
            JOptionPane.showMessageDialog(this, "Order placed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            selectedProducts.clear();
        }
    }

    private void viewOrders() {
        List<Order> orders = OrderFileHandler.findOrdersByUsername(currentUser.getUsername());
        if (currentUser.isAdmin()) {
            orders = OrderFileHandler.getAllOrders();
        } else {

            orders = OrderFileHandler.findOrdersByUsername(currentUser.getUsername());
        }
        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You have no orders.", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JPanel ordersPanel = new JPanel();
            ordersPanel.setLayout(new BoxLayout(ordersPanel, BoxLayout.Y_AXIS));

            for (Order order : orders) {
                JPanel orderPanel = new JPanel(new BorderLayout());
                orderPanel.setBorder(BorderFactory.createEtchedBorder());

                double totalPrice = 0;
                for (Product product : order.getProducts()) {
                    totalPrice += product.getPrice() * product.getQuantity();
                }

                order.setTotalPrice(totalPrice);

                JLabel orderInfoLabel = new JLabel("<html><b>Customer Name:</b> " + order.getCustomerUsername() +
                        " | <b>Total Price:</b> $" + order.calculateTotalPrice() +
                        " | <b>Address:</b> " + order.getDeliveryAddress().toString() + "</html>");
                orderPanel.add(orderInfoLabel, BorderLayout.NORTH);

                JTextArea productListArea = new JTextArea(order.getProductListAsString());
                productListArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(productListArea);
                scrollPane.setPreferredSize(new Dimension(300, 100));
                orderPanel.add(scrollPane, BorderLayout.CENTER);

                ordersPanel.add(orderPanel);
                ordersPanel.add(Box.createVerticalStrut(10));
            }

            JScrollPane scrollPane = new JScrollPane(ordersPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            JFrame frame = new JFrame("Your Orders");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(scrollPane);
            frame.pack();
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);
            frame.setResizable(true);
        }
    }

    private void adminOptions() {
        JFrame adminFrame = new JFrame("Admin Options");
        adminFrame.setSize(300, 200);
        adminFrame.setLayout(new GridLayout(6, 1));

        JButton addProductButton = new JButton("Add Product");
        customizeMainMenuButton(addProductButton);
        addProductButton.addActionListener(e -> addProduct());

        JButton removeProductButton = new JButton("Remove Product");
        customizeMainMenuButton(removeProductButton);
        removeProductButton.addActionListener(e -> removeProduct());

        JButton updateProductButton = new JButton("Update Product");
        customizeMainMenuButton(updateProductButton);
        updateProductButton.addActionListener(e -> updateProduct());

        JPanel gapPanel1 = new JPanel();
        JPanel gapPanel2 = new JPanel();
        JPanel gapPanel3 = new JPanel();
        gapPanel1.setPreferredSize(new Dimension(10, 10));
        gapPanel2.setPreferredSize(new Dimension(10, 10));
        gapPanel3.setPreferredSize(new Dimension(10, 10));

        adminFrame.add(addProductButton);
        adminFrame.add(gapPanel1);
        adminFrame.add(removeProductButton);
        adminFrame.add(gapPanel2);
        adminFrame.add(updateProductButton);
        adminFrame.add(gapPanel3);

        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);
    }

    private void addProduct() {
        String name = JOptionPane.showInputDialog("Enter product name:");
        double price = Double.parseDouble(JOptionPane.showInputDialog("Enter product price:"));
        int stock = Integer.parseInt(JOptionPane.showInputDialog("Enter product stock:"));
        String category = JOptionPane.showInputDialog("Enter product category:");
        int quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter product quantity:"));

        Product product = new Product(name, price, stock, category, quantity);
        ProductFileHandler.saveProduct(product);
        JOptionPane.showMessageDialog(MainMenuGUI.this, "Product added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void removeProduct() {
        String productName = JOptionPane.showInputDialog("Enter product name to remove:");
        ProductFileHandler.removeProduct(productName);
        JOptionPane.showMessageDialog(MainMenuGUI.this, "Product removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateProduct() {
        String productName = JOptionPane.showInputDialog("Enter product name to update:");
        double newPrice = Double.parseDouble(JOptionPane.showInputDialog("Enter new price:"));
        int newStock = Integer.parseInt(JOptionPane.showInputDialog("Enter new stock:"));
        String newCategory = JOptionPane.showInputDialog("Enter new category:");
        int newQuantity = Integer.parseInt(JOptionPane.showInputDialog("Enter new quantity:"));

        ProductFileHandler.updateProduct(productName, newPrice, newStock, newCategory, newQuantity);
        JOptionPane.showMessageDialog(MainMenuGUI.this, "Product updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void openLoginGUI() {
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenuGUI mainMenuGUI = new MainMenuGUI(null);
            mainMenuGUI.setVisible(true);
        });
    }
}
