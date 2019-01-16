package com.upgrade.challenge.andresfaya.controllers;

import com.upgrade.challenge.andresfaya.dto.ReservationDTO;
import com.upgrade.challenge.andresfaya.exceptions.InvalidInformationException;
import com.upgrade.challenge.andresfaya.exceptions.ReservationNotFoundException;
import com.upgrade.challenge.andresfaya.models.Reservation;
import com.upgrade.challenge.andresfaya.services.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.upgrade.challenge.andresfaya.validators.ReservationDtoValidator;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    ReservationService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ReservationDTO getReservation(@PathVariable long id) {
        Reservation reservation = service.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        return modelMapper.map(reservation, ReservationDTO.class);
    }

    @GetMapping("/availability/{beginDate}/{endDate}")
    public Collection<LocalDate> getAvailableDates(@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate beginDate, @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate endDate) {

        return service.getAvailableDates(beginDate, endDate);
    }

    @GetMapping("/availability/{beginDate}")
    public Collection<LocalDate> getAvailableDates(@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate beginDate) {
        LocalDate endDate = beginDate.plusMonths(1);
        return service.getAvailableDates(beginDate, endDate);
    }

    @PostMapping("/create")
    public ReservationDTO create(@Valid @RequestBody ReservationDTO reservationDto, BindingResult result) {
        ReservationDtoValidator reservationDtoValidator = new ReservationDtoValidator();
        reservationDtoValidator.validate(reservationDto, result);

        if (!result.hasErrors()){
            Reservation reservation = modelMapper.map(reservationDto, Reservation.class);

            reservation = service.create(reservation);

            return modelMapper.map(reservation, ReservationDTO.class);
        } else {
            String errors = result.getAllErrors()
                    .stream()
                    .map(e -> e.getCode() + "\n")
                    .collect(Collectors.joining());

            throw new InvalidInformationException(errors);
        }
    }

    @PutMapping("/update/{id}")
    public ReservationDTO update(@PathVariable long id, @Valid @RequestBody ReservationDTO reservationDto, BindingResult result) {
        ReservationDtoValidator reservationDtoValidator = new ReservationDtoValidator();
        reservationDtoValidator.validate(reservationDto, result);
        if (!result.hasErrors()) {
            if (id != reservationDto.getId())
                throw new InvalidInformationException("Parameters mismatch");

            Reservation reservation = service.findById(id)
                    .orElseThrow(() -> new ReservationNotFoundException(id));

            reservation.setBeginDate(reservationDto.getBeginDate());
            reservation.setEndDate(reservationDto.getEndDate());
            reservation.setFullName(reservationDto.getFullName());
            reservation.setEmail(reservation.getEmail());

            reservation = service.update(reservation);
            return modelMapper.map(reservation, ReservationDTO.class);
        } else {
            String errors = result.getAllErrors()
                    .stream()
                    .map(e -> e.getCode() + "\n")
                    .collect(Collectors.joining());

            throw new InvalidInformationException(errors);
        }
    }

    @PutMapping("/cancel/{id}")
    public ReservationDTO cancel(@PathVariable long id) {

        Reservation reservation = service.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        reservation = service.cancel(reservation);
        return modelMapper.map(reservation, ReservationDTO.class);
    }
}
