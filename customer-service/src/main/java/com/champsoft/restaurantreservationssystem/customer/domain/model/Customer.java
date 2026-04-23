package com.champsoft.restaurantreservationssystem.customer.domain.model;

public class Customer {

    private final CustomerId id;
    private CustomerName name;
    private CustomerPhone phone;
    private CustomerStatus status;

    public Customer(CustomerId id, CustomerName name, CustomerPhone phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.status = CustomerStatus.ACTIVE;
    }

    public CustomerId id() { return id; }
    public CustomerName name() { return name; }
    public CustomerPhone phone() { return phone; }
    public CustomerStatus status() { return status; }

    public void update(CustomerName newName, CustomerPhone newPhone) {
        this.name = newName;
        this.phone = newPhone;
    }

    public void deactivate() {
        this.status = CustomerStatus.INACTIVE;
    }
}
