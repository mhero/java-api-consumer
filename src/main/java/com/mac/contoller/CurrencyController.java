package com.mac.contoller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac.domain.dto.CurrencyDTO;
import com.mac.exchange.CurrencyService;

@RestController()
@RequestMapping("/api")
public class CurrencyController {

	@Autowired
	CurrencyService currencyService;

	@GetMapping("/currencies")
	public ResponseEntity<ArrayList<CurrencyDTO>> getCurrencies() {

		return new ResponseEntity<>(currencyService.getRates(), HttpStatus.OK);
	}

}
