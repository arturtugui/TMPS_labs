package com.ssintern.structural.decorator;

import com.ssintern.structural.composite.MenuComponent;

/**
 * Abstract base decorator for MenuComponent.
 * Delegates all calls to wrapped component by default.
 * Subclasses override to add extra behavior.
 */
public abstract class MenuItemDecorator implements MenuComponent {
    protected MenuComponent wrappedItem;

    public MenuItemDecorator(MenuComponent item) {
        this.wrappedItem = item;
    }

    @Override
    public int getId() {
        return wrappedItem.getId();
    }

    @Override
    public String getName() {
        return wrappedItem.getName();
    }

    @Override
    public String getDescription() {
        return wrappedItem.getDescription();
    }

    @Override
    public void display() {
        wrappedItem.display();
    }

    @Override
    public void display(int depth) {
        wrappedItem.display(depth);
    }
}
