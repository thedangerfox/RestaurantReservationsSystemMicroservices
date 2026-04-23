package com.champsoft.restaurantreservationssystem.reservation.infrastructure.persistence;

import com.champsoft.restaurantreservationssystem.reservation.application.port.out.ReservationRepositoryPort;
import com.champsoft.restaurantreservationssystem.reservation.domain.model.Reservation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaReservationRepositoryAdapter implements ReservationRepositoryPort {

    private final SpringDataReservationRepository jpa;

    public JpaReservationRepositoryAdapter(SpringDataReservationRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Reservation save(Reservation reservation) {
        var entity = ReservationMapper.toEntity(reservation);
        var saved = jpa.save(entity);
        return ReservationMapper.toDomain(saved);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return jpa.findById(id).map(ReservationMapper::toDomain);
    }

    @Override
    public List<Reservation> findAll() {
        return jpa.findAll().stream()
                .map(ReservationMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }
}
