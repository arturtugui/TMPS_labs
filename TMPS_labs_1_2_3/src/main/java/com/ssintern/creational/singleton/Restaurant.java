package com.ssintern.creational.singleton;

import com.ssintern.creational.pool.TablePool;

public class Restaurant {
    private static final Restaurant instance = new Restaurant();

    private TablePool tablePool;
    private String menu;

    private Restaurant() {
        this.tablePool = new TablePool(5);
        this.menu = "Sample Menu";
    }

    public static Restaurant getInstance() {
        return instance; // Always returns same instance
    }

    public TablePool getTablePool() {
        return tablePool;
    }

    public String getMenu() {
        return menu;
    }
}
