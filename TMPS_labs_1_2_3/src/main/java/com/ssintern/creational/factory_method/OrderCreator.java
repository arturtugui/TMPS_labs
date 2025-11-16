package com.ssintern.creational.factory_method;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class OrderCreator<T> {
    public static final AtomicInteger idCounter = new AtomicInteger(0);

    public abstract Order createOrder(T params);

    public Order processOrder(T params) {
        Order order = createOrder(params);
        order.setId(idCounter.incrementAndGet());
        return order;
    }

}
