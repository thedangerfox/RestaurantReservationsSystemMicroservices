package com.champsoft.restaurantreservationssystem.table.application.service;

import com.champsoft.restaurantreservationssystem.table.application.exception.TableNotFoundException;
import com.champsoft.restaurantreservationssystem.table.application.port.out.TableRepositoryPort;
import com.champsoft.restaurantreservationssystem.table.domain.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TableCrudService {

    private final TableRepositoryPort repo;

    public TableCrudService(TableRepositoryPort repo) {
        this.repo = repo;
    }

    @Transactional
    public Table create(Integer tableNumber, Integer capacity) {
        var table = new Table(
                TableId.newId(),
                new TableNumber(tableNumber),
                new TableCapacity(capacity)
        );
        return repo.save(table);
    }

    @Transactional(readOnly = true)
    public Table getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new TableNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Table> list() {
        return repo.findAll();
    }

    @Transactional
    public Table update(Long id, Integer tableNumber, Integer capacity) {
        var table = getById(id);
        table.update(
                new TableNumber(tableNumber),
                new TableCapacity(capacity)
        );
        return repo.save(table);
    }

    @Transactional
    public void delete(Long id) {
        getById(id); // ensures exception if missing
        repo.deleteById(id);
    }
}
