package com.upgrade.challenge.andresfaya.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReservationDTO {

    @NotNull
    private Long id;

    @NotNull
    private LocalDateTime beginDate;

    @NotNull
    private LocalDateTime endDate;

    @NotNull
    private String fullName;

    @NotNull
    @Email(message = "Please, provide a valid email")
    private String email;
}
