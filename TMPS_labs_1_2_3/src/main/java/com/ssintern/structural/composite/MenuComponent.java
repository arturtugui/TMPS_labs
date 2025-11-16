package com.ssintern.structural.composite;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface MenuComponent {
    public static final AtomicInteger idCounter = new AtomicInteger(1);

    int getId();

    String getName();

    String getDescription();

    void display();

    void display(int depth); // Overloaded method for hierarchical display with indentation

    // Composite operations - default implementation throws exception
    // This allows uniform interface but maintains type safety through runtime
    // checks
    default void add(MenuComponent component) {
        throw new UnsupportedOperationException("Cannot add to a leaf component");
    }

    default void remove(MenuComponent component) {
        throw new UnsupportedOperationException("Cannot remove from a leaf component");
    }

    default List<MenuComponent> getChildren() {
        throw new UnsupportedOperationException("Leaf components have no children");
    }

    // Ingredient-related operations - for MenuItem decoration
    default List<String> getIngredients() {
        return Collections.emptyList(); // Categories have no ingredients
    }

    default void addIngredient(String ingredient) {
        throw new UnsupportedOperationException("Cannot add ingredients to this component");
    }

    // Price operations - for decoration and ordering
    default double getPrice() {
        return 0.0; // Default price for components without price
    }

    default void setPrice(double price) {
        throw new UnsupportedOperationException("Cannot set price on this component");
    }

    // For creating modified copies (Prototype pattern integration)
    default MenuComponent createModifiedCopy() {
        throw new UnsupportedOperationException("Cannot create modified copy of this component");
    }
}
