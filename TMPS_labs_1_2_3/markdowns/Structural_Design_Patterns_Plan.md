# Structural Design Patterns Implementation Plan

## Current Project Analysis

Your restaurant management system uses:

- **Singleton**: Restaurant management
- **Factory Method**: Order creation (DeliveryOrder, DineInOrder, TakeawayOrder)
- **Object Pool**: Table management
- **Builder Alternative**: MenuItem construction

---

## Recommended Structural Patterns (5 patterns for comprehensive coverage)

### 1. **COMPOSITE** - Menu Hierarchy System

**Priority: START HERE (Foundation pattern)**

**Where to use:**

- Create a menu structure with categories and items
- Build combo meals / set menus that contain multiple items
- Treat individual items and combos uniformly

**The Problem Composite Solves:**

You want to treat individual menu items and groups of items (combos, categories) the same way. Without Composite, you'd need separate code to handle single items vs collections.

**Structure:**

```
MenuComponent (abstract/interface)
├── MenuItem (Leaf) - individual items like "Pizza", "Burger"
└── ComboMeal/MenuCategory (Composite) - contains other MenuComponents
```

**The Composite Caveat - Two Approaches:**

#### **Approach 1: Safety First (Recommended for your project)**

- Put `add()`, `remove()`, `getChild()` methods ONLY in Composite classes
- Component interface has only common operations: `getName()`, `getPrice()`, `getDescription()`
- **Pro:** Type-safe, can't call `add()` on a leaf by mistake
- **Con:** Need to cast or check type when traversing tree

```
// Component (interface or abstract class)
interface MenuComponent {
    String getName();
    double getPrice();
    String getDescription();
    void display();
}

// Leaf - no add/remove methods
class MenuItem implements MenuComponent {
    // Only implements interface methods
}

// Composite - has child management
class ComboMeal implements MenuComponent {
    private List<MenuComponent> items = new ArrayList<>();

    public void add(MenuComponent item) { items.add(item); }
    public void remove(MenuComponent item) { items.remove(item); }
    public MenuComponent getChild(int i) { return items.get(i); }

    // Also implements interface methods
}
```

#### **Approach 2: Uniformity (Transparency)**

- Put `add()`, `remove()`, `getChild()` in the Component interface
- Leaf classes throw `UnsupportedOperationException` or do nothing
- **Pro:** Uniform interface, no casting needed
- **Con:** Not type-safe, runtime errors possible

```
// Component with all methods
abstract class MenuComponent {
    public void add(MenuComponent item) {
        throw new UnsupportedOperationException("Cannot add to leaf");
    }
    public void remove(MenuComponent item) {
        throw new UnsupportedOperationException("Cannot remove from leaf");
    }
    // ... common methods
}

// Leaf - inherits unsupported methods
class MenuItem extends MenuComponent {
    // add/remove throw exceptions if called
}

// Composite - overrides add/remove
class ComboMeal extends MenuComponent {
    @Override
    public void add(MenuComponent item) {
        items.add(item); // Actually works
    }
}
```

**My Recommendation: Use Approach 1 (Safety First)**

Why? For a showcase project:

- Clearer intent - composites obviously manage children
- No runtime surprises
- Better demonstrates understanding of design tradeoffs
- The small inconvenience of type checking is worth the safety

**Implementation Details:**

**MenuComponent (interface):**

```java
- getName(): String
- getPrice(): double  // Leaf: item price, Composite: sum of children
- getDescription(): String
- display(): void     // Prints item/structure
```

**MenuItem (Leaf):**

```java
- name, basePrice, description
- Implements all MenuComponent methods
- NO child management methods
```

**ComboMeal (Composite):**

```java
- name, description, discount (optional)
- List<MenuComponent> items
- add(MenuComponent), remove(MenuComponent), getChild(int)
- getPrice() returns sum of all items minus discount
- display() iterates and displays all children
```

**MenuCategory (Composite):**

```java
- categoryName (e.g., "Appetizers")
- List<MenuComponent> items
- Similar structure to ComboMeal
- Used for organizing menu, not for ordering
```

**Real Example:**

```
Restaurant Menu (MenuCategory - root)
├── Appetizers (MenuCategory)
│   ├── Garlic Bread (MenuItem) - $5
│   └── Mozzarella Sticks (MenuItem) - $7
├── Main Course (MenuCategory)
│   ├── Margherita Pizza (MenuItem) - $12
│   └── Value Combo (ComboMeal) - $15
│       ├── Burger (MenuItem) - $10
│       ├── Fries (MenuItem) - $4
│       └── Soda (MenuItem) - $2
└── Desserts (MenuCategory)
    └── Cheesecake (MenuItem) - $6
```

**Key Operations:**

- `menu.display()` - prints entire menu hierarchy
- `valueCombo.getPrice()` - returns $15 (or sum with discount)
- `mainCourse.add(newItem)` - adds item to category
- `valueCombo.add(extraItem)` - adds item to combo

