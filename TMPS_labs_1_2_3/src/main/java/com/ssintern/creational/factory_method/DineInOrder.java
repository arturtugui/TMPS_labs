package com.ssintern.creational.factory_method;

public class DineInOrder extends Order{
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
}
