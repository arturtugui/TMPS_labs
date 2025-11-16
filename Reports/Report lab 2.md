# TMPS Laboratory Work #2 - Structural Design Patterns

**Author:** Țugui Artur  
**Group:** FAF-231

---

## Objectives:

1. Study and understand the Structural Design Patterns;
2. As a continuation of the previous laboratory work, think about the functionalities that the system will need to provide to the user;
3. Implement some additional functionalities using structural design patterns;

---

## Used Design Patterns:

- **Composite** - Menu hierarchy management
- **Decorator** - Dynamic item customization
- **Facade** - Simplified system interface

---

## Introduction

Structural Design Patterns are concerned with how classes and objects are composed to form larger structures. They use composition to create flexible and reusable designs. In this laboratory work, I extended the Restaurant Management System from Lab 1 by implementing three structural patterns that enhance the system's functionality while maintaining clean architecture.

The patterns implemented address real-world needs:

- **Composite**: Organizing menu items into hierarchical categories (Fast Food → Burgers, Soft Drinks)
- **Decorator**: Adding extras to menu items dynamically (cheese, bacon, etc.) without modifying the original classes
- **Facade**: Providing a simple interface that hides the complexity of multiple design patterns from the client

---

## Implementation

### 1. Composite Pattern - Menu Hierarchy

**Location:** `src/main/java/com/ssintern/structural/composite/`

**Purpose:**  
The Composite pattern allows treating individual menu items and groups of items (categories) uniformly. This solves the problem of organizing the menu into a hierarchical structure where categories can contain items or other categories.

**Implementation:**

```java
// MenuComponent interface - common operations for all components
public interface MenuComponent {
    int getId();
    String getName();
    String getDescription();
    void display();
    void display(int depth);

    // Composite operations with defaults
    default void add(MenuComponent component) {
        throw new UnsupportedOperationException("Cannot add to a leaf component");
    }

    default void remove(MenuComponent component) {
        throw new UnsupportedOperationException("Cannot remove from a leaf component");
    }

    default List<MenuComponent> getChildren() {
        throw new UnsupportedOperationException("Leaf components have no children");
    }
}

// MenuItem - Leaf component
public class MenuItem implements MenuComponent {
    private int id;
    private String name;
    private String description;
    private double price;
    private List<String> ingredients;

    @Override
    public void display(int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + name + " - $" + price + " - " + description);
    }
}

// MenuCategory - Composite component
public class MenuCategory implements MenuComponent {
    private int id;
    private String name;
    private String description;
    private List<MenuComponent> items = new ArrayList<>();

    @Override
    public void add(MenuComponent item) {
        items.add(item);
    }

    @Override
    public void display(int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + getName() + " - " + getDescription());
        for (MenuComponent item : items) {
            item.display(depth + 1);  // Recursive display
        }
    }
}
```

**Usage:**

```java
// Create category hierarchy
MenuComponent fastFoodCategory = new MenuCategory("Fast Food", "Tasty and quick meals");
MenuComponent burgersCategory = new MenuCategory("Burgers", "Delicious burgers");
fastFoodCategory.add(burgersCategory);

// Add items to category
MenuItem burger = new MenuItem.MenuItemBuilder()
    .setName("Classic Burger")
    .setPrice(8.99)
    .build();
burgersCategory.add(burger);

// Display entire hierarchy recursively
fastFoodCategory.display();
```

**Motivation:**  
The Composite pattern is essential for creating a hierarchical menu structure that mirrors real restaurant menus. It allows treating individual items and categories uniformly through the same interface, enabling recursive operations like displaying the entire menu with proper indentation. The transparency approach (adding operations to the interface with default exceptions) provides a uniform interface while maintaining type safety at runtime.

**Design Considerations:**

When implementing Composite, I had to choose between two approaches:

1. **Safety First Approach**: Put `add()`, `remove()`, `getChildren()` methods only in composite classes. This is type-safe at compile time but requires casting when working with the component interface.

2. **Transparency Approach** (chosen): Put all methods in the component interface with default implementations that throw exceptions for leaf nodes. This provides a uniform interface but moves type safety to runtime.

I chose the **transparency approach** because:

