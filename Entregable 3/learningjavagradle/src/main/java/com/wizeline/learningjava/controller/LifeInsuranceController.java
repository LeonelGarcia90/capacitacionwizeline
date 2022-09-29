package com.wizeline.learningjava.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wizeline.learningjava.model.ErrorDTO;
import com.wizeline.learningjava.model.InsuranceBase;
import com.wizeline.learningjava.service.LifeInsurance10;
import com.wizeline.learningjava.service.LifeInsurance20;

@RequestMapping("/api")
@RestController
public class LifeInsuranceController {

	private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

	@GetMapping(value = "/getLifeInsurance", produces = "application/json")
	public ResponseEntity<InsuranceBase> getInsurance(@RequestParam int cost) {
		LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
		InsuranceBase response = new InsuranceBase();
		if (cost == 10) {
			LifeInsurance10 lifeInsurance10 = new LifeInsurance10();
			response = lifeInsurance10.getInsurance(cost);
		} else if (cost == 20) {
			LifeInsurance20 lifeInsurance20 = new LifeInsurance20();
			response = lifeInsurance20.getInsurance(cost);
		} else {
			response.setCost(0);
			response.setDescription("Parametros incorrectos, favor de validar");
			response.setName(null);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		LOGGER.info("Login - Completed");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
}
