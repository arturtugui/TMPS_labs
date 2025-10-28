package com.ssintern.creational.builder;

import com.ssintern.domain.models.Order;

public interface OrderBuilder {
    void buildId();
    void buildItems();
    void buildOrderType();
    void buildTableId();
    void buildDeliveryAddress();
    Order getOrder();
}