**Why first:** Establishes your menu structure that other patterns will decorate/adapt/facade

---

### 2. **DECORATOR** - Dynamic Item Customization

**Priority: SECOND (Enhances Composite items)**

**Where to use:**

- Add toppings, extras, sides to menu items
- Modify item properties (extra cheese, no onions, spicy level)
- Calculate dynamic pricing based on additions

**The Problem Decorator Solves:**

You have a base item (Pizza) and want to add extras (cheese, bacon, etc.) dynamically at runtime. Without Decorator, you'd need a class for every combination: PizzaWithCheese, PizzaWithCheeseAndBacon, PizzaWithCheeseAndBaconAndMushrooms... explosion of classes!

**Structure:**

```
MenuComponent (interface from Composite)
├── MenuItem (base item)
└── MenuItemDecorator (abstract) - wraps MenuComponent
    ├── CheeseDecorator
    ├── BaconDecorator
    ├── ExtraSauceDecorator
    └── SpicyDecorator
```

**How Decorator Works:**

1. Decorator wraps a MenuComponent (the item being decorated)
2. Decorator IS-A MenuComponent (implements same interface)
3. Decorator HAS-A MenuComponent (holds reference to wrapped item)
4. Decorator adds behavior before/after delegating to wrapped item

**Implementation Details:**

**MenuItemDecorator (abstract):**

```java
abstract class MenuItemDecorator implements MenuComponent {
    protected MenuComponent wrappedItem;

    public MenuItemDecorator(MenuComponent item) {
        this.wrappedItem = item;
    }

    // Default: delegate to wrapped item
    public String getName() {
        return wrappedItem.getName();
    }

    public double getPrice() {
        return wrappedItem.getPrice();
    }

    public String getDescription() {
        return wrappedItem.getDescription();
    }
}
```

**Concrete Decorators:**

```java
class CheeseDecorator extends MenuItemDecorator {
    public CheeseDecorator(MenuComponent item) {
        super(item);
    }

    @Override
    public String getName() {
        return wrappedItem.getName() + " + Extra Cheese";
    }

    @Override
    public double getPrice() {
        return wrappedItem.getPrice() + 2.0;  // Add $2 for cheese
    }

    @Override
    public String getDescription() {
        return wrappedItem.getDescription() + ", with extra cheese";
    }
}

class BaconDecorator extends MenuItemDecorator {
    // Similar structure, adds $3 and modifies description
}

class SpicyDecorator extends MenuItemDecorator {
    // Adds spicy sauce, adds $1
}
```

**Real Example - Chaining Decorators:**

```java
// Start with base item
MenuComponent pizza = new MenuItem("Margherita Pizza", 12.0, "Classic pizza");

// Add cheese
pizza = new CheeseDecorator(pizza);
// Now: "Margherita Pizza + Extra Cheese", $14.0

// Add bacon
pizza = new BaconDecorator(pizza);
// Now: "Margherita Pizza + Extra Cheese + Bacon", $17.0

// Add spicy sauce
pizza = new SpicyDecorator(pizza);
// Now: "Margherita Pizza + Extra Cheese + Bacon + Spicy", $18.0

System.out.println(pizza.getName());     // Full decorated name
System.out.println(pizza.getPrice());    // $18.0
```

**Visual Wrapping:**

```
SpicyDecorator wraps →
    BaconDecorator wraps →
        CheeseDecorator wraps →
            MenuItem (Margherita Pizza)
```

When you call `pizza.getPrice()`:

1. SpicyDecorator adds $1 + calls wrapped.getPrice()
2. BaconDecorator adds $3 + calls wrapped.getPrice()
3. CheeseDecorator adds $2 + calls wrapped.getPrice()
4. MenuItem returns $12
5. Total: $12 + $2 + $3 + $1 = $18

**Suggested Decorators for Restaurant:**

- **CheeseDecorator** - extra cheese (+$2)
- **BaconDecorator** - add bacon (+$3)
- **ExtraSauceDecorator** - extra sauce (+$1)
- **SpicyDecorator** - make it spicy (+$1)
- **GlutenFreeDecorator** - gluten-free version (+$2)
- **LargePortionDecorator** - upsize (+$3)

**Integration with Composite:**

You can decorate:

- Individual MenuItem: `new CheeseDecorator(pizza)`
- Entire ComboMeal: `new SpicyDecorator(valueCombo)` - makes whole combo spicy
- Items within a combo before adding them

**Key Benefit:**
Add functionality at runtime without modifying original classes. Open/Closed Principle: open for extension, closed for modification.

---

### 3. **BRIDGE** - Order Processing vs Payment/Delivery Methods

**Priority: THIRD (Separates business from technical)**

**Where to use:**

- Separate order management (business logic) from how orders are fulfilled (implementation)

**The Problem Bridge Solves:**

Your current design: DeliveryOrder, DineInOrder, TakeawayOrder each contain BOTH:

- Business logic (pricing, discounts, validation)
- Technical details (GPS tracking, table assignment, pickup queues)

