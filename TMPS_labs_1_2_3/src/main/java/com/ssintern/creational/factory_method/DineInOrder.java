package com.ssintern.creational.factory_method;

public class DineInOrder extends Order {
    private Integer tableId;

    public DineInOrder(Integer tableId) {
        super();
        this.tableId = tableId;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    @Override
    public String getOrderDetails() {
        return "DineInOrder [id=" + getId() + ", items=" + getItems() + ", tableId=" + tableId + "]";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== Order #").append(getId()).append(" ==========\n");
        sb.append("Type: DINE-IN\n");
        sb.append("Table: ").append(tableId).append("\n");
        sb.append(formatItems());
        sb.append("\nTotal: $").append(String.format("%.2f", calculateTotal())).append("\n");
        sb.append("================================\n");
        return sb.toString();
    }
}
