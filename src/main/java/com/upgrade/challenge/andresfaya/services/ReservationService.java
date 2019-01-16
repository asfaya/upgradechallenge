package com.upgrade.challenge.andresfaya.services;

import com.upgrade.challenge.andresfaya.exceptions.ReservationNotCreatedException;
import com.upgrade.challenge.andresfaya.exceptions.ReservationNotSavedException;
import com.upgrade.challenge.andresfaya.models.Reservation;
import com.upgrade.challenge.andresfaya.models.ReservationStatus;
import com.upgrade.challenge.andresfaya.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReservationService implements IReservationService{

    @Autowired
    ReservationRepository repository;

    public Optional<Reservation> findById(Long id) {

        return repository.findById(id);
    }

    public Collection<LocalDate> getAvailableDates(LocalDate beginDate, LocalDate endDate) {

        // Get all dates in the range
        ArrayList<LocalDate> datesInRange = new ArrayList<>();

        LocalDate tmpDate = beginDate;
        while(tmpDate.isBefore(endDate) || tmpDate.equals(endDate)) {
            datesInRange.add(tmpDate);
            tmpDate = tmpDate.plusDays(1);
        }

        // Get all the reservations in the range
        // Overlapping range -->  (StartA <= EndB) and (EndA >= StartB)
        List<Reservation> reservationsInRange = repository.findByEndDateAfterAndBeginDateBeforeAndStatus(beginDate.atStartOfDay(), endDate.atStartOfDay(), ReservationStatus.CONFIRMED);

        // Check dates in range that are not used by any reservation
        ArrayList<LocalDate> availableDates = new ArrayList<>();
        datesInRange.forEach(date -> {
            List<Reservation> reservationForDate = reservationsInRange.stream()
                                                        .filter(res -> {
                                                                    LocalDateTime cmpDate = date.atTime(12, 0, 0);
                                                                    return ((cmpDate.isAfter(res.getBeginDate()) || cmpDate.isEqual(res.getBeginDate())) && (cmpDate.isBefore(res.getEndDate()) || cmpDate.isEqual(res.getEndDate())));
                                                                }
                                                        )
                                                        .collect(Collectors.toList());
            if (reservationForDate.isEmpty()) {
                availableDates.add(date);
            }
        });

        return availableDates;
    }

    public Reservation create(Reservation reservation) throws ReservationNotCreatedException {
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.CONFIRMED);

        try {
            reservation = repository.save(reservation);
        }
        catch (DataAccessException ex) {
            throw new ReservationNotCreatedException(ex.getRootCause().getMessage());
        }

        return reservation;
    }

    public Reservation update(Reservation reservation) {
        try {
            reservation = repository.save(reservation);
        }
        catch (DataAccessException ex) {
            throw new ReservationNotSavedException(reservation.getId(), ex.getRootCause().getMessage());
        }

        return reservation;
    }

    public Reservation cancel(Reservation reservation) {
        reservation.setStatus(ReservationStatus.CANCELED);

        try {
            reservation = repository.save(reservation);
        }
        catch (DataAccessException ex) {
            throw new ReservationNotSavedException(reservation.getId(), ex.getRootCause().getMessage());
        }

        return reservation;
    }
}
