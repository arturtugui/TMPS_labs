package com.ssintern.client;

import com.ssintern.domain.models.MenuItem;
import com.ssintern.domain.models.Order;
import com.ssintern.domain.models.Table;

import static com.ssintern.domain.models.OrderType.DINE_IN;

// TMPS lab 1, unfinished, will showcase all creational design patterns
public class Demo {
    public static void main(String[] args) {
        MenuItem burger = new MenuItem(1, "Burger", "A delicious beef burger", 5.99);
        System.out.println(burger);

        Table table1 = new Table(1, 4);
        System.out.println(table1);

        Order order1 = new Order(1, DINE_IN);
        order1.addItem(burger);
        order1.setTableId(table1.getId());
        table1.occupy();
        System.out.println("Order Total: $" + order1.calculateTotal());
    }
}