This creates **tight coupling**. If you want to:

- Change delivery provider (UberEats → DoorDash) → Must modify DeliveryOrder
- Add new order type (CateringOrder) → Must duplicate business logic
- Support multiple delivery methods → Need DeliveryOrderUberEats, DeliveryOrderDoorDash classes

**Bridge decouples these into two independent hierarchies.**

**Structure:**

```
Abstraction Hierarchy              Implementation Hierarchy
(Business Logic - WHAT)            (Technical Details - HOW)

OrderProcessor (abstract)          FulfillmentMethod (interface)
├── RestaurantOrderProcessor       ├── DeliveryFulfillment
└── CateringOrderProcessor         ├── DineInFulfillment
                                   └── TakeawayFulfillment
```

**Key Concept:**

- Abstraction HAS-A Implementation (composition, not inheritance)
- Both hierarchies can evolve independently
- Mix and match: RestaurantOrderProcessor can use ANY FulfillmentMethod

**Implementation Details:**

**Abstraction Side (Business Logic Layer):**

```java
abstract class OrderProcessor {
    protected FulfillmentMethod fulfillmentMethod;  // Bridge to implementation
    protected List<MenuComponent> items;
    protected String customerInfo;

    public OrderProcessor(FulfillmentMethod method) {
        this.fulfillmentMethod = method;
    }

    // Template method - business workflow
    public final void processOrder() {
        validateOrder();
        double total = calculateTotal();
        applyDiscounts();
        fulfillmentMethod.fulfill(this);  // Delegate to implementation
        generateReceipt();
    }

    protected abstract void validateOrder();
    protected abstract double calculateTotal();
    protected abstract void applyDiscounts();
    protected void generateReceipt() {
        // Common logic
    }
}

class RestaurantOrderProcessor extends OrderProcessor {
    public RestaurantOrderProcessor(FulfillmentMethod method) {
        super(method);
    }

    @Override
    protected void validateOrder() {
        // Restaurant-specific validation
        // Check if items are available
        // Validate customer info
    }

    @Override
    protected double calculateTotal() {
        // Sum item prices
        // Add taxes
        return total;
    }

    @Override
    protected void applyDiscounts() {
        // Apply restaurant loyalty discounts
        // Happy hour discounts
    }
}

class CateringOrderProcessor extends OrderProcessor {
    // Different business rules for large catering orders
    // Minimum order amount, advance booking required, etc.
}
```

**Implementation Side (Technical Layer):**

```java
interface FulfillmentMethod {
    void fulfill(OrderProcessor order);
    void notifyCustomer(String message);
    String getStatusUpdate();
}

class DeliveryFulfillment implements FulfillmentMethod {
    private String deliveryProvider;  // "UberEats", "DoorDash", etc.
    private GPSTracker tracker;

    @Override
    public void fulfill(OrderProcessor order) {
        // Technical delivery logic
        assignDriver();
        calculateRoute();
        tracker.startTracking();
        notifyCustomer("Driver assigned, ETA 30 mins");
    }

    private void assignDriver() {
        // Call delivery provider API
    }

    private void calculateRoute() {
        // GPS routing logic
    }

    @Override
    public String getStatusUpdate() {
        return "Order is 5 minutes away";
    }
}

class DineInFulfillment implements FulfillmentMethod {
    private TablePool tablePool;
    private KitchenDisplaySystem kds;

    @Override
    public void fulfill(OrderProcessor order) {
        // Technical dine-in logic
        Table table = tablePool.acquireTable();
        kds.sendToKitchen(order);
        notifyCustomer("Order sent to kitchen, table: " + table.getNumber());
    }
}

class TakeawayFulfillment implements FulfillmentMethod {
    private Queue<OrderProcessor> pickupQueue;

    @Override
    public void fulfill(OrderProcessor order) {
        // Technical takeaway logic
        pickupQueue.add(order);
        int queuePosition = pickupQueue.size();
        notifyCustomer("Order ready in 15 mins, position: " + queuePosition);
    }
}
```

**Real Example - Using Bridge:**

```java
// Business logic stays same, swap implementation
FulfillmentMethod delivery = new DeliveryFulfillment("UberEats");
OrderProcessor order1 = new RestaurantOrderProcessor(delivery);
order1.processOrder();  // Order processed via delivery

// Same business logic, different fulfillment
FulfillmentMethod dineIn = new DineInFulfillment();
OrderProcessor order2 = new RestaurantOrderProcessor(dineIn);
order2.processOrder();  // Order processed for dine-in

// Change delivery provider without touching business logic
FulfillmentMethod doorDash = new DeliveryFulfillment("DoorDash");
OrderProcessor order3 = new RestaurantOrderProcessor(doorDash);
order3.processOrder();  // Same business logic, different provider
```

**Benefits:**

