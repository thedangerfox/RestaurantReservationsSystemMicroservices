package com.champsoft.restaurantreservationssystem.menu.application.service;

import com.champsoft.restaurantreservationssystem.menu.application.exception.MenuItemNotFoundException;
import com.champsoft.restaurantreservationssystem.menu.application.port.out.MenuItemRepositoryPort;
import com.champsoft.restaurantreservationssystem.menu.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MenuItemCrudServiceTest {

    private MenuItemRepositoryPort repo;
    private MenuItemCrudService service;

    @BeforeEach
    void setup() {
        repo = mock(MenuItemRepositoryPort.class);
        service = new MenuItemCrudService(repo);
    }

    @Test
    void shouldCreate() {
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
        var item = service.create("Pizza", new BigDecimal("12.99"));
        assertThat(item.name().value()).isEqualTo("Pizza");
    }

    @Test
    void getByIdReturns() {
        var item = new MenuItem(MenuItemId.of(1L), new MenuItemName("X"), new MenuItemPrice(new BigDecimal("1")));
        when(repo.findById(1L)).thenReturn(Optional.of(item));
        assertThat(service.getById(1L)).isSameAs(item);
    }

    @Test
    void getByIdThrows() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getById(99L)).isInstanceOf(MenuItemNotFoundException.class);
    }

    @Test
    void shouldList() {
        when(repo.findAll()).thenReturn(List.of());
        assertThat(service.list()).isEmpty();
    }

    @Test
    void shouldUpdate() {
        var item = new MenuItem(MenuItemId.of(1L), new MenuItemName("Old"), new MenuItemPrice(new BigDecimal("1")));
        when(repo.findById(1L)).thenReturn(Optional.of(item));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var updated = service.update(1L, "New", new BigDecimal("5"));

        assertThat(updated.name().value()).isEqualTo("New");
        assertThat(updated.price().value()).isEqualByComparingTo("5");
    }

    @Test
    void shouldDelete() {
        var item = new MenuItem(MenuItemId.of(1L), new MenuItemName("X"), new MenuItemPrice(new BigDecimal("1")));
        when(repo.findById(1L)).thenReturn(Optional.of(item));

        service.delete(1L);

        verify(repo).deleteById(1L);
    }

    @Test
    void deleteMissingThrows() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(MenuItemNotFoundException.class);
    }
}
