# TMPS Laboratory Work #2 - Structural Design Patterns

**Author:** Țugui Artur  
**Group:** FAF-231

---

## Objectives:

- Get familiar with the Structural Design Patterns;
- Extend the existing Restaurant Management System with structural patterns;
- Implement at least 3 Structural DPs for the specific domain;

---

## Used Design Patterns:

- [To be implemented]
- [To be implemented]
- [To be implemented]

---

## Project Overview

### Domain: Restaurant Management System

This project continues the Restaurant Management System developed in Laboratory Work #1. The system models a restaurant with menu management, order processing, and table allocation using various design patterns.

### Existing Architecture (Lab 1 - Creational Patterns)

**Core Components:**

1. **Restaurant (Singleton)**

   - Single instance managing the entire restaurant
   - Contains menu (List of MenuItem) and table pool (TablePool)
   - Provides global access point for restaurant operations
   - Thread-safe lazy initialization with double-checked locking

2. **MenuItem (Builder + Prototype)**

   - Represents menu items (burgers, pizzas, drinks, etc.)
   - Built using Builder pattern for complex ingredient composition
   - Supports cloning via Prototype pattern for menu customization
   - Fields: `id`, `name`, `description`, `price`, `ingredients` (List<String>)

3. **Order Hierarchy (Factory Method)**

   - Abstract base class `Order` with three concrete implementations:
     - `DineInOrder` - includes `tableId` (Integer)
     - `DeliveryOrder` - includes `deliveryAddress` (String)
     - `TakeawayOrder` - no extra fields
   - Created via type-safe generic factories (`OrderCreator<T>`)
   - Each order contains a list of MenuItem objects

4. **Table (Domain Model)**

   - Represents physical tables in the restaurant
   - Fields: `id`, `capacity`, `isOccupied` (boolean)
   - Methods: `occupy()`, `release()`

5. **TablePool (Object Pool)**
   - Manages a pool of reusable Table objects
   - Thread-safe implementation with ConcurrentLinkedQueue
   - Methods: `acquireTable()`, `releaseTable()`

### Project Structure

```
src/main/java/com/ssintern/
├── client/
│   └── Demo.java                     # Client demonstration code
├── creational/
│   ├── singleton/
│   │   └── Restaurant.java           # Singleton pattern
│   ├── builderAlternative/
│   │   └── MenuItem.java             # Builder + Prototype patterns
│   ├── factory_method/
│   │   ├── Order.java                # Abstract product
│   │   ├── DineInOrder.java          # Concrete product
│   │   ├── DeliveryOrder.java        # Concrete product
│   │   ├── TakeawayOrder.java        # Concrete product
│   │   ├── OrderCreator.java         # Abstract creator
│   │   ├── DineInOrderCreator.java   # Concrete creator
│   │   ├── DeliveryOrderCreator.java # Concrete creator
│   │   └── TakeawayOrderCreator.java # Concrete creator
│   └── pool/
│       └── TablePool.java            # Object Pool pattern
└── domain/
    └── models/
        └── Table.java                # Domain model
```

### Current Design Characteristics

**Strengths:**

- ✅ Clear separation of concerns (creational patterns in separate packages)
- ✅ Type-safe generic implementation for Factory Method
- ✅ Thread-safe singleton and object pool
- ✅ Flexible menu item creation and customization
- ✅ Extensible order hierarchy with Open/Closed principle

**Extensibility Points for Structural Patterns:**

- MenuItem could be enhanced with decorators (add-ons, promotions, calorie info)
- Order processing could use facade to simplify complex operations
- External systems could integrate via adapters (payment gateways, inventory systems)
- Menu could be organized hierarchically using composite (categories, combo meals)
- MenuItem loading could be optimized with proxy (lazy loading of detailed descriptions, images)
- Pricing/preparation strategies could be separated using bridge

---

## Implementation

### [Pattern 1 Name] - [To be implemented]

**Purpose:**  
_Why this pattern is needed and what problem it solves in the restaurant system._

**Implementation:**

```java
// Code snippets will be added here
```

**Usage:**

```java
// Usage examples will be added here
```

**Benefits:**

- [Benefit 1]
- [Benefit 2]

---

### [Pattern 2 Name] - [To be implemented]

**Purpose:**  
_Why this pattern is needed and what problem it solves in the restaurant system._

**Implementation:**

```java
// Code snippets will be added here
```

**Usage:**

```java
// Usage examples will be added here
```

**Benefits:**

- [Benefit 1]
- [Benefit 2]

---

### [Pattern 3 Name] - [To be implemented]

**Purpose:**  
_Why this pattern is needed and what problem it solves in the restaurant system._

**Implementation:**

```java
// Code snippets will be added here
```

**Usage:**

```java
// Usage examples will be added here
```

**Benefits:**

- [Benefit 1]
- [Benefit 2]

---

## Output Examples

_Sample output demonstrating the structural patterns in action will be added here._

---
