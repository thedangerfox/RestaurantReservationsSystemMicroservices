package com.champsoft.restaurantreservationssystem.menu.api.mapper;

import com.champsoft.restaurantreservationssystem.menu.domain.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MenuItemApiMapperTest {

    @Test
    void shouldMapToResponse() {
        var item = new MenuItem(MenuItemId.of(1L), new MenuItemName("Pizza"), new MenuItemPrice(new BigDecimal("12.99")));

        var response = MenuItemApiMapper.toResponse(item);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Pizza");
        assertThat(response.price()).isEqualByComparingTo("12.99");
        assertThat(response.status()).isEqualTo("ACTIVE");
    }

    @Test
    void shouldMapDeactivated() {
        var item = new MenuItem(MenuItemId.of(2L), new MenuItemName("X"), new MenuItemPrice(new BigDecimal("1")));
        item.deactivate();

        assertThat(MenuItemApiMapper.toResponse(item).status()).isEqualTo("INACTIVE");
    }

    @Test
    void mapperCanBeInstantiated() {
        new MenuItemApiMapper();
    }
}
