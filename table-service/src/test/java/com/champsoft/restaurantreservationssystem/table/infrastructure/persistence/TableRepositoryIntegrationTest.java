package com.champsoft.restaurantreservationssystem.table.infrastructure.persistence;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import com.champsoft.restaurantreservationssystem.table.domain.model.TableStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("testing")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TableRepositoryIntegrationTest {

    @Autowired
    private SpringDataTableRepository repository;

    @Test
    @DisplayName("Should save a table successfully")
    void shouldSaveTableSuccessfully() {

        // ------------------- Arrange -------------------
        TableJpaEntity table = new TableJpaEntity();
        table.tableNumber = 100;
        table.capacity = 4;
        table.status = TableStatus.AVAILABLE;

        // ------------------- Act -------------------
        TableJpaEntity saved = repository.save(table);

        // ------------------- Assert -------------------
        assertThat(saved).isNotNull();
        assertThat(saved.id).isNotNull(); // Auto-generated ID
        assertThat(saved.tableNumber).isEqualTo(100);
        assertThat(saved.capacity).isEqualTo(4);
        assertThat(saved.status).isEqualTo(TableStatus.AVAILABLE);
    }

    @Test
    @DisplayName("Should find a table by ID")
    void shouldFindTableById() {

        // ------------------- Arrange -------------------
        TableJpaEntity table = new TableJpaEntity();
        table.tableNumber = 300; // unique number
        table.capacity = 2;
        table.status = TableStatus.RESERVED;

        TableJpaEntity saved = repository.save(table);

        // ------------------- Act -------------------
        Optional<TableJpaEntity> found = repository.findById(saved.id);

        // ------------------- Assert -------------------
        assertThat(found).isPresent();
        assertThat(found.get().id).isEqualTo(saved.id);
        assertThat(found.get().tableNumber).isEqualTo(300);
        assertThat(found.get().capacity).isEqualTo(2);
        assertThat(found.get().status).isEqualTo(TableStatus.RESERVED);
    }


    @Test
    @DisplayName("Should return empty when table ID is not found")
    void shouldReturnEmptyWhenTableIdNotFound() {

        // ------------------- Act -------------------
        Optional<TableJpaEntity> found = repository.findById(999L);

        // ------------------- Assert -------------------
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should find all tables (flexible)")
    void shouldFindAllTables() {

        // ------------------- Arrange -------------------
        TableJpaEntity t1 = new TableJpaEntity();
        t1.tableNumber = 100; // unique
        t1.capacity = 4;
        t1.status = TableStatus.AVAILABLE;

        TableJpaEntity t2 = new TableJpaEntity();
        t2.tableNumber = 101; // unique
        t2.capacity = 6;
        t2.status = TableStatus.OUT_OF_SERVICE;

        repository.save(t1);
        repository.save(t2);

        // ------------------- Act -------------------
        List<TableJpaEntity> all = repository.findAll();

        // ------------------- Assert -------------------
        assertThat(all)
                .extracting(t -> t.tableNumber)
                .contains(100, 101);
    }


    @Test
    @DisplayName("Should delete a table successfully")
    void shouldDeleteTableSuccessfully() {

        // ------------------- Arrange -------------------
        TableJpaEntity table = new TableJpaEntity();
        table.tableNumber = 20;
        table.capacity = 8;
        table.status = TableStatus.AVAILABLE;

        TableJpaEntity saved = repository.save(table);

        // ------------------- Act -------------------
        repository.deleteById(saved.id);

        Optional<TableJpaEntity> found = repository.findById(saved.id);

        // ------------------- Assert -------------------
        assertThat(found).isEmpty();
    }
}

