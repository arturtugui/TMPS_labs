package com.ssintern.structural.facade;

import com.ssintern.creational.builderAlternative.MenuItem;
import com.ssintern.creational.factory_method.*;
import com.ssintern.creational.singleton.Restaurant;
import com.ssintern.domain.models.Table;
import com.ssintern.structural.composite.MenuCategory;
import com.ssintern.structural.composite.MenuComponent;
import com.ssintern.structural.decorator.ExtraIngredientDecorator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Facade Pattern - Simplifies restaurant operations
 * Hides complexity of:
 * - Factory Method (order creation)
 * - Singleton (restaurant instance)
 * - Object Pool (table management)
 * - Composite (menu structure)
 * - Decorator (item customization)
 * - Builder (menu item creation)
 */
public class RestaurantFacade {
    private Restaurant restaurant;
    private Map<Integer, Order> activeOrders;
    private int orderCounter = 0;

    public RestaurantFacade() {
        this.restaurant = Restaurant.getInstance();
        this.activeOrders = new HashMap<>();
        initializeMenu();
    }

    /**
     * Initializes the restaurant menu with categories and items
     */
    private void initializeMenu() {
        // Create main category
        MenuComponent fastFoodCategory = new MenuCategory("Fast Food", "Tasty and quick meals");
        restaurant.addMenuComponent(fastFoodCategory);

        // Create subcategories
        MenuComponent burgersCategory = new MenuCategory("Burgers", "Delicious burgers with various toppings");
        MenuComponent softDrinksCategory = new MenuCategory("Soft Drinks", "Refreshing beverages");
        fastFoodCategory.add(burgersCategory);
        fastFoodCategory.add(softDrinksCategory);

        // Add menu items to categories
        MenuItem burger = new MenuItem.MenuItemBuilder()
                .setName("Classic Burger")
                .setDescription("A delicious beef burger with fresh vegetables")
                .setPrice(8.99)
                .addIngredient("Beef Patty")
                .addIngredient("Bun")
                .addIngredient("Lettuce")
                .addIngredient("Tomato")
                .build();
        burgersCategory.add(burger);

        MenuItem cheeseburger = burger.clone();
        cheeseburger.setName("Cheeseburger");
        cheeseburger.setDescription("Classic burger with melted cheddar cheese");
        cheeseburger.addIngredient("Cheddar Cheese");
        cheeseburger.setPrice(9.99);
        burgersCategory.add(cheeseburger);

        MenuItem cola = new MenuItem.MenuItemBuilder()
                .setName("Cola")
                .setDescription("Chilled carbonated soft drink")
                .setPrice(1.99)
                .addIngredient("Carbonated Water")
                .addIngredient("Sugar")
                .addIngredient("Caffeine")
                .build();
        softDrinksCategory.add(cola);
    }

    /**
     * Displays the restaurant menu (hides Composite pattern complexity)
     */
    public void showMenu() {
        restaurant.displayMenu();
    }

    /**
     * Creates a customized menu item with extras (hides Decorator pattern)
     * 
     * @param baseItemName Name of the base menu item
     * @param extras       List of extra ingredients to add
     * @return Customized menu item
     */
    public MenuItem customizeItem(String baseItemName, List<String> extras) {
        MenuItem baseItem = findMenuItem(baseItemName);
        if (baseItem == null) {
            throw new IllegalArgumentException("Item not found: " + baseItemName);
        }

        // Chain decorators - each decorator wraps and modifies the previous result
        MenuComponent currentComponent = baseItem;
        MenuItem currentModified = baseItem;

        for (String extra : extras) {
            ExtraIngredientDecorator decorator = new ExtraIngredientDecorator(currentComponent, extra, 1.50);
            // Get the modified item from this decorator
            if (decorator.getModifiedItem() != null) {
                currentModified = (MenuItem) decorator.getModifiedItem();
                // Next decorator wraps the modified item
                currentComponent = currentModified;
            }
        }

        return currentModified;
    }

    /**
     * Places a dine-in order (hides Factory Method and Object Pool)
     * 
     * @param itemNames List of menu item names to order
     * @return Order ID
     */
    public int placeDineInOrder(List<String> itemNames) {
        Table table = restaurant.getTablePool().acquireTable();
        OrderCreator<Integer> creator = new DineInOrderCreator();
        Order order = creator.createOrder(table.getId());

        for (String itemName : itemNames) {
            MenuItem item = findMenuItem(itemName);
            if (item != null) {
                order.addItem(item);
            }
        }

        int orderId = ++orderCounter;
        activeOrders.put(orderId, order);
        return orderId;
    }

    /**
     * Places a delivery order (hides Factory Method)
     * 
     * @param itemNames List of menu item names to order
     * @param address   Delivery address
     * @return Order ID
     */
    public int placeDeliveryOrder(List<String> itemNames, String address) {
        OrderCreator<String> creator = new DeliveryOrderCreator();
        Order order = creator.createOrder(address);

        for (String itemName : itemNames) {
            MenuItem item = findMenuItem(itemName);
            if (item != null) {
                order.addItem(item);
            }
        }

        int orderId = ++orderCounter;
        activeOrders.put(orderId, order);
        return orderId;
    }

    /**
     * Places a takeaway order (hides Factory Method)
     * 
     * @param itemNames List of menu item names to order
     * @return Order ID
     */
    public int placeTakeawayOrder(List<String> itemNames) {
        OrderCreator<Void> creator = new TakeawayOrderCreator();
        Order order = creator.createOrder(null);

        for (String itemName : itemNames) {
            MenuItem item = findMenuItem(itemName);
            if (item != null) {
                order.addItem(item);
            }
        }

        int orderId = ++orderCounter;
        activeOrders.put(orderId, order);
        return orderId;
    }

    /**
     * Gets order details
     * 
     * @param orderId Order ID
     * @return Order details as string
     */
    public String getOrderDetails(int orderId) {
        Order order = activeOrders.get(orderId);
        if (order == null) {
            return "Order not found";
        }
        return order.toString();
    }

    /**
     * Calculates order total
     * 
     * @param orderId Order ID
     * @return Total price
     */
    public double getOrderTotal(int orderId) {
        Order order = activeOrders.get(orderId);
        if (order == null) {
            return 0.0;
        }
        return order.calculateTotal();
    }

    /**
     * Gets the number of available tables (hides Object Pool)
     * 
     * @return Number of tables
     */
    public int getAvailableTables() {
        return restaurant.getTablePool().getMaxPoolSize();
    }

    /**
     * Gets restaurant name (hides Singleton)
     * 
     * @return Restaurant name
     */
    public String getRestaurantName() {
        return restaurant.getName();
    }

    /**
     * Helper method to find a menu item by name (traverses Composite structure)
     */
    private MenuItem findMenuItem(String name) {
        for (MenuComponent category : restaurant.getMenu()) {
            MenuItem found = searchInCategory(category, name);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    /**
     * Recursively searches for menu item in category hierarchy
     */
    private MenuItem searchInCategory(MenuComponent component, String name) {
        if (component instanceof MenuItem) {
            MenuItem item = (MenuItem) component;
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }

        try {
            List<MenuComponent> children = component.getChildren();
            for (MenuComponent child : children) {
                MenuItem found = searchInCategory(child, name);
                if (found != null) {
                    return found;
                }
            }
        } catch (UnsupportedOperationException e) {
            // Leaf node, no children
        }

        return null;
    }
}
