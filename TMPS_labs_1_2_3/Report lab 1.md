# TMPS Laboratory Work #1 - Creational Design Patterns

**Author:** Țugui Artur  
**Group:** FAF-231

---

## Objectives:

- Get familiar with the Creational DPs;
- Choose a specific domain (Restaurant Management);
- Implement at least 3 CDPs for the specific domain;

---

## Used Design Patterns:

- Singleton
- Builder
- Prototype
- Object Pool

---

## Implementation

This project models a restaurant management system using four creational design patterns:

- **Singleton** ensures a single Restaurant instance manages the menu and tables.
- **Builder** constructs complex menu items (e.g., burgers with ingredients) and orders.
- **Prototype** allows cloning and customizing menu items (e.g., making a cheeseburger from a burger).
- **Object Pool** manages a pool of reusable Table objects for dine-in orders.

### Main Classes

- **Restaurant**: Singleton class that manages the menu (a list of `MenuItem`), the pool of tables (`TablePool`), and provides global access to these resources. It exposes methods to add menu items and display the menu.
- **MenuItem**: Represents a menu product (e.g., burger, pizza) with a name, description, price, and a list of ingredients. Built using the Builder pattern and supports cloning (Prototype pattern) for easy customization.
- **Table**: Represents a physical table in the restaurant, with a unique ID, capacity, and occupancy status. Managed by the `TablePool` for efficient reuse.
- **Order**: Represents a customer order, containing a list of `MenuItem` objects, order type (dine-in, delivery, etc.), and optional table or delivery address. Built using a builder and director for different order types.

### Key Snippets

**Singleton Pattern (Restaurant):**  
_Fits this case because the restaurant must have a single, consistent state for its menu and tables throughout the application. Using Singleton prevents accidental creation of multiple restaurant instances, which could lead to resource conflicts or inconsistent data._

```java
public class Restaurant {
    private static final Restaurant instance = new Restaurant();
    private TablePool tablePool;
    private List<MenuItem> menu;
    private Restaurant() { ... }
    public static Restaurant getInstance() { return instance; }
    // ...
}
```

**Object Pool Pattern (TablePool):**  
_Fits this case because the number of tables in a restaurant is limited and they are reused by different customers. Object Pool ensures that tables are efficiently allocated and released, preventing unnecessary creation and destruction of table objects._

```java
Table table1 = restaurant.getTablePool().acquireTable();
// ... use table1 ...
restaurant.getTablePool().releaseTable(table1);
```

**Order Creation (Builder for Order):**  
_Fits this case because orders can have many optional parameters (table, delivery address, items, etc.). Builder makes it easy to construct orders with only the necessary fields, avoiding large constructors with many parameters or a proliferation of overloaded constructors. Each order type has different requirements:_

- `Order` fields: `id`, `List<MenuItem> items`, `OrderType orderType`, `Integer tableId`, `String deliveryAddress`
- `OrderType` enum: `DINE_IN`, `DELIVERY`, `TAKEAWAY`
  - **DINE_IN**: requires a table (tableId must be set)
  - **DELIVERY**: requires a delivery address
  - **TAKEAWAY**: needs neither table nor address

_The builder ensures that only the relevant fields are set for each order type, enforcing correct construction and reducing errors._

```java
DineInOrderBuilder dineInOrderBuilder = new DineInOrderBuilder(table1);
OrderDirector orderDirector = new OrderDirector(dineInOrderBuilder);
orderDirector.constructOrder();
Order dineInOrder = orderDirector.getOrder();
dineInOrder.addItem(burger);
dineInOrder.addItem(cheeseburger);
System.out.println(dineInOrder);
```

**Builder Pattern (MenuItem):**  
_Fits this case because menu items can have many optional ingredients and properties. Builder avoids telescoping constructors and makes it easy to create both simple and complex menu items in a readable, step-by-step way._

```java
MenuItem burger = new MenuItem.MenuItemBuilder()
    .setName("Classic Burger")
    .setDescription("A delicious beef burger with fresh vegetables")
    .setPrice(8.99)
    .addIngredient("Beef Patty")
    .addIngredient("Bun")
    .addIngredient("Lettuce")
    .addIngredient("Tomato")
    .build();
```

**Prototype Pattern (MenuItem):**  
_Fits this case because customers often want to customize existing menu items (e.g., "add cheese to my burger"). Prototype allows you to efficiently create a new item based on an existing one, without rebuilding from scratch or risking changes to the original._

