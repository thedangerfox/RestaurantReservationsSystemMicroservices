package com.champsoft.restaurantreservationssystem.table.domain.model;

public class Table {

    private final TableId id;
    private TableNumber number;
    private TableCapacity capacity;
    private TableStatus status;

    public Table(TableId id, TableNumber number, TableCapacity capacity) {
        this.id = id;
        this.number = number;
        this.capacity = capacity;
        this.status = TableStatus.AVAILABLE;
    }

    public TableId id() { return id; }
    public TableNumber number() { return number; }
    public TableCapacity capacity() { return capacity; }
    public TableStatus status() { return status; }

    public void update(TableNumber newNumber, TableCapacity newCapacity) {
        this.number = newNumber;
        this.capacity = newCapacity;
    }

    public void changeStatus(TableStatus newStatus) {
        this.status = newStatus;
    }
}
