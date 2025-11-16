package com.ssintern.client;

import com.ssintern.creational.builderAlternative.MenuItem;
import com.ssintern.structural.facade.RestaurantFacade;

import java.util.Arrays;
import java.util.List;

/**
 * Simplified client using Facade Pattern
 * 
 * Compare this with Demo.java to see how Facade simplifies the client code:
 * - No need to know about Factory Method, Singleton, Object Pool, Composite,
 * Decorator, Builder
 * - Single entry point for all restaurant operations
 * - Clean, readable code focused on business logic, not pattern complexity
 */
public class NewDemo {
    public static void main(String[] args) {
        System.out.println("=== Restaurant Management System - Simplified with Facade ===\n");

        // Create facade - hides all complexity
        RestaurantFacade restaurant = new RestaurantFacade();

        // Simple operations - no pattern knowledge needed
        System.out.println("Welcome to " + restaurant.getRestaurantName() + "!");
        System.out.println("Available Tables: " + restaurant.getAvailableTables());
        System.out.println();

        // Display menu - facade handles Composite structure
        restaurant.showMenu();

        // Customize item - facade handles Decorator pattern
        System.out.println("--- Customizing Menu Items ---");
        MenuItem customBurger = restaurant.customizeItem("Classic Burger",
                Arrays.asList("Extra Cheese", "Bacon"));
        System.out.println("Customized: " + customBurger.getName() + " - $" + customBurger.getPrice());
        System.out.println("Ingredients: " + customBurger.getIngredients());
        System.out.println();

        // Place orders - facade handles Factory Method and Object Pool
        System.out.println("--- Placing Orders ---");

        int dineInOrderId = restaurant.placeDineInOrder(
                Arrays.asList("Classic Burger", "Cola"));
        System.out.println("Dine-in order placed! Order ID: " + dineInOrderId);
        System.out.println("Total: $" + restaurant.getOrderTotal(dineInOrderId));
        System.out.println();

        int deliveryOrderId = restaurant.placeDeliveryOrder(
                Arrays.asList("Cheeseburger", "Cola"),
                "123 Main Street");
        System.out.println("Delivery order placed! Order ID: " + deliveryOrderId);
        System.out.println("Total: $" + restaurant.getOrderTotal(deliveryOrderId));
        System.out.println();

        int takeawayOrderId = restaurant.placeTakeawayOrder(
                Arrays.asList("Classic Burger"));
        System.out.println("Takeaway order placed! Order ID: " + takeawayOrderId);
        System.out.println("Total: $" + restaurant.getOrderTotal(takeawayOrderId));
        System.out.println();

        // Get order details
        System.out.println("--- Order Details ---");
        System.out.println(restaurant.getOrderDetails(dineInOrderId));
    }
}
