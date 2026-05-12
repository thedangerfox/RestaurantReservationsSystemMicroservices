package com.champsoft.restaurantreservationssystem.menu.application.service;

import com.champsoft.restaurantreservationssystem.menu.application.exception.MenuItemNotFoundException;
import com.champsoft.restaurantreservationssystem.menu.application.port.out.MenuItemRepositoryPort;
import com.champsoft.restaurantreservationssystem.menu.domain.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MenuItemEligibilityServiceTest {

    @Test
    void existingIsEligible() {
        var repo = mock(MenuItemRepositoryPort.class);
        var item = new MenuItem(MenuItemId.of(1L), new MenuItemName("X"), new MenuItemPrice(new BigDecimal("1")));
        when(repo.findById(1L)).thenReturn(Optional.of(item));

        assertThat(new MenuItemEligibilityService(repo).isEligible(1L)).isTrue();
    }

    @Test
    void missingThrows() {
        var repo = mock(MenuItemRepositoryPort.class);
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> new MenuItemEligibilityService(repo).isEligible(99L))
                .isInstanceOf(MenuItemNotFoundException.class);
    }
}
