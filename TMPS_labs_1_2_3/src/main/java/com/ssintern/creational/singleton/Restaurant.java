package com.ssintern.creational.singleton;

import com.ssintern.creational.builderAlternative.MenuItem;
import com.ssintern.creational.pool.TablePool;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private static volatile Restaurant instance;

    private TablePool tablePool;
    private String name;
    private List<MenuItem> menu;

    private Restaurant() {
        this.tablePool = new TablePool(5);
        this.name = "The Gourmet Restaurant";
        this.menu = new ArrayList<>();
    }

    public static Restaurant getInstance() {
        if (instance == null) {
            synchronized (Restaurant.class) {
                if (instance == null) {
                    instance = new Restaurant();
                }
            }
        }
        return instance;
    }

    public TablePool getTablePool() {
        return tablePool;
    }

    public String getName() {
        return name;
    }

    public void addMenuItem(MenuItem item) {
        menu.add(item);
        System.out.println("Added to menu: " + item.getName());
    }

    public List<MenuItem> getMenu() {
        return new ArrayList<>(menu); // Return copy to protect encapsulation
    }

    public void displayMenu() {
        System.out.println("\n========== " + name + " Menu ==========");
        if (menu.isEmpty()) {
            System.out.println("  (Menu is empty)");
        } else {
            for (int i = 0; i < menu.size(); i++) {
                MenuItem item = menu.get(i);
                System.out.println((i + 1) + ". " + item.getName() + " - $" +
                        String.format("%.2f", item.getPrice()));
                System.out.println("   " + item.getDescription());
                if (item.getIngredients() != null && !item.getIngredients().isEmpty()) {
                    System.out.println("   Ingredients: " + String.join(", ", item.getIngredients()));
                }
                System.out.println();
            }
        }
        System.out.println("========================================\n");
    }
}
