package com.champsoft.restaurantreservationssystem.table.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TableTest {

    @Test
    void newTableIsAvailable() {
        var t = new Table(TableId.of(1L), new TableNumber(5), new TableCapacity(4));

        assertThat(t.id().value()).isEqualTo(1L);
        assertThat(t.number().value()).isEqualTo(5);
        assertThat(t.capacity().value()).isEqualTo(4);
        assertThat(t.status()).isEqualTo(TableStatus.AVAILABLE);
    }

    @Test
    void updateChangesNumberAndCapacity() {
        var t = new Table(TableId.of(1L), new TableNumber(1), new TableCapacity(2));
        t.update(new TableNumber(99), new TableCapacity(8));

        assertThat(t.number().value()).isEqualTo(99);
        assertThat(t.capacity().value()).isEqualTo(8);
    }

    @Test
    void changeStatusUpdatesStatus() {
        var t = new Table(TableId.of(1L), new TableNumber(1), new TableCapacity(2));
        t.changeStatus(TableStatus.RESERVED);
        assertThat(t.status()).isEqualTo(TableStatus.RESERVED);
    }

    @Test
    void tableIdAccepts() {
        assertThat(TableId.of(1L).value()).isEqualTo(1L);
        assertThat(TableId.newId().value()).isNull();
        assertThatThrownBy(() -> TableId.of(0L)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> TableId.of(-1L)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void tableNumberRejects() {
        assertThatThrownBy(() -> new TableNumber(null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new TableNumber(0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new TableNumber(-1)).isInstanceOf(IllegalArgumentException.class);
        assertThat(new TableNumber(1).value()).isEqualTo(1);
    }

    @Test
    void tableCapacityRejects() {
        assertThatThrownBy(() -> new TableCapacity(null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new TableCapacity(0)).isInstanceOf(IllegalArgumentException.class);
        assertThat(new TableCapacity(2).value()).isEqualTo(2);
    }

    @Test
    void tableStatusValues() {
        assertThat(TableStatus.values()).hasSize(3);
        assertThat(TableStatus.valueOf("AVAILABLE")).isEqualTo(TableStatus.AVAILABLE);
    }
}
