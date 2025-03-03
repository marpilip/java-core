package org.example.tests;

public class Order {
    private final int id;
    private final String productName;
    private final int quantity;
    private final double unitPrice;

    public Order(int id, String productName, int quantity, double unitPrice) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return unitPrice * quantity;
    }
}
