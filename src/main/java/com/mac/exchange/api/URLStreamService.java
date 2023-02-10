package com.mac.exchange.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mac.domain.Currency;

@Service
public class URLStreamService {

	@Value("${api.currency.endpoint}")
	private String apiCurrencyEndpoint;

	private static final Logger LOG = LoggerFactory.getLogger(URLStreamService.class);

	public InputStream currencyEndpoint(Currency currency) {

		try {
			return new URL(String.format(apiCurrencyEndpoint, currency.name())).openStream();
		} catch (IOException e) {
			LOG.error("Error while opening stream for currency {} {}", currency, e.getMessage());
		}
		return null;

	}
}
