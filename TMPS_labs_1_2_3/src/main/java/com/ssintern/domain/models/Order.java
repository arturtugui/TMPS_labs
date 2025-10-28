package com.ssintern.domain.models;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private List<MenuItem> items;
    private OrderType orderType;
    // OPTIONAL
    private int tableId; // applicable for DINE_IN orders
    private String deliveryAddress; // applicable for DELIVERY orders

    public Order(int id, OrderType orderType) {
        setId(id);
        this.items = new ArrayList<MenuItem>();
        this.orderType = orderType;
        this.tableId = -1;
        this.deliveryAddress = null;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive.");
        }
        this.id = id;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setTableId(int tableId) {
        if( orderType != OrderType.DINE_IN) {
            throw new IllegalStateException("Table ID can only be set for DINE_IN orders.");
        }
        this.tableId = tableId;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        if( orderType != OrderType.DELIVERY) {
            throw new IllegalStateException("Delivery address can only be set for DELIVERY orders.");
        }
        this.deliveryAddress = deliveryAddress;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", items=" + items +
                ", orderType=" + orderType +
                ", tableId=" + tableId +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                '}';
    }
}
