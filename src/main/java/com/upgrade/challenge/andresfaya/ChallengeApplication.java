package com.upgrade.challenge.andresfaya;

import com.upgrade.challenge.andresfaya.dto.ReservationDTO;
import com.upgrade.challenge.andresfaya.models.Reservation;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChallengeApplication {

	public static void main(String[] args) {

		SpringApplication.run(ChallengeApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapper.addMappings(new PropertyMap<ReservationDTO, Reservation>() {
			protected void configure() {
				skip().setReservationDate(null);
				skip().setStatus(null);
			}
		});
		modelMapper.addMappings(new PropertyMap<Reservation, ReservationDTO>() {
			protected void configure() {
			}
		});
		return modelMapper;
	}
}

