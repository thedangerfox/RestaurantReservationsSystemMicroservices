package com.champsoft.restaurantreservationssystem.table.infrastructure.persistence;

import com.champsoft.restaurantreservationssystem.table.domain.model.*;

public class TableMapper {

    public static Table toDomain(TableJpaEntity e) {
        return new Table(
                new TableId(e.id),
                new TableNumber(e.tableNumber),
                new TableCapacity(e.capacity)
        );
    }

    public static TableJpaEntity toEntity(Table t) {
        var e = new TableJpaEntity();
        e.id = t.id() != null ? t.id().value() : null;
        e.tableNumber = t.number().value();
        e.capacity = t.capacity().value();
        e.status = t.status();
        return e;
    }
}
