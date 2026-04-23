package com.champsoft.restaurantreservationssystem.customer.api;

import com.champsoft.restaurantreservationssystem.customer.api.dto.*;
import com.champsoft.restaurantreservationssystem.customer.api.mapper.CustomerApiMapper;
import com.champsoft.restaurantreservationssystem.customer.application.service.CustomerCrudService;
import com.champsoft.restaurantreservationssystem.customer.application.service.CustomerEligibilityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerCrudService service;
    private final CustomerEligibilityService eligibilityService;

    public CustomerController(CustomerCrudService service,
                              CustomerEligibilityService eligibilityService) {
        this.service = service;
        this.eligibilityService = eligibilityService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateCustomerRequest req) {
        var c = service.create(req.fullName(), req.phone());
        return ResponseEntity.ok(CustomerApiMapper.toResponse(c));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        var c = service.getById(id);
        return ResponseEntity.ok(CustomerApiMapper.toResponse(c));
    }

    @GetMapping
    public ResponseEntity<?> list() {
        var list = service.list().stream()
                .map(CustomerApiMapper::toResponse)
                .toList();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCustomerRequest req
    ) {
        var c = service.update(id, req.fullName(), req.phone());
        return ResponseEntity.ok(CustomerApiMapper.toResponse(c));
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
