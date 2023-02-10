package com.mac.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.mac.util.DateParser;

@SpringBootTest
@ActiveProfiles("test")
public class CurrencyConversionRateTests {

	@Test
	public void should_ReturnAnOject_When_FilterAndRawRateStringWithoutRateProvided() {
		String[] rawRate = { "1999-01-03", ".", "No value available" };

		assertEquals(new CurrencyConversionRate(Currency.AUD, null, DateParser.parseToDate("1999-01-03")),
				new CurrencyConversionRate(Currency.AUD, rawRate));

	}

	@Test
	public void should_ReturnAnOject_When_FilterAndRawRateStringWithRateProvided() {
		String[] rawRate = { "1999-01-03", "5.6", "" };

		assertEquals(new CurrencyConversionRate(Currency.AUD, 5.6, DateParser.parseToDate("1999-01-03")),
				new CurrencyConversionRate(Currency.AUD, rawRate));

	}

	@Test
	public void should_ThrowExcpetiont_When_InvalidArgumentProvided() {
		String[] rawRateInvalidRate = { "1999-01-03", "X", "No value available" };

		assertThrows(IllegalArgumentException.class,
				() -> new CurrencyConversionRate(Currency.AUD, rawRateInvalidRate));

		String[] rawRateInvalidDate = { "Y", "5.6", "No value available" };

		assertThrows(IllegalArgumentException.class,
				() -> new CurrencyConversionRate(Currency.AUD, rawRateInvalidDate));

		String[] rawRate = { "1999-01-03", "5.6", "" };
		assertThrows(IllegalArgumentException.class, () -> new CurrencyConversionRate(null, rawRate));

	}
}
