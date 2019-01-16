package com.upgrade.challenge.andresfaya.validators;

import com.upgrade.challenge.andresfaya.dto.ReservationDTO;
import org.springframework.validation.Errors;

import org.springframework.validation.Validator;
import java.time.LocalDate;

public class ReservationDtoValidator implements Validator {

    /**
     * This Validator validates *just* ReservationDTO instances
     */
    @Override
    public boolean supports(Class clazz) {
        return ReservationDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors e) {

        ReservationDTO dto = (ReservationDTO) target;
        LocalDate begin = dto.getBeginDate().toLocalDate();
        LocalDate end = dto.getEndDate().toLocalDate();

        // Reservation should start at least tomorrow
        if (begin.compareTo(LocalDate.now().plusDays(1)) < 0)
            e.rejectValue("beginDate", "You can't make a reservation for today");

        // End date should be greater than begin date
        if (begin.compareTo(end) > 0)
            e.rejectValue("beginDate", "End date must be greater than begin date");

        // Reservation can't be created more than one month in advance
        // End date should be greater than begin date
        if (begin.compareTo(LocalDate.now().plusMonths(1)) > 0)
            e.rejectValue("beginDate", "Reservation can't be created with more than one month in advance");

        // Reservation should not last more than 3 days
        if (end.compareTo(begin) >= 3)
            e.rejectValue("beginDate", "You can't reserve for more than 3 days");
    }
}
