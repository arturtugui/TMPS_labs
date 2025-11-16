package com.ssintern.creational.factory_method;

public class DeliveryOrderCreator extends OrderCreator<String> {
    public Order createOrder(String address) {
        return new DeliveryOrder(address);
    }
}
