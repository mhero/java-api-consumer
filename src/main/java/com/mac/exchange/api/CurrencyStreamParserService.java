package com.mac.exchange.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mac.domain.Currency;
import com.mac.domain.CurrencyConversionRate;
import com.mac.util.DateParser;

@Service
public class CurrencyStreamParserService {

	private static final Logger LOG = LoggerFactory.getLogger(CurrencyStreamParserService.class);
	private static final String SEPARATOR = ",";

	public List<CurrencyConversionRate> asList(InputStream stream, Currency currency) {

		if (stream == null || currency == null) {
			return List.of();
		}
		try (InputStreamReader streamReader = new InputStreamReader(stream);
				Stream<String> lines = new BufferedReader(streamReader).lines()) {
			return filterStream(lines, currency);
		} catch (IOException e) {
			LOG.error("Error while reading stream {}", e.getMessage());
			return List.of();
		}

	}

	public LocalDateTime filterLatestUpdateStream(InputStream stream) {

		if (stream == null) {
			return null;
		}
		try (InputStreamReader streamReader = new InputStreamReader(stream);
				Stream<String> lines = new BufferedReader(streamReader).lines()) {
			String latestDateStr = lines.takeWhile(line -> !DateParser.isValid(line.split(SEPARATOR)[0]))
					.reduce((first, second) -> second.split(SEPARATOR)[1]).orElse(null);

			return DateParser.parseToDateTime(latestDateStr);
		} catch (IOException e) {
			LOG.error("Error while reading stream {}", e.getMessage());
			return null;
		}

	}

	private List<CurrencyConversionRate> filterStream(Stream<String> streamLines, Currency currency) {

		return streamLines.map(line -> line.split(SEPARATOR)).filter(line -> DateParser.isValid(line[0])).map(line -> {
			try {
				return new CurrencyConversionRate(currency, line);
			} catch (IllegalArgumentException e) {
				LOG.error("Error while parsing stream {} {} {} {}", currency, line[1], line[0], e.getMessage());
			}
			return null;
		}).filter(Objects::nonNull).collect(Collectors.toList());

	}

}
