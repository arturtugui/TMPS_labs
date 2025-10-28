package com.ssintern.creational.singleton;

public class Restaurant {
    private static Restaurant instance;

    public static Restaurant getInstance() {
        if (instance == null) {
            instance = new Restaurant();
        }
        return instance;
    }



    public void acquireTable(int id) {
        System.out.println("Table acquired.");
        // getTableById(id).occupy();
    }
}
