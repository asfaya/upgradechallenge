package com.upgrade.challenge.andresfaya.services;

import com.upgrade.challenge.andresfaya.models.Reservation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Component
public interface IReservationService {
    Optional<Reservation> findById(Long id);

    Collection<LocalDate> getAvailableDates(LocalDate beginDate, LocalDate endDate);

    Reservation create(Reservation reservation);

    Reservation update(Reservation reservation);

    Reservation cancel(Reservation reservation);
}
