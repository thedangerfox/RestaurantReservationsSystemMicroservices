package com.champsoft.restaurantreservationssystem.table.infrastructure.persistence;

import com.champsoft.restaurantreservationssystem.table.domain.model.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TableMapperTest {

    @Test
    void shouldMapEntityToDomain() {
        var e = new TableJpaEntity();
        e.id = 1L;
        e.tableNumber = 5;
        e.capacity = 4;
        e.status = TableStatus.AVAILABLE;

        var domain = TableMapper.toDomain(e);

        assertThat(domain.id().value()).isEqualTo(1L);
        assertThat(domain.number().value()).isEqualTo(5);
        assertThat(domain.capacity().value()).isEqualTo(4);
    }

    @Test
    void shouldMapDomainToEntity() {
        var t = new Table(TableId.of(7L), new TableNumber(1), new TableCapacity(2));

        var entity = TableMapper.toEntity(t);

        assertThat(entity.id).isEqualTo(7L);
        assertThat(entity.tableNumber).isEqualTo(1);
        assertThat(entity.capacity).isEqualTo(2);
        assertThat(entity.status).isEqualTo(TableStatus.AVAILABLE);
    }

    @Test
    void shouldMapDomainWithNewIdToEntity() {
        var t = new Table(TableId.newId(), new TableNumber(1), new TableCapacity(2));

        var entity = TableMapper.toEntity(t);

        assertThat(entity.id).isNull();
    }

    @Test
    void mapperCanBeInstantiated() {
        new TableMapper();
    }
}
