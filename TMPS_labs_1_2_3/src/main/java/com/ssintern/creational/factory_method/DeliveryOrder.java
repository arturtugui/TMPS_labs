package com.ssintern.creational.factory_method;

public class DeliveryOrder extends Order{
    private String deliveryAddress;

    public DeliveryOrder(String deliveryAddress) {
        super();
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    @Override
    public String getOrderDetails() {
        return "DeliveryOrder [id=" + getId() + ", items=" + getItems() + ", deliveryAddress=" + deliveryAddress + "]";
    }
}