- It provides a truly uniform interface - client code can treat leaves and composites identically
- No need for type checking or casting in client code
- The recursive `display()` method works elegantly without knowing component types
- Runtime exceptions are acceptable since in practice, we control which components are leaves vs composites
- Follows the canonical Gang of Four Composite pattern more closely

This approach resulted in clean, maintainable code where the menu hierarchy can be traversed and displayed recursively without any `instanceof` checks or type casting.

---

### 2. Decorator Pattern - Item Customization

**Location:** `src/main/java/com/ssintern/structural/decorator/`

**Purpose:**  
The Decorator pattern dynamically adds responsibilities to menu items (like extra ingredients) without modifying the original MenuItem class. This solves the problem of having to create separate classes for every possible combination of extras (BurgerWithCheese, BurgerWithCheeseAndBacon, etc.).

**Implementation:**

```java
// Abstract decorator base class
public abstract class MenuItemDecorator implements MenuComponent {
    protected MenuComponent wrappedItem;

    public MenuItemDecorator(MenuComponent item) {
        this.wrappedItem = item;
    }

    // Delegates to wrapped item by default
    @Override
    public String getName() {
        return wrappedItem.getName();
    }
}

// Concrete decorator
public class ExtraIngredientDecorator extends MenuItemDecorator {
    private String ingredientName;
    private double extraCost;
    private MenuComponent modifiedItem;

    public ExtraIngredientDecorator(MenuComponent item, String ingredientName, double extraCost) {
        super(item);
        this.ingredientName = ingredientName;
        this.extraCost = extraCost;

        try {
            // Use Prototype pattern to clone and modify
            this.modifiedItem = item.createModifiedCopy();
            this.modifiedItem.addIngredient(ingredientName);
            this.modifiedItem.setPrice(modifiedItem.getPrice() + extraCost);
        } catch (UnsupportedOperationException e) {
            this.modifiedItem = null;
        }
    }

    @Override
    public String getName() {
        return wrappedItem.getName() + " + " + ingredientName;
    }

    @Override
    public double getPrice() {
        return modifiedItem != null
            ? modifiedItem.getPrice()
            : wrappedItem.getPrice() + extraCost;
    }
}
```

**Usage:**

```java
MenuItem burger = new MenuItem.MenuItemBuilder()
    .setName("Classic Burger")
    .setPrice(8.99)
    .build();

// Chain decorators
MenuComponent customized = new ExtraIngredientDecorator(burger, "Extra Cheese", 2.0);
customized = new ExtraIngredientDecorator(customized, "Bacon", 3.0);

// Result: "Classic Burger + Extra Cheese + Bacon" - $13.99
```

**Motivation:**  
The Decorator pattern is crucial for a restaurant system where customers can customize their orders. Without it, we would need a class explosion (hundreds of classes for every combination). The pattern integrates elegantly with the Prototype pattern, using `createModifiedCopy()` to clone and modify items without instanceof checks, demonstrating pure polymorphic OOP design.

**Design Considerations:**

A key challenge was how to add ingredients to a decorated item when the decorator wraps a `MenuComponent` interface, but only `MenuItem` (the leaf) has ingredient operations. I considered three approaches:

1. **instanceof Check**: Check if wrapped item is `MenuItem` and cast. Simple but violates OOP principles and is a code smell.

2. **Interface Extension** (chosen): Add ingredient-related methods (`getIngredients()`, `addIngredient()`, `getPrice()`, `setPrice()`, `createModifiedCopy()`) to the `MenuComponent` interface with default implementations that throw exceptions. `MenuItem` overrides these with real functionality.

3. **MenuItem-Only Constructor**: Make decorator accept only `MenuItem`, not `MenuComponent`. Type-safe but breaks Decorator pattern principle and reduces flexibility.

I chose **Option 2 (Interface Extension)** because:

- No `instanceof` checks - pure polymorphism through interface contracts
- Decorator can work with any `MenuComponent` uniformly
- Graceful exception handling for components that don't support modification
- Integrates the Prototype pattern elegantly: `createModifiedCopy()` delegates to `clone()`
- Demonstrates advanced OOP: using default interface methods (Java 8+) to provide base behavior
- Follows SOLID principles: Open/Closed (open for extension, closed for modification)

The decorator creates a modified copy using the Prototype pattern, adds the ingredient, adjusts the price, and returns the customized item - all through polymorphic method calls without any type checking.