1. **Decoupling:** Change delivery provider without modifying order processing logic
2. **Flexibility:** Mix any OrderProcessor with any FulfillmentMethod
3. **Scalability:** Add new fulfillment methods (DroneDelivery) or processors (CorporateOrderProcessor) independently
4. **Testability:** Test business logic without real delivery APIs

**Integration with Factory Method:**

Your existing factories now create OrderProcessor with appropriate FulfillmentMethod:

```java
class DeliveryOrderCreator extends OrderCreator {
    @Override
    public OrderProcessor createOrder() {
        return new RestaurantOrderProcessor(new DeliveryFulfillment("UberEats"));
    }
}
```

**Why Bridge (Teacher's Emphasis):**

- Separates "what you do" (order processing rules) from "how you do it" (delivery/dine-in mechanics)
- Business layer remains stable when technical integrations change
- Real-world scenario: restaurants switch delivery partners frequently

---

### 4. **FACADE** - Simplified Order Management System

**Priority: FOURTH (Unifies everything)**

**Where to use:**

- Create a simple interface hiding complex subsystems
- Hide Factory Method, Composite menus, Decorators from client
- Single entry point for restaurant operations

**The Problem Facade Solves:**

Your Demo.java client currently needs to know about:

- Which factory to use for creating orders
- How to access TablePool
- How to build menu structures with Composite
- How to chain Decorators
- How to work with Bridge pattern for fulfillment

This makes the client complex and tightly coupled to implementation details.

**Structure:**

```
Client (Demo.java)
    ↓ (uses only)
RestaurantFacade
    ↓ (delegates to)
├── OrderCreator factories
├── MenuComponent hierarchy
├── Decorator system
├── Bridge pattern (OrderProcessor + FulfillmentMethod)
├── PaymentProcessor adapters
├── TablePool
└── Restaurant singleton
```

**Implementation Details:**

```java
public class RestaurantFacade {
    // Subsystem components (hidden from client)
    private Restaurant restaurant;
    private MenuComponent menu;
    private TablePool tablePool;
    private Map<String, OrderProcessor> activeOrders;

    public RestaurantFacade() {
        // Initialize all subsystems
        this.restaurant = Restaurant.getInstance();
        this.menu = buildMenu();  // Creates Composite menu structure
        this.tablePool = TablePool.getInstance();
        this.activeOrders = new HashMap<>();
    }

    // Simple method 1: Place order (hides Factory + Bridge)
    public String placeOrder(String orderType, String customerInfo,
                            List<String> itemNames) {
        // 1. Get menu items from composite structure
        List<MenuComponent> items = new ArrayList<>();
        for (String name : itemNames) {
            MenuComponent item = findMenuItem(name);
            items.add(item);
        }

        // 2. Create appropriate fulfillment method
        FulfillmentMethod fulfillment = createFulfillment(orderType);

        // 3. Create order processor (hiding factory)
        OrderProcessor processor = new RestaurantOrderProcessor(fulfillment);
        processor.setItems(items);
        processor.setCustomerInfo(customerInfo);

        // 4. Process order
        String orderId = generateOrderId();
        processor.processOrder();
        activeOrders.put(orderId, processor);

        return orderId;
    }

    // Simple method 2: Customize item (hides Decorator)
    public MenuComponent customizeItem(String itemName,
                                       List<String> customizations) {
        MenuComponent item = findMenuItem(itemName);

        // Chain decorators based on customizations
        for (String custom : customizations) {
            switch(custom.toLowerCase()) {
                case "extra cheese":
                    item = new CheeseDecorator(item);
                    break;
                case "bacon":
                    item = new BaconDecorator(item);
                    break;
                case "spicy":
                    item = new SpicyDecorator(item);
                    break;
                // ... more decorators
            }
        }

        return item;
    }

    // Simple method 3: Process payment (hides Adapter)
    public boolean processPayment(String orderId, String paymentType,
                                   Map<String, String> paymentDetails) {
        OrderProcessor order = activeOrders.get(orderId);
        if (order == null) return false;

        // Select payment adapter
        PaymentProcessor paymentProcessor = getPaymentAdapter(paymentType);

        // Process payment
        double amount = order.getTotal();
        boolean success = paymentProcessor.processPayment(amount, paymentDetails);

        if (success) {
            order.markAsPaid();
        }

        return success;
    }

    // Simple method 4: Reserve table (hides Pool)
    public Table reserveTable(int partySize, String time) {
        return tablePool.acquireTable(partySize);
    }

    // Simple method 5: Get menu
    public void displayMenu() {
        menu.display();  // Composite pattern handles hierarchy
    }

    // Simple method 6: Check order status
    public String checkOrderStatus(String orderId) {
        OrderProcessor order = activeOrders.get(orderId);
        if (order == null) return "Order not found";

        return order.getFulfillmentMethod().getStatusUpdate();
    }

    // Private helper methods (internal complexity)
    private MenuComponent buildMenu() {
        // Build entire Composite menu structure
        MenuCategory menu = new MenuCategory("Restaurant Menu");

        MenuCategory appetizers = new MenuCategory("Appetizers");
        appetizers.add(new MenuItem("Garlic Bread", 5.0, "..."));
        // ... more items

        menu.add(appetizers);
        // ... more categories

        return menu;
    }

    private FulfillmentMethod createFulfillment(String type) {
        switch(type.toLowerCase()) {
            case "delivery":
                return new DeliveryFulfillment("UberEats");
            case "dine-in":
                return new DineInFulfillment();
            case "takeaway":
                return new TakeawayFulfillment();
            default:
                throw new IllegalArgumentException("Unknown order type");
        }
    }

    private PaymentProcessor getPaymentAdapter(String type) {
        switch(type.toLowerCase()) {
            case "credit":
                return new StripeAdapter();
            case "paypal":
                return new PayPalAdapter();
            case "cash":
                return new CashPaymentAdapter();
            default:
                throw new IllegalArgumentException("Unknown payment type");
        }
    }

    private MenuComponent findMenuItem(String name) {
        // Traverse composite structure to find item
        // Implementation details hidden from client
        return searchInMenu(menu, name);
    }
}
```

**Client Code BEFORE Facade:**

```java
// Demo.java - Complex and coupled
public class Demo {
    public static void main(String[] args) {
        // Client needs to know about factories
        OrderCreator creator = new DeliveryOrderCreator();

        // Client needs to build menu structure
        MenuItem pizza = new MenuItem("Pizza", 12.0, "...");
        MenuItem burger = new MenuItem("Burger", 10.0, "...");

        // Client needs to chain decorators
        MenuComponent customPizza = new CheeseDecorator(pizza);
        customPizza = new BaconDecorator(customPizza);

        // Client needs to understand Bridge pattern
        FulfillmentMethod fulfillment = new DeliveryFulfillment("UberEats");
        OrderProcessor processor = new RestaurantOrderProcessor(fulfillment);

        // Client needs to manage tables
        TablePool pool = TablePool.getInstance();
        Table table = pool.acquireTable(4);

        // ... much more complexity
    }
}
```

**Client Code AFTER Facade:**

```java
// Demo.java - Simple and clean
public class Demo {
    public static void main(String[] args) {
        RestaurantFacade restaurant = new RestaurantFacade();

        // 1. Display menu
        restaurant.displayMenu();

        // 2. Customize item
        MenuComponent customPizza = restaurant.customizeItem("Margherita Pizza",
            Arrays.asList("extra cheese", "bacon"));

        // 3. Place order
        String orderId = restaurant.placeOrder("delivery", "John Doe",
            Arrays.asList("Margherita Pizza", "Caesar Salad"));

        // 4. Process payment
        Map<String, String> cardDetails = new HashMap<>();
        cardDetails.put("cardNumber", "4242...");
        boolean paid = restaurant.processPayment(orderId, "credit", cardDetails);

        // 5. Check status
        String status = restaurant.checkOrderStatus(orderId);
        System.out.println("Order status: " + status);

        // 6. Reserve table
        Table table = restaurant.reserveTable(4, "19:00");
    }
}
```

**Key Benefits:**

1. **Simplification:** Client code reduced by ~70%
2. **Decoupling:** Client doesn't know about factories, pools, decorators
3. **Single Entry Point:** All restaurant operations through one class
4. **Flexibility:** Can change internal implementation without affecting client
5. **Requirement Met:** "There should only be one client for the whole system"

**What Facade Hides:**

- ✅ Factory Method pattern for creating orders
- ✅ Composite menu structure traversal
- ✅ Decorator chaining logic
- ✅ Bridge pattern complexity
- ✅ Adapter selection for payments
- ✅ Singleton and Pool management

---

### 5. **ADAPTER** - External Payment Gateway Integration

**Priority: FIFTH (External integration)**

**Where to use:**

- Integrate third-party payment systems with incompatible interfaces
- Connect to external services (payment processors, loyalty programs)

**The Problem Adapter Solves:**

You want to accept payments via Stripe, PayPal, and Cash, but each has a different interface:

- Stripe: `Stripe.charge(token, amount, currency)`
- PayPal: `PayPal.executePayment(payerId, paymentId)`
- Cash: Just record the transaction

Your system needs a unified interface: `PaymentProcessor.processPayment(amount, details)`

**Structure:**

```
Your System Interface           External Systems (Incompatible)

PaymentProcessor               StripeAPI
├── StripeAdapter    ─────────>  - charge(token, amount, currency)
├── PayPalAdapter    ─────────>  PayPalSDK
└── CashAdapter                   - executePayment(payerId, paymentId)

                                 CashRegister
                                  - recordTransaction(amount)
```

**Implementation Details:**

**Target Interface (What your system expects):**

```java
public interface PaymentProcessor {
    boolean processPayment(double amount, Map<String, String> paymentDetails);
    String getTransactionId();
    void refund(String transactionId, double amount);
}
```

**Adaptee 1: Stripe API (External library):**

```java
// External library - you can't modify this
public class StripeAPI {
    public String charge(String token, int amountInCents, String currency) {
        // Stripe-specific logic
        System.out.println("Charging $" + amountInCents/100 + " via Stripe");
        return "stripe_txn_" + System.currentTimeMillis();
    }

    public void refundCharge(String chargeId, int amountInCents) {
        System.out.println("Refunding stripe charge: " + chargeId);
    }
}
```

**Adapter 1: StripeAdapter:**

```java
public class StripeAdapter implements PaymentProcessor {
    private StripeAPI stripeAPI;
    private String lastTransactionId;

    public StripeAdapter() {
        this.stripeAPI = new StripeAPI();
    }

    @Override
    public boolean processPayment(double amount, Map<String, String> details) {
        try {
            // Convert your interface to Stripe's interface
            String token = details.get("cardToken");
            int amountInCents = (int)(amount * 100);  // Stripe uses cents
            String currency = "USD";

            // Call Stripe's method
            lastTransactionId = stripeAPI.charge(token, amountInCents, currency);

            return lastTransactionId != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getTransactionId() {
        return lastTransactionId;
    }

    @Override
    public void refund(String transactionId, double amount) {
        int amountInCents = (int)(amount * 100);
        stripeAPI.refundCharge(transactionId, amountInCents);
    }
}
```

**Adaptee 2: PayPal SDK (External library):**

```java
// External library - different interface
public class PayPalSDK {
    public boolean executePayment(String payerId, String paymentId) {
        System.out.println("Processing PayPal payment: " + paymentId);
        return true;
    }

    public String createPayment(double amount, String description) {
        return "paypal_" + System.currentTimeMillis();
    }

    public void refundPayment(String paymentId) {
        System.out.println("Refunding PayPal payment: " + paymentId);
    }
}
```

**Adapter 2: PayPalAdapter:**

```java
public class PayPalAdapter implements PaymentProcessor {
    private PayPalSDK payPalSDK;
    private String lastTransactionId;

    public PayPalAdapter() {
        this.payPalSDK = new PayPalSDK();
    }

    @Override
    public boolean processPayment(double amount, Map<String, String> details) {
        try {
            // PayPal requires two-step process
            String paymentId = payPalSDK.createPayment(amount, "Restaurant Order");
            String payerId = details.get("payerId");

            boolean success = payPalSDK.executePayment(payerId, paymentId);

            if (success) {
                lastTransactionId = paymentId;
            }

            return success;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getTransactionId() {
        return lastTransactionId;
    }

    @Override
    public void refund(String transactionId, double amount) {
        payPalSDK.refundPayment(transactionId);
    }
}
```

**Adaptee 3: Cash System (Internal):**

```java
// Your own simple cash system
public class CashRegister {
    public void recordTransaction(double amount, String cashierName) {
        System.out.println("Cash payment: $" + amount + " by " + cashierName);
    }
}
```

**Adapter 3: CashPaymentAdapter:**

```java
public class CashPaymentAdapter implements PaymentProcessor {
    private CashRegister cashRegister;
    private String lastTransactionId;

    public CashPaymentAdapter() {
        this.cashRegister = new CashRegister();
    }

    @Override
    public boolean processPayment(double amount, Map<String, String> details) {
        String cashierName = details.get("cashier");
        cashRegister.recordTransaction(amount, cashierName);

        lastTransactionId = "cash_" + System.currentTimeMillis();
        return true;
    }

    @Override
    public String getTransactionId() {
        return lastTransactionId;
    }

    @Override
    public void refund(String transactionId, double amount) {
        System.out.println("Cash refund: $" + amount + " for " + transactionId);
    }
}
```

**Usage Example:**

```java
// Your code works with uniform interface
PaymentProcessor processor;

// Pay with Stripe
processor = new StripeAdapter();
Map<String, String> stripeDetails = new HashMap<>();
stripeDetails.put("cardToken", "tok_visa");
processor.processPayment(50.00, stripeDetails);

// Pay with PayPal (same interface!)
processor = new PayPalAdapter();
Map<String, String> paypalDetails = new HashMap<>();
paypalDetails.put("payerId", "PAYER123");
processor.processPayment(50.00, paypalDetails);

// Pay with Cash (same interface!)
processor = new CashPaymentAdapter();
Map<String, String> cashDetails = new HashMap<>();
cashDetails.put("cashier", "Alice");
processor.processPayment(50.00, cashDetails);

// Your system doesn't care which one is used!
```

**Integration with Other Patterns:**

**With Bridge:**

```java
// Payment processing can be part of fulfillment
class DeliveryFulfillment implements FulfillmentMethod {
    private PaymentProcessor paymentProcessor;

    public void processPayment(double amount) {
        // Use adapter through uniform interface
        paymentProcessor.processPayment(amount, details);
    }
}
```

**With Facade:**

```java
// Facade selects appropriate adapter
public class RestaurantFacade {
    private PaymentProcessor getPaymentAdapter(String type) {
        switch(type) {
            case "credit": return new StripeAdapter();
            case "paypal": return new PayPalAdapter();
            case "cash": return new CashPaymentAdapter();
        }
    }
}
```

**Key Benefits:**

1. **Integration:** Connect to external systems without modifying their code
2. **Flexibility:** Easy to add new payment methods (Bitcoin, ApplePay)
3. **Testability:** Can create MockPaymentAdapter for testing
4. **Uniform Interface:** Rest of your code doesn't care about payment implementation

**Real-World Analogy:**
Like a power adapter when traveling - your device (your system) expects 110V US plug, but the wall (external system) provides 220V EU plug. The adapter converts between them.

---

## Optional Pattern: **FLYWEIGHT** - Shared Menu Item Data

### Does Flyweight Fit?

**YES, but with caveats:**

**Where it makes sense:**

- Share immutable menu item data across multiple orders
- Reduce memory when same items ordered frequently
- Separate intrinsic state (shared) from extrinsic state (order-specific)

**Implementation approach:**

```
Intrinsic State (shared via Flyweight):
- Item name, base price, description, category
- Preparation instructions, ingredients list
- Nutritional information

Extrinsic State (unique per order):
- Quantity ordered
- Custom modifications (from Decorator)
- Special instructions
- Order timestamp
```

**Concrete design:**

- `MenuItemFlyweightFactory` - manages shared MenuItem instances
- `OrderItem` - wrapper that combines Flyweight + extrinsic state (quantity, customizations)
- When 50 customers order "Margherita Pizza", only ONE MenuItemFlyweight exists in memory

### Should You Implement It?

**Pros:**

- Demonstrates memory optimization understanding
- Natural fit with restaurant ordering (items reused frequently)
- Complements Composite/Decorator nicely
- Gets you to 6 patterns (very impressive)

**Cons:**

- Less critical than the other 5 patterns
- Benefit only visible with large-scale data (thousands of orders)
- Adds complexity to the codebase
- May be harder to explain/demonstrate value in small demo

### Recommendation:

**Implement Flyweight IF:**

- You want to go above and beyond (6 patterns)
- You have time after implementing the core 5
- You can demo/explain memory savings clearly

**Skip Flyweight IF:**

- You're time-constrained
- 5 patterns already showcase your skills
- You want to focus on deeper implementation of core patterns

### Integration with other patterns:

If you implement Flyweight:

1. **With Composite:** MenuCategory holds references to Flyweight items
2. **With Decorator:** Decorators wrap Flyweight instances without duplicating base data
3. **With Factory:** Factory returns Flyweight instances instead of new objects each time
4. **In Facade:** `placeOrder()` uses FlyweightFactory to fetch shared menu items

---

## Alternative Pattern: **PROXY** (Another option for 6th pattern)

**Where to use:**

- Control access to expensive operations
- Add logging/security to order processing
- Lazy loading of menu data

**Options:**

- `OrderLoggingProxy` - logs all order operations before delegating
- `MenuCacheProxy` - caches menu data, loads on-demand
- `AuthorizationProxy` - checks permissions before processing orders (manager vs cashier)

**Verdict:** Proxy is easier to implement and demonstrate than Flyweight, but less unique to your domain.

---

## Implementation Order & Reasoning

### Phase 1: Foundation (Weeks 1-2)

1. **COMPOSITE** - Build menu structure
   - Refactor existing MenuItem into the composite pattern
   - Create MenuCategory and ComboMeal
   - Test hierarchical menu operations

### Phase 2: Enhancement (Week 2-3)

2. **DECORATOR** - Add customization
   - Create decorator base class
   - Implement 3-4 concrete decorators
   - Test price calculation and descriptions

### Phase 3: Separation (Week 3-4)

3. **BRIDGE** - Refactor order processing
   - Extract fulfillment logic from current Order classes
   - Create abstraction (OrderProcessor) and implementation (FulfillmentMethod) hierarchies
   - Rewire Factory Method to use Bridge

### Phase 4: Integration (Week 4)

4. **FACADE** - Simplify client interface

   - Create facade wrapping all subsystems
   - Refactor Demo.java to use only facade
   - Hide all factory methods and complex operations

5. **ADAPTER** - External integrations
   - Define payment processor interface
   - Create 2-3 adapters for different payment systems
   - Integrate with Bridge pattern's payment processing

### Phase 5: Optimization (Optional, Week 5)

6. **FLYWEIGHT** - Memory optimization
   - Create FlyweightFactory for menu items
   - Separate intrinsic/extrinsic state
   - Demonstrate memory savings in Demo

---

## Key Architectural Changes

### Client Simplification

**Before:** Client uses factories, pools, directly creates objects

```
Demo.java uses:
- OrderCreator factories
- Restaurant.getInstance()
- TablePool directly
- MenuItem builders
```

**After:** Client uses only Facade

```
Demo.java uses:
- RestaurantFacade.placeOrder()
- RestaurantFacade.customizeItem()
- RestaurantFacade.processPayment()
```

### Pattern Integration Map

```
Client → FACADE → {
    COMPOSITE (Menu structure)
    DECORATOR (Item customization)
    FLYWEIGHT (Shared menu data) ← Optional
    FACTORY METHOD (Order creation) → BRIDGE (Order processing)
    ADAPTER (Payment systems)
    SINGLETON (Restaurant)
    POOL (Tables)
}
```

---

## Business Logic vs Technical Implementation (Bridge Focus)

### Current Issue

Your `DeliveryOrder`, `DineInOrder`, `TakeawayOrder` currently mix:

- Business rules (pricing, validation, order composition)
- Technical details (delivery API, table assignment, pickup queue)

### Bridge Solution

**Abstraction Side (Business Logic):**

- Order validation rules
- Discount application
- Total calculation
- Order state management
- Customer information handling

**Implementation Side (Technical/Lower Level):**

- GPS tracking for delivery
- Kitchen display system integration
- Table reservation system
- Pickup notification system
- Third-party delivery platform APIs

**Benefit:** Can change delivery provider (UberEats → DoorDash) without touching business logic

---

## Flyweight Deep Dive: Memory Savings Example

### Without Flyweight:

```
100 orders × 10 menu items each = 1000 MenuItem objects in memory
Each MenuItem: ~500 bytes
Total: ~500KB for item data (lots of duplication)
```

### With Flyweight:

```
20 unique menu items (Flyweights) = 20 MenuItem objects
100 orders × 10 OrderItem wrappers = 1000 lightweight wrappers
Each Flyweight: ~500 bytes
Each wrapper: ~50 bytes
Total: ~10KB (Flyweights) + ~50KB (wrappers) = ~60KB
Savings: ~88% memory reduction
```

### When Flyweight Shines:

- Restaurant with 50+ menu items
- Peak hours: 200+ concurrent orders
- Multiple locations sharing same menu
- Mobile app needs to cache menu efficiently

---

## Success Criteria

✅ **At least 3 patterns** (you'll have 5-6)
✅ **Patterns solve real problems** (not just added for sake of it)
✅ **Single client** (Demo.java uses only Facade)
✅ **Creational patterns buried** (hidden behind Facade)
✅ **System functional** (can still place orders, customize items, process payments)

---

## Quick Start Checklist

- [ ] Review current MenuItem and menu structure
- [ ] Design Composite hierarchy (MenuComponent, MenuItem, ComboMeal, MenuCategory)
- [ ] Implement Composite with 2-3 menu categories and 1 combo meal
- [ ] Create Decorator base class and 3 concrete decorators
- [ ] Test decoration on composite items
- [ ] Analyze current Order classes to identify business vs technical logic
- [ ] Design Bridge abstraction (OrderProcessor) and implementation (FulfillmentMethod)
- [ ] Refactor Order creation to use Bridge
- [ ] Create Facade with 5-7 key operations
- [ ] Refactor Demo.java to use only Facade
- [ ] Design payment processor interface
- [ ] Implement 2-3 payment adapters
- [ ] Integrate adapters with Bridge/Facade
- [ ] (Optional) Implement Flyweight for menu item sharing
- [ ] (Optional) Demonstrate memory savings with Flyweight

---

## Final Recommendations

### Go with 5 patterns (safe, comprehensive):

1. Composite ⭐ (Foundation)
2. Decorator ⭐ (Enhancement)
3. Bridge ⭐ (Required by teacher)
4. Facade ⭐ (Integration)
5. Adapter ⭐ (External systems)

### Go with 6 patterns (impressive, ambitious):

Add either:

- **Flyweight** - If you want to show optimization skills and have time
- **Proxy** - If you want easier implementation with logging/caching

### My suggestion:

Start with the core 5. If implementation goes smoothly and you have time, add Flyweight to demonstrate advanced understanding. The teacher will be impressed by quality implementation of 5 patterns more than rushed implementation of 6.

---

## Notes

- **Don't overthink Bridge initially** - focus on separation: "what to do" vs "how to do it"
- **Composite before Decorator** - need structure before you can enhance it
- **Facade last** - needs all other patterns in place to wrap them
- **Test incrementally** - ensure each pattern works before adding next
- **Keep Demo.java simple** - the goal is one clean client showcasing all functionality
- **Flyweight is optional** - only add if it provides clear value without over-complicating

---

## Questions to Consider Before Starting

1. **Do you want to impress with 6 patterns or perfect 5?**

   - 6 with Flyweight = ambitious but more complex
   - 5 well-implemented = solid, clear, maintainable

2. **How much time do you have?**

   - 2-3 weeks → stick to 5 patterns
   - 4+ weeks → consider adding Flyweight

3. **What's your goal?**
   - Maximum grade with safety → 5 patterns
   - Stand out / portfolio piece → 6 patterns with Flyweight

Choose wisely and stick to your decision! 🚀
