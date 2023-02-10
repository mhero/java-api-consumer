package com.mac.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.mac.domain.Currency;
import com.mac.domain.CurrencyConversionRate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class DateParserTests {

	@Test
	void should_ReturnAValidDate_When_ValidStringDateSent() {
		String date = "2022-11-01";
		assertEquals(date, DateParser.parseToDate(date).toString());
	}

	@Test
	void should_ReturnNull_When_NullValueSent() {
		assertNull(DateParser.parseToDate(null));
	}

	@Test
	void should_ThrowException_When_InvalidValueSent() {
		String date = "8";
		assertThrows(DateTimeParseException.class, () -> DateParser.parseToDate(date));

	}

	@Test
	void should_ReturnTrue_When_ValidStringDateSent() {
		String date = "2022-11-01";
		assertEquals(true, DateParser.isValid(date));

	}

	@Test
	void should_ReturnFalse_When_InvalidStringDateSent() {
		String date = "2022-13-01";
		assertEquals(false, DateParser.isValid(date));
		date = "word";
		assertEquals(false, DateParser.isValid(date));
		date = "2022-12-32";
		assertEquals(false, DateParser.isValid(date));
	}

	@Test
	void should_ReturnFalse_When_CurrencyHasNoDate() {

		LocalDate today = LocalDate.now();
		CurrencyConversionRate currency = new CurrencyConversionRate(Currency.AUD, 0.0, null);
		assertEquals(false, DateParser.isSameDate(currency, today));

	}

	@Test
	void should_ReturnTrue_When_CurrencyHasNoDateAndDateIsNull() {
		CurrencyConversionRate currency = new CurrencyConversionRate(Currency.AUD, 0.0, null);
		assertEquals(true, DateParser.isSameDate(currency, null));
	}

	@Test
	void should_ReturnTrue_When_CurrencyHasDateAndDateIsNull() {
		CurrencyConversionRate currency = new CurrencyConversionRate(Currency.AUD, 0.0, LocalDate.now());

		assertEquals(true, DateParser.isSameDate(currency, null));
	}

	@Test
	void should_ReturnTrue_When_CurrencyDateAndDateAreTheSame() {
		LocalDate today = LocalDate.now();
		CurrencyConversionRate currency = new CurrencyConversionRate(Currency.AUD, 0.0, today);
		assertEquals(true, DateParser.isSameDate(currency, today));
	}

	@Test
	void should_ReturnFalse_When_CurrencyDateAndDateAreDifferent() {
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		CurrencyConversionRate currency = new CurrencyConversionRate(Currency.AUD, 0.0, today);
		assertEquals(false, DateParser.isSameDate(currency, tomorrow));
	}

	@Test
	void should_ReturnAValidDateTime_When_ValidStringDateSent() {
		String date = "2022-11-01 16:10:54";

		assertEquals("2022-11-01T16:10:54", DateParser.parseToDateTime(date).toString());
	}

}
