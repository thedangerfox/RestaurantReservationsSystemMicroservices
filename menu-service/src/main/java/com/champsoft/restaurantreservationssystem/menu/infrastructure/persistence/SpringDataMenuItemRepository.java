package com.champsoft.restaurantreservationssystem.menu.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMenuItemRepository
        extends JpaRepository<MenuItemJpaEntity, Long> {
}
