package com.mac.contoller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mac.domain.Currency;
import com.mac.domain.CurrencyConversionRate;
import com.mac.exchange.CurrencyRatesService;

@RestController()
@RequestMapping("/api")
public class RateController {

	@Autowired
	CurrencyRatesService currencyRatesService;

	@GetMapping("/rates")
	public ResponseEntity<Page<CurrencyConversionRate>> getAllRatesCurrencies(
			@RequestParam(required = false) Currency currency,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "50") Integer pageSize) {

		return new ResponseEntity<>(currencyRatesService.findBy(currency, date, page, pageSize), HttpStatus.OK);
	}

}
