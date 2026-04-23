package com.champsoft.restaurantreservationssystem.reservation.api;

import com.champsoft.restaurantreservationssystem.reservation.api.dto.*;
import com.champsoft.restaurantreservationssystem.reservation.api.mapper.ReservationApiMapper;
import com.champsoft.restaurantreservationssystem.reservation.application.service.ReservationCrudService;
import com.champsoft.restaurantreservationssystem.reservation.application.service.ReservationOrchestrator;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationOrchestrator orchestrator;
    private final ReservationCrudService crud;
    private final ReservationRepresentationAssembler assembler;

    public ReservationController(
            ReservationOrchestrator orchestrator,
            ReservationCrudService crud,
            ReservationRepresentationAssembler assembler
    ) {
        this.orchestrator = orchestrator;
        this.crud = crud;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<ReservationResponse>> create(
            @RequestBody @Valid CreateReservationRequest req
    ) {
        var reservation = orchestrator.create(
                req.customerId(),
                req.tableId(),
                req.reservationTime(),
                req.partySize(),
                req.preOrderItems()
        );

        var response = ReservationApiMapper.toResponse(reservation);
        return ResponseEntity.ok(assembler.toModel(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ReservationResponse>> get(@PathVariable Long id) {
        var reservation = crud.get(id);
        var response = ReservationApiMapper.toResponse(reservation);
        return ResponseEntity.ok(assembler.toModel(response));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ReservationResponse>>> list() {
        List<ReservationResponse> responses = crud.list().stream()
                .map(ReservationApiMapper::toResponse)
                .toList();

        return ResponseEntity.ok(assembler.toCollectionModel(responses));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ReservationResponse>> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateReservationRequest req
    ) {
        var reservation = crud.update(
                id,
                req.reservationTime(),
                req.partySize(),
                req.preOrderItems()
        );

        var response = ReservationApiMapper.toResponse(reservation);
        return ResponseEntity.ok(assembler.toModel(response));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<EntityModel<ReservationResponse>> cancel(@PathVariable Long id) {
        var reservation = crud.cancel(id);
        var response = ReservationApiMapper.toResponse(reservation);
        return ResponseEntity.ok(assembler.toModel(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        crud.delete(id);
        return ResponseEntity.noContent().build();
    }
}
