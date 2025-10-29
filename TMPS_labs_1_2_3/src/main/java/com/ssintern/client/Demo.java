package com.ssintern.client;

import com.ssintern.creational.builder.DineInOrderBuilder;
import com.ssintern.creational.builder.OrderDirector;
import com.ssintern.creational.singleton.Restaurant;
import com.ssintern.creational.builderAlternative.MenuItem;
import com.ssintern.domain.models.Order;
import com.ssintern.domain.models.Table;

public class Demo {
    public static void main(String[] args) {
        System.out.println("=== Restaurant Management System Demo ===\n");

        // Singleton Pattern
        Restaurant restaurant = Restaurant.getInstance();
        System.out.println("Restaurant: " + restaurant.getName());
        System.out.println();

        // Builder Pattern for MenuItem
        System.out.println("--- Creating Menu Items (Builder Pattern) ---");
        MenuItem burger = new MenuItem.MenuItemBuilder()
                .setName("Classic Burger")
                .setDescription("A delicious beef burger with fresh vegetables")
                .setPrice(8.99)
                .addIngredient("Beef Patty")
                .addIngredient("Bun")
                .addIngredient("Lettuce")
                .addIngredient("Tomato")
                .build();
        restaurant.addMenuItem(burger);

        // Prototype Pattern
        System.out.println("\n--- Customizing Menu Items (Prototype Pattern) ---");
        MenuItem cheeseburger = burger.clone();
        cheeseburger.setName("Cheeseburger");
        cheeseburger.setDescription("Classic burger with melted cheddar cheese");
        cheeseburger.addIngredient("Cheddar Cheese");
        cheeseburger.setPrice(9.99);
        restaurant.addMenuItem(cheeseburger);

        restaurant.displayMenu();

        // Object Pool Pattern
        System.out.println("--- Table Management (Object Pool Pattern) ---");
        Table table1 = restaurant.getTablePool().acquireTable();
        System.out.println("Acquired Table #" + table1.getId() + " (Capacity: " + table1.getCapacity() + ")");
        System.out.println();

        // Builder Pattern for Order
        System.out.println("--- Creating Order (Builder Pattern) ---");
        DineInOrderBuilder dineInOrderBuilder = new DineInOrderBuilder(table1);
        OrderDirector orderDirector = new OrderDirector(dineInOrderBuilder);
        orderDirector.constructOrder();
        Order dineInOrder = orderDirector.getOrder();

        dineInOrder.addItem(burger);
        dineInOrder.addItem(cheeseburger);

        System.out.println(dineInOrder);

        System.out.println("--- Releasing Table ---");
        restaurant.getTablePool().releaseTable(table1);
        System.out.println("Released Table #" + table1.getId());
        System.out.println("\nTable is now available: " + !table1.isOccupied());
    }
}
