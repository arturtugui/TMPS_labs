package com.ssintern.creational.factory_method;

import com.ssintern.creational.builderAlternative.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Order {
    private static final AtomicInteger orderIdCounter = new AtomicInteger(0);

    private int id;
    private List<MenuItem> items;

    // No-arg constructor for Builder pattern
    public Order() {
        this.id = orderIdCounter.incrementAndGet(); // Auto-generate ID
        this.items = new ArrayList<>();
    }

    public abstract String getOrderDetails();

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

    protected String formatItems() {
        StringBuilder sb = new StringBuilder();
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
        return sb.toString();
    }
}
