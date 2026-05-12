package com.champsoft.restaurantreservationssystem.menu.infrastructure.persistence;

import com.champsoft.restaurantreservationssystem.menu.domain.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MenuItemMapperTest {

    @Test
    void shouldMapEntityToDomain() {
        var entity = new MenuItemJpaEntity();
        entity.id = 1L;
        entity.name = "Burger";
        entity.price = new BigDecimal("9.99");
        entity.status = MenuItemStatus.ACTIVE;

        var domain = MenuItemMapper.toDomain(entity);

        assertThat(domain.id().value()).isEqualTo(1L);
        assertThat(domain.name().value()).isEqualTo("Burger");
        assertThat(domain.price().value()).isEqualByComparingTo("9.99");
    }

    @Test
    void shouldMapDomainToEntity() {
        var item = new MenuItem(MenuItemId.of(7L), new MenuItemName("Soup"), new MenuItemPrice(new BigDecimal("5")));

        var entity = MenuItemMapper.toEntity(item);

        assertThat(entity.id).isEqualTo(7L);
        assertThat(entity.name).isEqualTo("Soup");
        assertThat(entity.price).isEqualByComparingTo("5");
        assertThat(entity.status).isEqualTo(MenuItemStatus.ACTIVE);
    }

    @Test
    void shouldMapDomainWithNewIdToEntity() {
        // newId() returns MenuItemId(null), but item.id() is not null — its value is
        var item = new MenuItem(MenuItemId.newId(), new MenuItemName("X"), new MenuItemPrice(new BigDecimal("1")));

        var entity = MenuItemMapper.toEntity(item);

        // item.id() returns a non-null MenuItemId whose value is null;
        // mapper branch `item.id() != null` is true, so e.id = item.id().value() = null
        assertThat(entity.id).isNull();
        assertThat(entity.name).isEqualTo("X");
    }

    @Test
    void mapperCanBeInstantiated() {
        new MenuItemMapper();
    }
}
