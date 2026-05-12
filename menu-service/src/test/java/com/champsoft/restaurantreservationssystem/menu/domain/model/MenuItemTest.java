package com.champsoft.restaurantreservationssystem.menu.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MenuItemTest {

    @Test
    void shouldCreateActiveByDefault() {
        var item = new MenuItem(
                MenuItemId.of(1L),
                new MenuItemName("Pizza"),
                new MenuItemPrice(new BigDecimal("12.99"))
        );

        assertThat(item.id().value()).isEqualTo(1L);
        assertThat(item.name().value()).isEqualTo("Pizza");
        assertThat(item.price().value()).isEqualByComparingTo("12.99");
        assertThat(item.status()).isEqualTo(MenuItemStatus.ACTIVE);
    }

    @Test
    void shouldUpdate() {
        var item = new MenuItem(MenuItemId.of(1L), new MenuItemName("Old"), new MenuItemPrice(new BigDecimal("1")));
        item.update(new MenuItemName("New"), new MenuItemPrice(new BigDecimal("2")));

        assertThat(item.name().value()).isEqualTo("New");
        assertThat(item.price().value()).isEqualByComparingTo("2");
    }

    @Test
    void shouldDeactivate() {
        var item = new MenuItem(MenuItemId.of(1L), new MenuItemName("X"), new MenuItemPrice(new BigDecimal("1")));
        item.deactivate();
        assertThat(item.status()).isEqualTo(MenuItemStatus.INACTIVE);
    }
}
