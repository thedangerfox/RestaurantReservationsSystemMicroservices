package com.champsoft.restaurantreservationssystem.table.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTableRepository
        extends JpaRepository<TableJpaEntity, Long> {
}
