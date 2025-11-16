package com.ssintern.client;

import com.ssintern.creational.factory_method.DineInOrderCreator;
import com.ssintern.creational.factory_method.Order;
import com.ssintern.creational.factory_method.OrderCreator;
import com.ssintern.creational.singleton.Restaurant;
import com.ssintern.creational.builderAlternative.MenuItem;
import com.ssintern.domain.models.Table;
import com.ssintern.structural.composite.MenuCategory;
import com.ssintern.structural.composite.MenuComponent;

public class Demo {
    public static void main(String[] args) {
        System.out.println("=== Restaurant Management System Demo ===\n");

        // Singleton - single Restaurant instance - Creational DP
        Restaurant restaurant = Restaurant.getInstance();
        System.out.println("Welcome to " + restaurant.getName() + "!");
        System.out.println("Available Tables: " + restaurant.getTablePool().getMaxPoolSize() + "\n");

        // Composite - for Menu hierarchy - Structural DP
        MenuComponent fastFoodCategory = new MenuCategory("Fast Food", "Tasty and quick meals");
        restaurant.addMenuComponent(fastFoodCategory);

        MenuComponent burgersCategory = new MenuCategory("Burgers", "Delicious burgers with various toppings");
        fastFoodCategory.add(burgersCategory);

        MenuComponent softDrinksCategory = new MenuCategory("Soft Drinks", "Refreshing beverages");
        fastFoodCategory.add(softDrinksCategory);

        // Builder - for MenuItem - Creational DP
        System.out.println("--- Creating Menu Items (Builder Pattern) ---");
        MenuItem cola = new MenuItem.MenuItemBuilder()
                .setName("Cola")
                .setDescription("Chilled carbonated soft drink")
                .setPrice(1.99)
                .addIngredient("Carbonated Water")
                .addIngredient("Sugar")
                .addIngredient("Caffeine")
                .build();
        softDrinksCategory.add(cola); // Add to category
        System.out.println("Created Menu Item: " + cola.getName() + " - $" + cola.getPrice());

        // Prototype - for cloning MenuItems - Creational DP
        System.out.println("\n--- Customizing Menu Items (Prototype Pattern) ---");
        MenuItem burger = new MenuItem.MenuItemBuilder()
                .setName("Classic Burger")
                .setDescription("A delicious beef burger with fresh vegetables")
                .setPrice(8.99)
                .addIngredient("Beef Patty")
                .addIngredient("Bun")
                .addIngredient("Lettuce")
                .addIngredient("Tomato")
                .build();
        burgersCategory.add(burger); // Add to category
        System.out.println("Created Menu Item: " + burger.getName() + " - $" + burger.getPrice());

        MenuItem cheeseburger = burger.clone();
        cheeseburger.setName("Cheeseburger");
        cheeseburger.setDescription("Classic burger with melted cheddar cheese");
        cheeseburger.addIngredient("Cheddar Cheese");
        cheeseburger.setPrice(9.99);
        burgersCategory.add(cheeseburger); // Add to category
        System.out.println("Cloned and Customized Menu Item: " + cheeseburger.getName() + " - $" + cheeseburger.getPrice());

        restaurant.displayMenu();

        // Object Pool - for Tables Management - Creational DP
        System.out.println("--- Table Management (Object Pool Pattern) ---");
        Table table1 = restaurant.getTablePool().acquireTable();
        System.out.println("Acquired Table #" + table1.getId() + " (Capacity: " + table1.getCapacity() + ")");
        System.out.println("Table is now available: " + !table1.isOccupied());
        System.out.println();

        // Factory method - for Order - Creational DP
        System.out.println("--- Creating Order (Factory method Pattern) ---");
        OrderCreator<Integer> dineInOrderCreator = new DineInOrderCreator();
        Order dineInOrder = dineInOrderCreator.createOrder(table1.getId());

        dineInOrder.addItem(cola);
        dineInOrder.addItem(burger);
        dineInOrder.addItem(cheeseburger);

        System.out.println(dineInOrder);

        System.out.println("--- Releasing Table ---");
        restaurant.getTablePool().releaseTable(table1);
        System.out.println("Released Table #" + table1.getId());
        System.out.println("Table is now available: " + !table1.isOccupied());
    }
}
