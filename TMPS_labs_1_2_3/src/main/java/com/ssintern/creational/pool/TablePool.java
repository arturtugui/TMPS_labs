package com.ssintern.creational.pool;

import com.ssintern.domain.models.Table;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TablePool {
    private ConcurrentLinkedQueue<Table> tablesPool = new ConcurrentLinkedQueue<>();
    private int maxSize;

    public TablePool(int maxSize) {
        this.maxSize = maxSize;
        initializePool();
    }

    private void initializePool() {
        for (int i = 0; i < maxSize; i++) {
            tablesPool.add(createTable());
        }
    }

    private Table createTable() {
        return new Table(4);
    }

    public Table acquireTable() {
        Table table = tablesPool.poll();
        if (table == null) {
            throw new RuntimeException("No tables available");
        }

        table.occupy();
        return table;
    }

    public void releaseTable(Table table) {
        if (table != null && tablesPool.size() < maxSize) {
            table.release();
            tablesPool.offer(table);
        }
    }

    public void setMaxPoolSize(int newMaxSize) {
        if (newMaxSize <= 0) {
            throw new IllegalArgumentException("Max pool size must be positive.");
        }
        this.maxSize = newMaxSize;
    }

    public int getMaxPoolSize() {
        return maxSize;
    }
}