---

### 3. Facade Pattern - Unified Interface

**Location:** `src/main/java/com/ssintern/structural/facade/RestaurantFacade.java`

**Purpose:**  
The Facade pattern provides a simplified interface to the complex subsystem of patterns (Factory Method, Singleton, Object Pool, Composite, Decorator, Builder). This solves the problem of clients needing to understand and interact with multiple patterns directly, satisfying the requirement of "only one client for the whole system."

**Implementation:**

```java
public class RestaurantFacade {
    private Restaurant restaurant;
    private Map<Integer, Order> activeOrders;
    private int orderCounter = 0;

    public RestaurantFacade() {
        this.restaurant = Restaurant.getInstance();
        this.activeOrders = new HashMap<>();
        initializeMenu();  // Hides Composite structure creation
    }

    // Simple method hiding Factory Method and Object Pool
    public int placeDineInOrder(List<String> itemNames) {
        Table table = restaurant.getTablePool().acquireTable();  // Pool
        OrderCreator<Integer> creator = new DineInOrderCreator(); // Factory
        Order order = creator.createOrder(table.getId());

        for (String itemName : itemNames) {
            MenuItem item = findMenuItem(itemName);  // Composite traversal
            if (item != null) {
                order.addItem(item);
            }
        }

        int orderId = ++orderCounter;
        activeOrders.put(orderId, order);
        return orderId;
    }

    // Simple method hiding Decorator pattern
    public MenuItem customizeItem(String baseItemName, List<String> extras) {
        MenuItem baseItem = findMenuItem(baseItemName);
        MenuComponent currentComponent = baseItem;
        MenuItem currentModified = baseItem;

        for (String extra : extras) {
            ExtraIngredientDecorator decorator =
                new ExtraIngredientDecorator(currentComponent, extra, 1.50);
            if (decorator.getModifiedItem() != null) {
                currentModified = (MenuItem) decorator.getModifiedItem();
                currentComponent = currentModified;
            }
        }

        return currentModified;
    }

    // Helper method that traverses Composite structure
    private MenuItem findMenuItem(String name) {
        for (MenuComponent category : restaurant.getMenu()) {
            MenuItem found = searchInCategory(category, name);
            if (found != null) return found;
        }
        return null;
    }
}
```

**Client Code (NewDemo.java):**

```java
public class NewDemo {
    public static void main(String[] args) {
        // Single facade instance - hides all complexity
        RestaurantFacade restaurant = new RestaurantFacade();

        // Simple operations
        restaurant.showMenu();

        MenuItem customBurger = restaurant.customizeItem("Classic Burger",
            Arrays.asList("Extra Cheese", "Bacon"));

        int orderId = restaurant.placeDineInOrder(
            Arrays.asList("Classic Burger", "Cola"));

        System.out.println("Total: $" + restaurant.getOrderTotal(orderId));
    }
}
```

---

## Results

**Output from NewDemo.java (Simplified Client):**

```
=== Restaurant Management System - Simplified with Facade ===

Welcome to The Gourmet Restaurant!
Available Tables: 5


=== The Gourmet Restaurant Menu ===
Fast Food - Tasty and quick meals
  Burgers - Delicious burgers with various toppings
    Classic Burger - $8.99 - A delicious beef burger with fresh vegetables
    Cheeseburger - $9.99 - Classic burger with melted cheddar cheese
  Soft Drinks - Refreshing beverages
    Cola - $1.99 - Chilled carbonated soft drink
======================

--- Customizing Menu Items ---
Customized: Classic Burger - $11.99
Ingredients: [Beef Patty, Bun, Lettuce, Tomato, Extra Cheese, Bacon]

--- Placing Orders ---
Dine-in order placed! Order ID: 1
Total: $10.98

Delivery order placed! Order ID: 2
Total: $11.98

Takeaway order placed! Order ID: 3
Total: $8.99

--- Order Details ---

========== Order #1 ==========
Type: DINE-IN
Table: 1

Items:
  1. Classic Burger - $8.99
     A delicious beef burger with fresh vegetables
     Ingredients: Beef Patty, Bun, Lettuce, Tomato
  2. Cola - $1.99
     Chilled carbonated soft drink
     Ingredients: Carbonated Water, Sugar, Caffeine

Total: $10.98
================================
```
