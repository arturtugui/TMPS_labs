# TMPS Laboratory Work #0 - SOLID Principles

**Author:** Țugui Artur  
**Group:** FAF-231

---

## Objective

Implement 3 SOLID letters in a simple project

---

## Table of Contents

1. [Definitions](#definitions)
2. [What Was Implemented](#what-was-implemented)
3. [Code Examples](#code-examples)

---

## Definitions

### SOLID Principles

**SOLID** is an acronym representing five fundamental principles of object-oriented design that make software designs more understandable, flexible, and maintainable:

#### **S - Single Responsibility Principle (SRP)**

> _"A class should have only one reason to change."_

Each class should have only one job or responsibility. This makes the code easier to understand, test, and maintain.

#### **O - Open/Closed Principle (OCP)**

> _"Software entities should be open for extension but closed for modification."_

You should be able to add new functionality without changing existing code. This is achieved through abstraction and polymorphism.

#### **L - Liskov Substitution Principle (LSP)**

> _"Objects of a superclass should be replaceable with objects of a subclass without breaking the application."_

Derived classes must be substitutable for their base classes without altering the correctness of the program.

#### **I - Interface Segregation Principle (ISP)**

> _"Clients should not be forced to depend on interfaces they do not use."_

Many small, specific interfaces are better than one large, general-purpose interface.

#### **D - Dependency Inversion Principle (DIP)**

> _"High-level modules should not depend on low-level modules. Both should depend on abstractions."_

Depend on abstractions (interfaces) rather than concrete implementations.

---

## What Was Implemented

This project implements a **Shape Drawing Application** that demonstrates all five SOLID principles through a geometric shapes system with rendering capabilities.

### Features Implemented:

1. **Geometric Shapes System**

   - Three concrete shapes: Circle, Rectangle, Triangle
   - Each shape can calculate its area and perimeter
   - All shapes share a common `Shape` interface

2. **Transformation Capabilities**

   - **Movable**: Shapes can be moved/translated
   - **Scalable**: Shapes can be resized
   - **Rotatable**: Shapes can be rotated around their center

3. **Rendering System**

   - Separation between geometry and graphics
   - `Drawable` interface for rendering
   - Wrapper classes for each shape type

4. **Shape Management**

   - `ShapeManager` class to manage multiple drawable shapes
   - Operations: add, remove, draw all, clear

5. **Utility Classes**
   - `Point` class for 2D coordinate operations
   - Helper methods for distance and rotation calculations

---

## Code Examples

### 1. Single Responsibility Principle (SRP)

**Separation of Concerns**: Geometry vs Graphics vs Management

**Circle.java** - Handles only geometric calculations and transformations:

- Responsibilities: `getArea()`, `getPerimeter()`, `move()`, `scale()`
- Stores geometric data (center point, radius)
- Performs mathematical calculations

**DrawableCircle.java** - Handles only rendering:

- Responsibility: `draw()`
- Formats and displays circle information to console
- No geometric calculations, only presentation

**ShapeManager.java** - Handles only shape collection management:

- Manages collection of drawable shapes
- No knowledge of specific shape types or rendering details

**Each class has exactly one reason to change.**

---

### 2. Open/Closed Principle (OCP)

**Open for extension, closed for modification**

**Shape Interface** - Allows adding new shapes without modifying existing code:

```java
package BaseConceptInterfaces;

public interface Shape {
    double getArea();
    double getPerimeter();
}
```

**Adding a new shape** (e.g., Pentagon) requires:

- Creating a new `Pentagon` class implementing `Shape`
- NO modification to existing `Circle`, `Rectangle`, or `Triangle`
- NO modification to `ShapeManager`

**Important Note on OCP in this implementation:**

While adding a **new shape type** follows OCP perfectly, adding a **new capability** (like `Scalable` or `Rotatable`) to an existing shape does require modifying that shape's class signature:

```java
// Adding Scalable to Circle requires modifying the class declaration
public class Circle implements Shape, Movable, Scalable {
    @Override
    public void scale(double factor) {
        this.radius *= factor;
    }
}
```

---

### 3. Liskov Substitution Principle (LSP)

**Derived classes must be substitutable for base classes**

All shapes correctly implement the `Shape` interface contract:

- All shapes return valid positive numbers for area and perimeter
- No shape throws unexpected exceptions
- All shapes maintain geometric correctness

**Any shape can substitute the Shape interface without breaking the program.**

---

### 4. Interface Segregation Principle (ISP)

**Many small interfaces rather than one large interface**

Segregated interfaces:

```java
// Base concept - all shapes must have these
public interface Shape {
    double getArea();
    double getPerimeter();
}

// Optional behaviors - implement only if needed
public interface Movable { void move(double dx, double dy); }
public interface Scalable { void scale(double factor); }
public interface Rotatable { void rotate(double angleRadians); }
```

Usage:

```java
public class Circle implements Shape, Movable, Scalable {
    // No forced rotation implementation
}

public class Rectangle implements Shape, Movable, Scalable, Rotatable {
    // Full transformation support
}
```

**Clients only depend on the interfaces they actually use.**

---

### 5. Dependency Inversion Principle (DIP)

**Depend on abstractions, not concrete classes**

**ShapeManager** depends on the `Drawable` interface:

```java

public class ShapeManager {
    private List<Drawable> shapes;  // Depends on abstraction
    public void drawAll() {
        for (Drawable shape : shapes) {
            shape.draw();  // Works for any Drawable implementation
        }
    }
}
```

- ShapeManager doesn't know about specific shape types
- Can add new drawable types without changing ShapeManager
- Easy to test (can use mock Drawable objects)

Usage:

```java
ShapeManager manager = new ShapeManager();
manager.addShape(new DrawableCircle(circle));
manager.addShape(new DrawableRectangle(rect));
manager.addShape(new DrawableTriangle(triangle));
// Can add any future Drawable without changing ShapeManager
```

**High-level module (ShapeManager) depends on abstraction (Drawable), not concrete classes.**

**Important Note on DIP:**

While `ShapeManager` correctly depends on the `Drawable` abstraction, the `DrawableCircle`, `DrawableRectangle`, and `DrawableTriangle` classes depend on **concrete** shape classes (`Circle`, `Rectangle`, `Triangle`) rather than the `Shape` interface:

```java
public class DrawableCircle implements Drawable {
    private Circle circle;  // Depends on concrete Circle, not Shape interface
    
    public void draw() {
        // Needs Circle-specific methods like getRadius(), getCenter()
    }
}
```

