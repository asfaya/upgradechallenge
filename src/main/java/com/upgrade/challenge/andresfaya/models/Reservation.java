package com.upgrade.challenge.andresfaya.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reservations")
@Data
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ReservationDate")
    private LocalDateTime reservationDate;

    @Column(name = "BeginDate")
    private LocalDateTime beginDate;

    @Column(name = "EndDate")
    private LocalDateTime endDate;

    @Column(name = "FullName")
    private String fullName;

    @Column(name = "Email")
    @Email(message = "The email is not valid")
    private String email;

    @Enumerated
    @Column(name = "status", columnDefinition = "smallint")
    private ReservationStatus status;
}
