package com.champsoft.restaurantreservationssystem.menu.infrastructure.persistence;


import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.champsoft.restaurantreservationssystem.menu.domain.model.MenuItemStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("testing")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MenuItemRepositoryIntegrationTest {

    @Autowired
    private SpringDataMenuItemRepository repository;

    @Test
    @DisplayName("Should save a menu item successfully")
    void shouldSaveMenuItemSuccessfully() {

        // ------------------- Arrange -------------------
        MenuItemJpaEntity item = new MenuItemJpaEntity();
        item.name = "Cheeseburger";
        item.price = new BigDecimal("12.99");
        item.status = MenuItemStatus.ACTIVE;

        // ------------------- Act -------------------
        MenuItemJpaEntity saved = repository.save(item);

        // ------------------- Assert -------------------
        assertThat(saved).isNotNull();
        assertThat(saved.id).isNotNull(); // Auto-generated ID
        assertThat(saved.name).isEqualTo("Cheeseburger");
        assertThat(saved.price).isEqualByComparingTo("12.99");
        assertThat(saved.status).isEqualTo(MenuItemStatus.ACTIVE);
    }

    @Test
    @DisplayName("Should find a menu item by ID")
    void shouldFindMenuItemById() {

        // ------------------- Arrange -------------------
        MenuItemJpaEntity item = new MenuItemJpaEntity();
        item.name = "Pizza";
        item.price = new BigDecimal("15.50");
        item.status = MenuItemStatus.ACTIVE;

        MenuItemJpaEntity saved = repository.save(item);

        // ------------------- Act -------------------
        Optional<MenuItemJpaEntity> found = repository.findById(saved.id);

        // ------------------- Assert -------------------
        assertThat(found).isPresent();
        assertThat(found.get().id).isEqualTo(saved.id);
        assertThat(found.get().name).isEqualTo("Pizza");
        assertThat(found.get().price).isEqualByComparingTo("15.50");
        assertThat(found.get().status).isEqualTo(MenuItemStatus.ACTIVE);
    }

    @Test
    @DisplayName("Should return empty when menu item ID is not found")
    void shouldReturnEmptyWhenMenuItemIdNotFound() {

        // ------------------- Act -------------------
        Optional<MenuItemJpaEntity> found = repository.findById(999L);

        // ------------------- Assert -------------------
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should find all menu items (flexible)")
    void shouldFindAllMenuItems() {

        // ------------------- Arrange -------------------
        MenuItemJpaEntity m1 = new MenuItemJpaEntity();
        m1.name = "Pasta";
        m1.price = new BigDecimal("10.00");
        m1.status = MenuItemStatus.ACTIVE;

        MenuItemJpaEntity m2 = new MenuItemJpaEntity();
        m2.name = "Salad";
        m2.price = new BigDecimal("7.50");
        m2.status = MenuItemStatus.INACTIVE;

        repository.save(m1);
        repository.save(m2);

        // ------------------- Act -------------------
        List<MenuItemJpaEntity> all = repository.findAll();

        // ------------------- Assert -------------------
        // We do NOT check size — DB may contain more items
        assertThat(all)
                .extracting(i -> i.name)
                .contains("Pasta", "Salad");
    }

    @Test
    @DisplayName("Should delete a menu item successfully")
    void shouldDeleteMenuItemSuccessfully() {

        // ------------------- Arrange -------------------
        MenuItemJpaEntity item = new MenuItemJpaEntity();
        item.name = "Soup";
        item.price = new BigDecimal("5.99");
        item.status = MenuItemStatus.ACTIVE;

        MenuItemJpaEntity saved = repository.save(item);

        // ------------------- Act -------------------
        repository.deleteById(saved.id);

        Optional<MenuItemJpaEntity> found = repository.findById(saved.id);

        // ------------------- Assert -------------------
        assertThat(found).isEmpty();
    }
}

