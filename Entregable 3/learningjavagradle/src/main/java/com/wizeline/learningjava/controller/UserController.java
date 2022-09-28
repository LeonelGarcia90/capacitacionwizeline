package com.wizeline.learningjava.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wizeline.learningjava.model.ResponseDTO;
import com.wizeline.learningjava.model.UserDTO;
import com.wizeline.learningjava.service.UserService;

@RequestMapping("/api")
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
	String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";

	@GetMapping(value = "/login", produces = "application/json")
	public ResponseEntity<ResponseDTO> loginUser(@RequestParam String user, @RequestParam String password) {
		LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
		ResponseDTO response = new ResponseDTO();
		response = userService.login(user, password);
		LOGGER.info("Login - Completed");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/createUser", produces = "application/json")
	public ResponseEntity<ResponseDTO> createUserAccount(@RequestBody UserDTO request) {
		LOGGER.info("LearningJava - Procesando peticion HTTP de tipo POST - Starting... ");
		ResponseDTO response = new ResponseDTO();
		response = userService.createUser(request.getUser(), request.getPassword());
		LOGGER.info("Create user - Completed");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}