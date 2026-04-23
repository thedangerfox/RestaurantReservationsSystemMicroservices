package com.champsoft.restaurantreservationssystem.reservation.application.service;

import com.champsoft.restaurantreservationssystem.reservation.api.dto.PreOrderItemRequest;
import com.champsoft.restaurantreservationssystem.reservation.application.port.out.ReservationRepositoryPort;
import com.champsoft.restaurantreservationssystem.reservation.domain.exception.ReservationNotFoundException;
import com.champsoft.restaurantreservationssystem.reservation.domain.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationCrudService {

    private final ReservationRepositoryPort repo;

    public ReservationCrudService(ReservationRepositoryPort repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public Reservation get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Reservation> list() {
        return repo.findAll();
    }

    @Transactional
    public Reservation update(
            Long id,
            LocalDateTime reservationTime,
            Integer partySize,
            List<PreOrderItemRequest> preOrderItems
    ) {
        var reservation = get(id);

        // Convert DTO preorder items → domain preorder items
        var items = preOrderItems == null ? List.<PreOrderItem>of() :
                preOrderItems.stream()
                        .map(i -> new PreOrderItem(
                                i.itemId(),
                                new PreOrderQuantity(i.quantity())
                        ))
                        .toList();

        reservation.update(
                new ReservationTime(reservationTime),
                new PartySize(partySize),
                items
        );

        return repo.save(reservation);
    }

    @Transactional
    public Reservation cancel(Long id) {
        var reservation = get(id);
        reservation.cancel();
        return repo.save(reservation);
    }

    @Transactional
    public void delete(Long id) {
        get(id); // ensures it exists
        repo.deleteById(id);
    }
}
