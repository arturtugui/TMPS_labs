package com.ssintern.client;

import com.ssintern.creational.builder.DineInOrderBuilder;
import com.ssintern.creational.builder.OrderDirector;
import com.ssintern.creational.pool.TablePool;
import com.ssintern.domain.models.MenuItem;
import com.ssintern.domain.models.Order;
import com.ssintern.domain.models.Table;

import static com.ssintern.domain.models.OrderType.DINE_IN;

// TMPS lab 1, unfinished, will showcase all creational design patterns
public class Demo {
    public static void main(String[] args) {
        MenuItem burger = new MenuItem("Burger", "A delicious beef burger", 5.99);
        System.out.println(burger);

        TablePool tablePool = new TablePool(5);
        Table table1 = tablePool.acquireTable();
        System.out.println("Acquired Table ID: " + table1.getId());

        DineInOrderBuilder dineInOrderBuilder = new DineInOrderBuilder();
        OrderDirector orderDirector = new OrderDirector(dineInOrderBuilder);
        orderDirector.constructOrder();
        Order dineInOrder = orderDirector.getOrder();
        dineInOrder.addItem(burger);
        System.out.println(dineInOrder);

        dineInOrder.calculateTotal();

        tablePool.releaseTable(table1);


    }
}
