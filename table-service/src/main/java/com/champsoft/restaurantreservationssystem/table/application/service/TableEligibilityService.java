package com.champsoft.restaurantreservationssystem.table.application.service;

import com.champsoft.restaurantreservationssystem.table.application.exception.TableNotFoundException;
import com.champsoft.restaurantreservationssystem.table.application.port.out.TableRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TableEligibilityService {

    private final TableRepositoryPort repo;

    public TableEligibilityService(TableRepositoryPort repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public boolean isEligible(Long tableId) {
        return repo.findById(tableId)
                .map(t -> true) // tables have no ACTIVE/INACTIVE status
                .orElseThrow(() -> new TableNotFoundException(tableId));
    }
}
