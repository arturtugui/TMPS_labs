package com.ssintern.creational.builder;

import com.ssintern.domain.models.Order;
import com.ssintern.domain.models.OrderType;

public class TakeawayOrderBuilder implements OrderBuilder {
    private Order order;

    public TakeawayOrderBuilder() {
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
        order.setOrderType(OrderType.TAKEAWAY);
    }

    @Override
    public void buildTableId() {
        order.setTableId(null);
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
