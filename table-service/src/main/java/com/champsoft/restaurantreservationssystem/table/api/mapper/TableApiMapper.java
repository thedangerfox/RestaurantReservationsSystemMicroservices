package com.champsoft.restaurantreservationssystem.table.api.mapper;

import com.champsoft.restaurantreservationssystem.table.api.dto.TableResponse;
import com.champsoft.restaurantreservationssystem.table.domain.model.Table;

public class TableApiMapper {

    public static TableResponse toResponse(Table t) {
        return new TableResponse(
                t.id().value(),
                t.number().value(),
                t.capacity().value(),
                t.status().name()
        );
    }
}
