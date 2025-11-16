package com.ssintern.creational.factory_method;

public class DineInOrderCreator extends OrderCreator<Integer> {
    @Override
    public Order createOrder(Integer tableId) {
        return new DineInOrder(tableId);
    }
}
