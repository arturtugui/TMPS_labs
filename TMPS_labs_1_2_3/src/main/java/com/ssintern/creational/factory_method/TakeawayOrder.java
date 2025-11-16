package com.ssintern.creational.factory_method;

public class TakeawayOrder extends Order {
    @Override
    public String getOrderDetails() {
        return "TakeawayOrder [id=" + getId() + ", items=" + getItems() + "]";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== Order #").append(getId()).append(" ==========\n");
        sb.append("Type: TAKEAWAY\n");
        sb.append(formatItems());
        sb.append("\nTotal: $").append(String.format("%.2f", calculateTotal())).append("\n");
        sb.append("================================\n");
        return sb.toString();
    }
}
