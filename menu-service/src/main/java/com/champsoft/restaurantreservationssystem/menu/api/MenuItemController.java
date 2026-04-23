package com.champsoft.restaurantreservationssystem.menu.api;

import com.champsoft.restaurantreservationssystem.menu.api.dto.*;
import com.champsoft.restaurantreservationssystem.menu.api.mapper.MenuItemApiMapper;
import com.champsoft.restaurantreservationssystem.menu.application.service.MenuItemCrudService;
import com.champsoft.restaurantreservationssystem.menu.application.service.MenuItemEligibilityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    private final MenuItemCrudService service;
    private final MenuItemEligibilityService eligibilityService;

    public MenuItemController(MenuItemCrudService service,
                              MenuItemEligibilityService eligibilityService) {
        this.service = service;
        this.eligibilityService = eligibilityService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateMenuItemRequest req) {
        var item = service.create(req.name(), req.price());
        return ResponseEntity.ok(MenuItemApiMapper.toResponse(item));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        var item = service.getById(id);
        return ResponseEntity.ok(MenuItemApiMapper.toResponse(item));
    }

    @GetMapping
    public ResponseEntity<?> list() {
        var list = service.list().stream()
                .map(MenuItemApiMapper::toResponse)
                .toList();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateMenuItemRequest req
    ) {
        var item = service.update(id, req.name(), req.price());
        return ResponseEntity.ok(MenuItemApiMapper.toResponse(item));
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
