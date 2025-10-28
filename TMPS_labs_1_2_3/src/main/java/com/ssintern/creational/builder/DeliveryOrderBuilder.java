package com.ssintern.creational.builder;

import com.ssintern.domain.models.Order;
import com.ssintern.domain.models.OrderType;

public class DeliveryOrderBuilder implements OrderBuilder {
    private Order order;

    public DeliveryOrderBuilder() {
        this.order = new Order();
    }

    @Override
    public void buildId() {
        order.setId(Order.idCounter.incrementAndGet());
    }

    @Override
    public void buildItems() {
        // Items list already initialized in Order constructor
    }

    @Override
    public void buildOrderType() {
        order.setOrderType(OrderType.DELIVERY);
    }

    @Override
    public void buildTableId() {
        order.setTableId(null);
    }

    @Override
    public void buildDeliveryAddress() {
        order.setDeliveryAddress("123 Main St, Cityville"); // Placeholder, should be set to actual address
    }

    @Override
    public Order getOrder() {
        return order;
    }
}
