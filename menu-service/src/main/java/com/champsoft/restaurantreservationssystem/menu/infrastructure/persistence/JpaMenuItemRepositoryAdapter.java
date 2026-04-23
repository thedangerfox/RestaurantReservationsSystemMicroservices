package com.champsoft.restaurantreservationssystem.menu.infrastructure.persistence;

import com.champsoft.restaurantreservationssystem.menu.application.port.out.MenuItemRepositoryPort;
import com.champsoft.restaurantreservationssystem.menu.domain.model.MenuItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaMenuItemRepositoryAdapter implements MenuItemRepositoryPort {

    private final SpringDataMenuItemRepository jpa;

    public JpaMenuItemRepositoryAdapter(SpringDataMenuItemRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public MenuItem save(MenuItem item) {
        var entity = MenuItemMapper.toEntity(item);
        var saved = jpa.save(entity);
        return MenuItemMapper.toDomain(saved);
    }

    @Override
    public Optional<MenuItem> findById(Long id) {
        return jpa.findById(id).map(MenuItemMapper::toDomain);
    }

    @Override
    public List<MenuItem> findAll() {
        return jpa.findAll().stream()
                .map(MenuItemMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }
}
