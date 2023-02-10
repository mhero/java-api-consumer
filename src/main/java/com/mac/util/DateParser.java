package com.mac.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.validator.GenericValidator;

import com.mac.domain.CurrencyConversionRate;

public class DateParser {
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static LocalDate parseToDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		return date == null ? null : LocalDate.parse(date, formatter);
	}

	public static LocalDateTime parseToDateTime(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
		return date == null ? null : LocalDateTime.parse(date, formatter);
	}

	public static Boolean isValid(String date) {
		return GenericValidator.isDate(date, DATE_FORMAT, true);
	}

	public static Boolean isSameDate(CurrencyConversionRate currency, LocalDate date) {
		return (date == null || date.equals(currency.getDate()));
	}
}
