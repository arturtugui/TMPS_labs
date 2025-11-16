package com.ssintern.creational.factory_method;

public class TakeawayOrderCreator extends OrderCreator<Void> {
    @Override
    public Order createOrder(Void unused) {
        return new TakeawayOrder();
    }
}
