package com.wizeline.learningjava.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wizeline.learningjava.model.BankAccountDTO;
import com.wizeline.learningjava.model.ResponseDTO;
import com.wizeline.learningjava.service.BankAccountService;
import com.wizeline.learningjava.service.UserService;
import com.wizeline.learningjava.utils.Utils;

@RequestMapping("/api")
@RestController
public class BankingAccountController {

	private static final String SUCCESS_CODE = "OK000";

	@Autowired
	private BankAccountService bankAccountService;

	@Autowired
	private UserService userService;

	@Autowired
	private Utils utils;

	private static final Logger LOGGER = Logger.getLogger(BankingAccountController.class.getName());
	String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";

	@GetMapping("/getUserAccount")
	public ResponseEntity<?> getUserAccount(@RequestParam String user, @RequestParam String password,
			@RequestParam String date) {
		LOGGER.info(msgProcPeticion);
		Instant inicioDeEjecucion = Instant.now();
		ResponseDTO response = new ResponseDTO();
		HttpHeaders responseHeaders = new HttpHeaders();
		String responseText = "";
		responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
		if (utils.isDateFormatValid(date)) {
			// Valida el password del usuario (password)
			if (utils.isPasswordValid(password)) {
				response = userService.login(user, password);
				if (response.getCode().equals(SUCCESS_CODE)) {
					BankAccountDTO bankAccountDTO = getAccountDetails(user, date);
					Instant finalDeEjecucion = Instant.now();
					LOGGER.info("LearningJava - Cerrando recursos ...");
					String total = new String(
							String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis())
									.concat(" segundos."));
					LOGGER.info("Tiempo de respuesta: ".concat(total));
					return new ResponseEntity<>(bankAccountDTO, responseHeaders, HttpStatus.OK);
				}
			} else {
				Instant finalDeEjecucion = Instant.now();
				LOGGER.info("LearningJava - Cerrando recursos ...");
				String total = new String(
						String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis())
								.concat(" segundos."));
				LOGGER.info("Tiempo de respuesta: ".concat(total));
				responseText = "Password Incorrecto";
				return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
			}
		} else {
			responseText = "Formato de Fecha Incorrecto";
		}
		Instant finalDeEjecucion = Instant.now();
		LOGGER.info("LearningJava - Cerrando recursos ...");
		String total = new String(
				String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
		LOGGER.info("Tiempo de respuesta: ".concat(total));
		return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
	}

	@GetMapping("/getAccounts")
	public ResponseEntity<List<BankAccountDTO>> getAccounts() {
		LOGGER.info(msgProcPeticion);
		Instant inicioDeEjecucion = Instant.now();
		LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
		List<BankAccountDTO> accounts = bankAccountService.getAccounts();

		Instant finalDeEjecucion = Instant.now();

		LOGGER.info("LearningJava - Cerrando recursos ...");
		String total = new String(
				String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
		LOGGER.info("Tiempo de respuesta: ".concat(total));

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
		return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);

	}

	@GetMapping("/getAccountsGroupByType")
	public ResponseEntity<Map<String, List<BankAccountDTO>>> getAccountsGroupByType() {

		LOGGER.info(msgProcPeticion);
		Instant inicioDeEjecucion = Instant.now();

		LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
		List<BankAccountDTO> accounts = bankAccountService.getAccounts();

		// Aqui implementaremos la programación funcional
		Map<String, List<BankAccountDTO>> groupedAccounts;
		Function<BankAccountDTO, String> groupFunction = (account) -> account.getAccountType().toString();
		groupedAccounts = accounts.stream().collect(Collectors.groupingBy(groupFunction));
		Instant finalDeEjecucion = Instant.now();

		LOGGER.info("LearningJava - Cerrando recursos ...");
		String total = new String(
				String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
		LOGGER.info("Tiempo de respuesta: ".concat(total));

		return new ResponseEntity<>(groupedAccounts, HttpStatus.OK);
	}

	private BankAccountDTO getAccountDetails(String user, String lastUsage) {
		return bankAccountService.getAccountDetails(user, lastUsage);
	}

}
