package com.mac.exchange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import com.mac.domain.Currency;
import com.mac.domain.CurrencyConversionRate;
import com.mac.exchange.api.CurrencyStreamParserService;
import com.mac.exchange.api.URLStreamService;

public class CurrencyStreamParserProcessor implements Callable<List<CurrencyConversionRate>> {

	private final Currency currency;

	private final URLStreamService urlStreamConnectorService;

	private final CurrencyStreamParserService currencyStreamReaderService;

	private final List<CurrencyConversionRate> resultRates = Collections.synchronizedList(new ArrayList<>());

	public CurrencyStreamParserProcessor(Currency currency, URLStreamService urlStreamConnectorService,
			CurrencyStreamParserService currencyStreamReaderService) {
		this.currency = currency;
		this.urlStreamConnectorService = urlStreamConnectorService;
		this.currencyStreamReaderService = currencyStreamReaderService;
	}

	@Override
	public List<CurrencyConversionRate> call() {
		synchronized (resultRates) {

			resultRates.addAll(
					currencyStreamReaderService.asList(urlStreamConnectorService.currencyEndpoint(currency), currency));
		}
		return resultRates;
	}

}
