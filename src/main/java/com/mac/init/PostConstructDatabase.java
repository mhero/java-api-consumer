package com.mac.init;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.mac.domain.Currency;
import com.mac.domain.CurrencyUpdate;
import com.mac.exchange.CurrencyRatesStreamService;
import com.mac.repository.CurrencyConversionRateRepository;
import com.mac.repository.CurrencyUpdateRepository;

@Component
public class PostConstructDatabase {

	private static final Logger LOG = LoggerFactory.getLogger(CurrencyRatesStreamService.class);

	@Autowired
	CurrencyRatesStreamService currencyRatesStreamService;

	@Autowired
	CurrencyConversionRateRepository currencyConversionRateRepository;

	@Autowired
	CurrencyUpdateRepository currencyUpdateRepository;

	@Autowired
	private Environment environment;

	@PostConstruct
	public void init() {
		LOG.info("Initialize database loading");

		if (isTestEnv()) {
			return;
		}

		List<Currency> updatetableCurrencyDates = updatetableCurrencyDates();
		if (updatetableCurrencyDates.isEmpty()) {
			LOG.info("No pending updates");
			return;
		}

		LOG.info("Currencies to be updated {}", updatetableCurrencyDates);

		currencyConversionRateRepository.saveAll(currencyRatesStreamService.getAllRates(updatetableCurrencyDates));

		LOG.info("Finished database loading");
	}

	private List<Currency> updatetableCurrencyDates() {
		List<CurrencyUpdate> storedUpdates = currencyUpdateRepository.findAll();
		List<CurrencyUpdate> latestUpdates = currencyRatesStreamService.getLastestUpdate();

		if (storedUpdates.isEmpty()) {
			currencyUpdateRepository.saveAll(latestUpdates);
			return List.of(Currency.values());
		} else {
			storedUpdates.removeAll(latestUpdates);
			currencyUpdateRepository.saveAll(storedUpdates);
			return storedUpdates.stream().map(CurrencyUpdate::getCurrency).collect(Collectors.toList());

		}
	}

	private Boolean isTestEnv() {
		return Arrays.asList(environment.getActiveProfiles()).contains("test");
	}
}