package com.champsoft.restaurantreservationssystem.table.application.port.out;

import com.champsoft.restaurantreservationssystem.table.domain.model.Table;

import java.util.List;
import java.util.Optional;

public interface TableRepositoryPort {

    Table save(Table table);

    Optional<Table> findById(Long id);

    List<Table> findAll();

    void deleteById(Long id);
}
