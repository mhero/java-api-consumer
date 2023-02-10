package com.mac.contoller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mac.domain.Currency;
import com.mac.domain.dto.ExchangeDTO;
import com.mac.exchange.CurrencyRatesService;

@RestController()
@RequestMapping("/api")
public class ExchangeController {

	@Autowired
	CurrencyRatesService currencyRatesService;

	@GetMapping("/exchange")
	public ResponseEntity<ExchangeDTO> getExchange(@RequestParam() Currency currency,
			@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @RequestParam() Double amount) {

		return new ResponseEntity<>(currencyRatesService.getExchange(currency, date, amount), HttpStatus.OK);
	}

}
