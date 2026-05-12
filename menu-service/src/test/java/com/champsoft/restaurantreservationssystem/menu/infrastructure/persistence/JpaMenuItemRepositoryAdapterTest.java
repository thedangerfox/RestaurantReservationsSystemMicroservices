package com.champsoft.restaurantreservationssystem.menu.infrastructure.persistence;

import com.champsoft.restaurantreservationssystem.menu.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JpaMenuItemRepositoryAdapterTest {

    private SpringDataMenuItemRepository jpa;
    private JpaMenuItemRepositoryAdapter adapter;

    @BeforeEach
    void setup() {
        jpa = mock(SpringDataMenuItemRepository.class);
        adapter = new JpaMenuItemRepositoryAdapter(jpa);
    }

    private MenuItemJpaEntity entity(Long id, String name, String price) {
        var e = new MenuItemJpaEntity();
        e.id = id;
        e.name = name;
        e.price = new BigDecimal(price);
        e.status = MenuItemStatus.ACTIVE;
        return e;
    }

    @Test
    void shouldSave() {
        var item = new MenuItem(MenuItemId.of(1L), new MenuItemName("Pizza"), new MenuItemPrice(new BigDecimal("12")));
        when(jpa.save(any(MenuItemJpaEntity.class))).thenReturn(entity(1L, "Pizza", "12"));

        var saved = adapter.save(item);

        assertThat(saved.name().value()).isEqualTo("Pizza");
        verify(jpa).save(any(MenuItemJpaEntity.class));
    }

    @Test
    void shouldFindById() {
        when(jpa.findById(1L)).thenReturn(Optional.of(entity(1L, "Pizza", "12")));
        assertThat(adapter.findById(1L)).isPresent();
    }

    @Test
    void shouldReturnEmpty() {
        when(jpa.findById(99L)).thenReturn(Optional.empty());
        assertThat(adapter.findById(99L)).isEmpty();
    }

    @Test
    void shouldFindAll() {
        when(jpa.findAll()).thenReturn(List.of(entity(1L, "A", "1"), entity(2L, "B", "2")));
        assertThat(adapter.findAll()).hasSize(2);
    }

    @Test
    void shouldDelete() {
        adapter.deleteById(5L);
        verify(jpa).deleteById(5L);
    }
}
