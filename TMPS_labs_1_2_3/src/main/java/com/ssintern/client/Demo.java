package com.ssintern.client;

import com.ssintern.creational.builder.DineInOrderBuilder;
import com.ssintern.creational.builder.OrderDirector;
import com.ssintern.creational.singleton.Restaurant;
import com.ssintern.domain.models.MenuItem;
import com.ssintern.domain.models.Order;
import com.ssintern.domain.models.Table;

// TMPS lab 1, unfinished, will showcase all creational design patterns
public class Demo {
    public static void main(String[] args) {
        Restaurant restaurant = Restaurant.getInstance();
        System.out.println("Restaurant Menu: " + restaurant.getMenu());

        MenuItem burger = new MenuItem("Burger", "A delicious beef burger", 5.99);
        System.out.println(burger);
        // Add burger to menu

        Table table1 = restaurant.getTablePool().acquireTable();
        System.out.println("Acquired Table ID: " + table1.getId());

        DineInOrderBuilder dineInOrderBuilder = new DineInOrderBuilder();
        OrderDirector orderDirector = new OrderDirector(dineInOrderBuilder);
        orderDirector.constructOrder();
        Order dineInOrder = orderDirector.getOrder();
        dineInOrder.addItem(burger);
        System.out.println(dineInOrder);

        System.out.println("Total: " + dineInOrder.calculateTotal());

        restaurant.getTablePool().releaseTable(table1);
        System.out.println("Released Table ID: " + table1.getId());

    }
}
