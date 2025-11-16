package com.ssintern.creational.factory_method;

public class TakeawayOrder extends Order{
    @Override
    public String getOrderDetails() {
        return "TakeawayOrder [id=" + getId() + ", items=" + getItems() + "]";
    }
}
