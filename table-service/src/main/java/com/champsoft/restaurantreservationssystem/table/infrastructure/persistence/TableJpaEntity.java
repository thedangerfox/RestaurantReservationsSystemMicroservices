package com.champsoft.restaurantreservationssystem.table.infrastructure.persistence;

import com.champsoft.restaurantreservationssystem.table.domain.model.TableStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "restaurant_tables")
public class TableJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public Integer tableNumber;

    @Column(nullable = false)
    public Integer capacity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public TableStatus status;
}


