package com.champsoft.restaurantreservationssystem.menu.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuItemValueObjectsTest {

    @Test
    void menuItemIdAccepts() {
        assertThat(MenuItemId.of(1L).value()).isEqualTo(1L);
        assertThat(MenuItemId.newId().value()).isNull();
    }

    @Test
    void menuItemIdRejectsNonPositive() {
        assertThatThrownBy(() -> MenuItemId.of(0L)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> MenuItemId.of(-1L)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void menuItemNameAccepts() {
        assertThat(new MenuItemName("Pizza").value()).isEqualTo("Pizza");
    }

    @Test
    void menuItemNameRejectsNull() {
        assertThatThrownBy(() -> new MenuItemName(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void menuItemNameRejectsBlank() {
        assertThatThrownBy(() -> new MenuItemName("  ")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void menuItemNameRejectsTooLong() {
        assertThatThrownBy(() -> new MenuItemName("a".repeat(121)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("too long");
    }

    @Test
    void menuItemPriceAccepts() {
        assertThat(new MenuItemPrice(new BigDecimal("1.50")).value()).isEqualByComparingTo("1.50");
    }

    @Test
    void menuItemPriceRejectsNull() {
        assertThatThrownBy(() -> new MenuItemPrice(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("required");
    }

    @Test
    void menuItemPriceRejectsZero() {
        assertThatThrownBy(() -> new MenuItemPrice(BigDecimal.ZERO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("positive");
    }

    @Test
    void menuItemPriceRejectsNegative() {
        assertThatThrownBy(() -> new MenuItemPrice(new BigDecimal("-1")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void menuItemStatusValues() {
        assertThat(MenuItemStatus.values()).contains(MenuItemStatus.ACTIVE, MenuItemStatus.INACTIVE);
        assertThat(MenuItemStatus.valueOf("ACTIVE")).isEqualTo(MenuItemStatus.ACTIVE);
    }
}
