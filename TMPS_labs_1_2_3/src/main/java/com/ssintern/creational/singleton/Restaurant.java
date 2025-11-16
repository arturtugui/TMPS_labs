package com.ssintern.creational.singleton;

import com.ssintern.creational.builderAlternative.MenuItem;
import com.ssintern.creational.pool.TablePool;
import com.ssintern.structural.composite.MenuComponent;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private static volatile Restaurant instance;

    private TablePool tablePool;
    private String name;
    private List<MenuComponent> menu;

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

    public void addMenuComponent(MenuComponent component) {
        menu.add(component);
    }

    public List<MenuComponent> getMenu() {
        return new ArrayList<>(menu); // Return copy to protect encapsulation
    }

    public void displayMenu() {
        System.out.println("\n=== " + name + " Menu ===");
        for (MenuComponent item : menu) {
            item.display();
        }
        System.out.println("======================\n");
    }
}
