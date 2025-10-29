package com.ssintern.domain.models;

import java.util.concurrent.atomic.AtomicInteger;

public class Table {
    public static final AtomicInteger idCounter = new AtomicInteger(0);

    private int id;
    private int capacity;
    private boolean isOccupied;

    public Table(int capacity) {
        setId(idCounter.incrementAndGet());
        this.capacity = capacity;
        this.isOccupied = false;
    }

    public void occupy() {
        this.isOccupied = true;
    }

    public void release() {
        this.isOccupied = false;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive.");
        }
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", isOccupied=" + isOccupied +
                '}';
    }
}
