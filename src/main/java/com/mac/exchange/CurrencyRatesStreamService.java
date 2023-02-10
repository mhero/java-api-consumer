package com.mac.exchange;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mac.domain.Currency;
import com.mac.domain.CurrencyConversionRate;
import com.mac.domain.CurrencyUpdate;
import com.mac.exchange.api.CurrencyStreamParserService;
import com.mac.exchange.api.URLStreamService;

@Service
public class CurrencyRatesStreamService {

	private static final Logger LOG = LoggerFactory.getLogger(CurrencyRatesStreamService.class);

	@Autowired
	URLStreamService urlStreamConnectorService;

	@Autowired
	CurrencyStreamParserService currencyStreamReaderService;

	public List<CurrencyConversionRate> getRatesFor(Currency currency) {

		return currencyStreamReaderService.asList(urlStreamConnectorService.currencyEndpoint(currency), currency);
	}

	public List<CurrencyUpdate> getLastestUpdate() {
		List<CurrencyUpdate> latestUpdatePerCurrency = new ArrayList<>();
		for (Currency currency : Currency.values()) {
			latestUpdatePerCurrency.add(latestUpdatePer(currency));

		}
		return latestUpdatePerCurrency;
	}

	public List<CurrencyConversionRate> getAllRates(List<Currency> availableCurrencies) {

		if (availableCurrencies == null || availableCurrencies.isEmpty()) {
			return List.of();
		}
		
		List<CurrencyConversionRate> rates = new ArrayList<>();

		for (Future<List<CurrencyConversionRate>> currencyConversionRate : currencyConversionRateAsync(
				availableCurrencies)) {
			try {
				rates.addAll(currencyConversionRate.get());
			} catch (InterruptedException | ExecutionException e) {
				LOG.error("Error while processing currency futures {}", e.getMessage());
			}

		}
		return rates;

	}

	private CurrencyUpdate latestUpdatePer(Currency currency) {
		return new CurrencyUpdate(currency, currencyStreamReaderService
				.filterLatestUpdateStream(urlStreamConnectorService.currencyEndpoint(currency)));
	}

	private List<Future<List<CurrencyConversionRate>>> currencyConversionRateAsync(List<Currency> availableCurrencies) {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		List<Future<List<CurrencyConversionRate>>> currencyConversionRate = new ArrayList<>();

		for (Currency currency : availableCurrencies) {
			currencyConversionRate.add(executor.submit(currencyStreamParserProcessor(currency)));
		}
		executor.shutdown();

		try {
			executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			LOG.error("Error while terminating currency threads {}", e.getMessage());
		}
		return currencyConversionRate;

	}

	private CurrencyStreamParserProcessor currencyStreamParserProcessor(Currency currency) {
		return new CurrencyStreamParserProcessor(currency, urlStreamConnectorService, currencyStreamReaderService);
	}
}
