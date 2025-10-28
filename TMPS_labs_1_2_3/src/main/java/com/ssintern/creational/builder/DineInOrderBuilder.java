package com.ssintern.creational.builder;

import com.ssintern.domain.models.Order;
import com.ssintern.domain.models.OrderType;

public class DineInOrderBuilder implements OrderBuilder {
    private Order order;

    public DineInOrderBuilder() {
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
        order.setOrderType(OrderType.DINE_IN);
    }

    @Override
    public void buildTableId() {
        order.setTableId(1000); // Placeholder, should be set to actual table ID, by object pool
        // the occupation of table is handled by Restaurant class
    }

    @Override
    public void buildDeliveryAddress() {
        order.setDeliveryAddress(null);
    }

    @Override
    public Order getOrder() {
        return order;
    }
}
