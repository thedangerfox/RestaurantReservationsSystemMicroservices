package com.champsoft.restaurantreservationssystem.menu.application.service;

import com.champsoft.restaurantreservationssystem.menu.application.exception.MenuItemNotFoundException;
import com.champsoft.restaurantreservationssystem.menu.application.port.out.MenuItemRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuItemEligibilityService {

    private final MenuItemRepositoryPort repo;

    public MenuItemEligibilityService(MenuItemRepositoryPort repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public boolean isEligible(Long menuItemId) {
        return repo.findById(menuItemId)
                .map(item -> true) // menu items have no ACTIVE/INACTIVE status
                .orElseThrow(() -> new MenuItemNotFoundException(menuItemId));
    }
}
