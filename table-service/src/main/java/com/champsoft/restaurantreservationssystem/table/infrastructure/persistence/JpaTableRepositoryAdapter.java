package com.champsoft.restaurantreservationssystem.table.infrastructure.persistence;

import com.champsoft.restaurantreservationssystem.table.application.port.out.TableRepositoryPort;
import com.champsoft.restaurantreservationssystem.table.domain.model.Table;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaTableRepositoryAdapter implements TableRepositoryPort {

    private final SpringDataTableRepository jpa;

    public JpaTableRepositoryAdapter(SpringDataTableRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Table save(Table table) {
        var entity = TableMapper.toEntity(table);
        var saved = jpa.save(entity);
        return TableMapper.toDomain(saved);
    }

    @Override
    public Optional<Table> findById(Long id) {
        return jpa.findById(id).map(TableMapper::toDomain);
    }

    @Override
    public List<Table> findAll() {
        return jpa.findAll().stream()
                .map(TableMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }
}
