package com.ssintern.structural.composite;

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
}
