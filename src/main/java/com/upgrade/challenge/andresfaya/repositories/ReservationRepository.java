package com.upgrade.challenge.andresfaya.repositories;

import com.upgrade.challenge.andresfaya.models.Reservation;
import com.upgrade.challenge.andresfaya.models.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByEndDateAfterAndBeginDateBeforeAndStatus(LocalDateTime beginDate, LocalDateTime endDate, ReservationStatus status);
}
