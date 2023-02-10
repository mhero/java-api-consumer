package com.mac.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mac.domain.Currency;
import com.mac.domain.CurrencyConversionRate;

public interface CurrencyConversionRateRepository
		extends PagingAndSortingRepository<CurrencyConversionRate, Long>, CrudRepository<CurrencyConversionRate, Long> {
	Page<CurrencyConversionRate> findAll(Pageable pageable);

	Page<CurrencyConversionRate> findAllByDate(LocalDate date, Pageable pageable);

	Page<CurrencyConversionRate> findAllByCurrency(Currency currency, Pageable pageable);

	Page<CurrencyConversionRate> findByDateAndCurrency(LocalDate date, Currency currency, Pageable pageable);

	CurrencyConversionRate findFirstByDateAndCurrency(LocalDate date, Currency currency);

}
