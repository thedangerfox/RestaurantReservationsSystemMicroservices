package com.champsoft.restaurantreservationssystem.table.api;

import com.champsoft.restaurantreservationssystem.table.api.dto.*;
import com.champsoft.restaurantreservationssystem.table.api.mapper.TableApiMapper;
import com.champsoft.restaurantreservationssystem.table.application.service.TableCrudService;
import com.champsoft.restaurantreservationssystem.table.application.service.TableEligibilityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tables")
public class TableController {

    private final TableCrudService service;
    private final TableEligibilityService eligibilityService;

    public TableController(TableCrudService service,
                           TableEligibilityService eligibilityService) {
        this.service = service;
        this.eligibilityService = eligibilityService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateTableRequest req) {
        var t = service.create(req.tableNumber(), req.capacity());
        return ResponseEntity.ok(TableApiMapper.toResponse(t));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        var t = service.getById(id);
        return ResponseEntity.ok(TableApiMapper.toResponse(t));
    }

    @GetMapping
    public ResponseEntity<?> list() {
        var list = service.list().stream()
                .map(TableApiMapper::toResponse)
                .toList();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTableRequest req
    ) {
        var t = service.update(id, req.tableNumber(), req.capacity());
        return ResponseEntity.ok(TableApiMapper.toResponse(t));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/eligibility")
    public ResponseEntity<Boolean> isEligible(@PathVariable Long id) {
        return ResponseEntity.ok(eligibilityService.isEligible(id));
    }
}
