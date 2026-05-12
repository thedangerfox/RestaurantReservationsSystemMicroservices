package com.champsoft.restaurantreservationssystem.table.api.mapper;

import com.champsoft.restaurantreservationssystem.table.domain.model.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TableApiMapperTest {

    @Test
    void mapsToResponse() {
        var t = new Table(TableId.of(1L), new TableNumber(5), new TableCapacity(4));

        var r = TableApiMapper.toResponse(t);

        assertThat(r.id()).isEqualTo(1L);
        assertThat(r.tableNumber()).isEqualTo(5);
        assertThat(r.capacity()).isEqualTo(4);
        assertThat(r.status()).isEqualTo("AVAILABLE");
    }

    @Test
    void mapperCanBeInstantiated() {
        new TableApiMapper();
    }
}
