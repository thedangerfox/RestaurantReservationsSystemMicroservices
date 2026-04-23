package com.champsoft.restaurantreservationssystem.menu.application.port.out;

import com.champsoft.restaurantreservationssystem.menu.domain.model.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepositoryPort {

    MenuItem save(MenuItem item);

    Optional<MenuItem> findById(Long id);

    List<MenuItem> findAll();

    void deleteById(Long id);
}