```java
MenuItem cheeseburger = burger.clone();
cheeseburger.setName("Cheeseburger");
cheeseburger.setDescription("Classic burger with melted cheddar cheese");
cheeseburger.addIngredient("Cheddar Cheese");
cheeseburger.setPrice(9.99);
```

**Order Output Example:**

```
=== Restaurant Management System Demo ===

Restaurant: The Gourmet Restaurant

--- Creating Menu Items (Builder Pattern) ---
Added to menu: Classic Burger

--- Customizing Menu Items (Prototype Pattern) ---
Added to menu: Cheeseburger

========== The Gourmet Restaurant Menu ==========
1. Classic Burger - $8.99
   A delicious beef burger with fresh vegetables
   Ingredients: Beef Patty, Bun, Lettuce, Tomato

2. Cheeseburger - $9.99
   Classic burger with melted cheddar cheese
   Ingredients: Beef Patty, Bun, Lettuce, Tomato, Cheddar Cheese

========================================

--- Table Management (Object Pool Pattern) ---
Acquired Table #1 (Capacity: 4)

--- Creating Order (Builder Pattern) ---

========== Order #1 ==========
Type: DINE_IN
Table: 1

Items:
  1. Classic Burger - $8.99
     A delicious beef burger with fresh vegetables
     Ingredients: Beef Patty, Bun, Lettuce, Tomato
  2. Cheeseburger - $9.99
     Classic burger with melted cheddar cheese
     Ingredients: Beef Patty, Bun, Lettuce, Tomato, Cheddar Cheese

Total: $18.98
================================

--- Releasing Table ---
Released Table #1

Table is now available: true

```

---

## Refactoring: Builder to Factory Method for Orders

After initial implementation with the Builder pattern for Order creation, the design was refactored to use the **Factory Method pattern** based on professor feedback. This change better fits the use case.

### Why Factory Method is Better for Orders

**Builder Pattern** is ideal for objects with:

- Many optional parameters (10+ fields)
- Complex configuration steps
- Same type with different configurations (e.g., MenuItem with varying ingredients)

**Factory Method Pattern** is better for:

- Creating **different types** of objects (DineInOrder, DeliveryOrder, TakeawayOrder)
- When each type has **unique behavior** and **different fields**
- When the creation process varies by type but follows a common template

### Implementation Details

**Order Hierarchy:**

```java
public abstract class Order {
    private int id;
    private List<MenuItem> items;

    public abstract String getOrderDetails();
    protected String formatItems() { /* common formatting */ }
    public double calculateTotal() { /* common logic */ }
}

public class DineInOrder extends Order {
    private Integer tableId;  // Type-specific field
    // ...
}

public class DeliveryOrder extends Order {
    private String deliveryAddress;  // Type-specific field
    // ...
}

public class TakeawayOrder extends Order {
    // No extra fields
}
```

**Factory Method with Generics:**

```java
public abstract class OrderCreator<T> {
    public static final AtomicInteger idCounter = new AtomicInteger(0);

    public abstract Order createOrder(T params);

    public Order processOrder(T params) {
        Order order = createOrder(params);
        order.setId(idCounter.incrementAndGet());
        return order;
    }
}

public class DineInOrderCreator extends OrderCreator<Integer> {
    @Override
    public Order createOrder(Integer tableId) {
        return new DineInOrder(tableId);
    }
}

public class DeliveryOrderCreator extends OrderCreator<String> {
    @Override
    public Order createOrder(String address) {
        return new DeliveryOrder(address);
    }
}

public class TakeawayOrderCreator extends OrderCreator<Void> {
    @Override
    public Order createOrder(Void unused) {
        return new TakeawayOrder();
    }
}
```

**Usage:**

```java
OrderCreator<Integer> dineInCreator = new DineInOrderCreator();
Order dineInOrder = dineInCreator.processOrder(table.getId());
dineInOrder.addItem(burger);
```

### Singleton Pattern Update

The Restaurant singleton was also improved from eager to lazy initialization:

```java
public class Restaurant {
    private static volatile Restaurant instance;  // volatile for thread safety

    private Restaurant() { /* ... */ }

    public static Restaurant getInstance() {
        if (instance == null) {
            synchronized (Restaurant.class) {  // double-checked locking
                if (instance == null) {
                    instance = new Restaurant();
                }
            }
        }
        return instance;
    }
}
```
