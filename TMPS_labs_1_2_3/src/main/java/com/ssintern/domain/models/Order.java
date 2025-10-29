package com.ssintern.domain.models;

import com.ssintern.creational.builderAlternative.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {
    public static final AtomicInteger idCounter = new AtomicInteger(0);

    private int id;
    private List<MenuItem> items;
    private OrderType orderType;
    // OPTIONAL
    private Integer tableId; // applicable for DINE_IN orders
    private String deliveryAddress; // applicable for DELIVERY orders

    // No-arg constructor for Builder pattern
    public Order() {
        this.items = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        this.items.add(item);
    }

    public double calculateTotal() {
        double total = 0.0;
        for (MenuItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive.");
        }
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== Order #").append(id).append(" ==========\n");
        sb.append("Type: ").append(orderType).append("\n");

        if (tableId != null) {
            sb.append("Table: ").append(tableId).append("\n");
        }
        if (deliveryAddress != null) {
            sb.append("Delivery Address: ").append(deliveryAddress).append("\n");
        }

        sb.append("\nItems:\n");
        if (items.isEmpty()) {
            sb.append("  (no items)\n");
        } else {
            for (int i = 0; i < items.size(); i++) {
                MenuItem item = items.get(i);
                sb.append("  ").append(i + 1).append(". ").append(item.getName())
                        .append(" - $").append(String.format("%.2f", item.getPrice())).append("\n");
                sb.append("     ").append(item.getDescription()).append("\n");
                if (item.getIngredients() != null && !item.getIngredients().isEmpty()) {
                    sb.append("     Ingredients: ").append(String.join(", ", item.getIngredients())).append("\n");
                }
            }
        }

        sb.append("\nTotal: $").append(String.format("%.2f", calculateTotal())).append("\n");
        sb.append("================================\n");
        return sb.toString();
    }
}
