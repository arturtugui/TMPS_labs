package com.ssintern.creational.builderAlternative;

import com.ssintern.structural.composite.MenuComponent;

import java.util.ArrayList;
import java.util.List;

public class MenuItem implements Cloneable, MenuComponent {
    private int id;
    private String name;
    private String description;
    private double price;
    private List<String> ingredients;

    public MenuItem(MenuItemBuilder builder) {
        this.id = idCounter.incrementAndGet();
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
        this.ingredients = new ArrayList<>(builder.ingredients);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive.");
        }
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getIngredients() {
        return new ArrayList<>(ingredients); // Return copy to protect encapsulation
    }

    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }

    // for prototype CDP
    @Override
    public MenuItem clone() {
        try {
            MenuItem cloned = (MenuItem) super.clone();
            // Deep copy the ingredients list
            cloned.ingredients = new ArrayList<>(this.ingredients);
            // Generate new unique ID for the cloned item
            cloned.id = idCounter.incrementAndGet();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", ingredients=" + ingredients +
                '}';
    }

    @Override
    public void display() {
        display(0); // Default: no indentation
    }

    @Override
    public void display(int depth) {
        // MenuItem is a leaf - just print itself with indentation
        String indent = "  ".repeat(depth);
        System.out.println(indent + name + " - $" + price + " - " + description);
        // No children to display - recursion stops here naturally!
    }

    public static class MenuItemBuilder {
        private String name;
        private String description;
        private double price;
        private List<String> ingredients = new ArrayList<>();

        public MenuItemBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public MenuItemBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public MenuItemBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        public MenuItemBuilder addIngredient(String ingredient) {
            this.ingredients.add(ingredient);
            return this;
        }

        public MenuItem build() {
            return new MenuItem(this);
        }
    }
}
