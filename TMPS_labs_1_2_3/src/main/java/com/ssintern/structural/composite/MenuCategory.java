package com.ssintern.structural.composite;

import java.util.ArrayList;
import java.util.List;

public class MenuCategory implements MenuComponent {
    private int id;
    private String name;
    private String description;
    private List<MenuComponent> items = new ArrayList<>();

    public MenuCategory(String name, String description) {
        this.id = idCounter.incrementAndGet();
        this.name = name;
        this.description = description;
    }

    @Override
    public void add(MenuComponent item) {
        items.add(item);
    }

    @Override
    public void remove(MenuComponent item) {
        items.remove(item);
    }

    @Override
    public List<MenuComponent> getChildren() {
        return items;
    }

    public List<MenuComponent> getChildrem() {
        return items;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public List<MenuComponent> getItems() {
        return items;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void display() {
        display(0); // Start with no indentation
    }

    @Override
    public void display(int depth) {
        // Print category with indentation
        String indent = "  ".repeat(depth);
        System.out.println(indent + getName() + " - " + getDescription());

        // Recursively display all children with increased indentation
        for (MenuComponent item : items) {
            item.display(depth + 1); // Each child increases depth
        }
    }
}
