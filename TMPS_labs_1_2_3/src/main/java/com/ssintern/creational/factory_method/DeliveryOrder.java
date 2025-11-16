package com.ssintern.creational.factory_method;

public class DeliveryOrder extends Order {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== Order #").append(getId()).append(" ==========\n");
        sb.append("Type: DELIVERY\n");
        sb.append("Address: ").append(deliveryAddress).append("\n");
        sb.append(formatItems());
        sb.append("\nTotal: $").append(String.format("%.2f", calculateTotal())).append("\n");
        sb.append("================================\n");
        return sb.toString();
    }
}
