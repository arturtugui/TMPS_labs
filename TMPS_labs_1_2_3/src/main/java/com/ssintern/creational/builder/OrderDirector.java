package com.ssintern.creational.builder;

import com.ssintern.domain.models.Order;

public class OrderDirector {
    private OrderBuilder orderBuilder;

    public OrderDirector(OrderBuilder orderBuilder) {
        this.orderBuilder = orderBuilder;
    }

    public void constructOrder() {
        orderBuilder.buildId();
        orderBuilder.buildItems();
        orderBuilder.buildOrderType();
        orderBuilder.buildTableId();
        orderBuilder.buildDeliveryAddress();
    }

    public Order getOrder() {
        return orderBuilder.getOrder();
    }
}
