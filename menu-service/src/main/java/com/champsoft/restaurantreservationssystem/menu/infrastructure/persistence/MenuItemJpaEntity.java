package com.champsoft.restaurantreservationssystem.menu.infrastructure.persistence;

import com.champsoft.restaurantreservationssystem.menu.domain.model.MenuItemStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "menu_items")
public class MenuItemJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public MenuItemStatus status;
}


