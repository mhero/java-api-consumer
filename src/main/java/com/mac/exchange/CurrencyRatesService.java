package com.mac.exchange;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mac.domain.Currency;
import com.mac.domain.CurrencyConversionRate;
import com.mac.domain.dto.ExchangeDTO;
import com.mac.repository.CurrencyConversionRateRepository;

@Service
public class CurrencyRatesService {
	@Autowired
	CurrencyConversionRateRepository currencyConversionRateRepository;

	public Page<CurrencyConversionRate> findBy(Currency currency, LocalDate date, Integer page, Integer size) {

		PageRequest pagination = PageRequest.of(page, size);
		if (date == null && currency == null) {
			return currencyConversionRateRepository.findAll(pagination);
		} else if (currency == null) {
			return currencyConversionRateRepository.findAllByDate(date, pagination);
		} else if (date == null) {
			return currencyConversionRateRepository.findAllByCurrency(currency, pagination);
		} else {
			return currencyConversionRateRepository.findByDateAndCurrency(date, currency, pagination);
		}
	}

	public ExchangeDTO getExchange(Currency sourceCurrency, LocalDate date, Double sourceAmount) {

		CurrencyConversionRate currencyRate = currencyConversionRateRepository.findFirstByDateAndCurrency(date,
				sourceCurrency);

		Double exchangeAmount = (currencyRate == null || currencyRate.getConversionRate() == null) ? null
				: sourceAmount / currencyRate.getConversionRate();

		ExchangeDTO exchange = new ExchangeDTO(sourceCurrency, sourceAmount, date, exchangeAmount, Currency.EUR);

		return exchange;
	}

}
