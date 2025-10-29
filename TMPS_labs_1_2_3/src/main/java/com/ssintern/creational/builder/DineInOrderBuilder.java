package com.ssintern.creational.builder;

import com.ssintern.domain.models.Order;
import com.ssintern.domain.models.OrderType;
import com.ssintern.domain.models.Table;

public class DineInOrderBuilder implements OrderBuilder {
    private Order order;
    private Table table;

    public DineInOrderBuilder(Table table) {
        this.order = new Order();
        this.table = table;
        // Note: Table should already be occupied by TablePool.acquireTable()
        // If not occupied, occupy it now (safety check)
        if (!table.isOccupied()) {
            table.occupy();
        }
    }

    @Override
    public void buildId() {
        order.setId(Order.idCounter.incrementAndGet());
    }

    @Override
    public void buildItems() {
        // Items list already initialized in Order constructor
    }

    @Override
    public void buildOrderType() {
        order.setOrderType(OrderType.DINE_IN);
    }

    @Override
    public void buildTableId() {
        order.setTableId(table.getId()); // Use actual table ID from provided table
    }

    @Override
    public void buildDeliveryAddress() {
        order.setDeliveryAddress(null);
    }

    @Override
    public Order getOrder() {
        return order;
    }
}
