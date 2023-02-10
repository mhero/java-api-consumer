package com.mac.domain;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

import com.mac.util.DateParser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@IdClass(CurrencyConversionRateKey.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyConversionRate {

	private static final String EMPTY_PLACEHOLDER = ".";
	@Id
	@Column
	@Enumerated(EnumType.STRING)
	private Currency currency;

	@Column
	private Double conversionRate;

	@Id
	@Column
	private LocalDate date;

	public CurrencyConversionRate(Currency currency, String[] rawRateData) {

		if (currency == null) {
			throw new IllegalArgumentException("Error empty currency");
		}
		this.currency = currency;

		try {
			this.conversionRate = parseRate(rawRateData[1]);
			this.date = DateParser.parseToDate(rawRateData[0]);
		} catch (NumberFormatException | DateTimeParseException e) {
			throw new IllegalArgumentException(e.getCause());
		}

	}

	private Double parseRate(String rate) {
		return EMPTY_PLACEHOLDER.equals(rate) ? null : Double.valueOf(rate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(conversionRate, currency, date);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurrencyConversionRate other = (CurrencyConversionRate) obj;
		return Objects.equals(conversionRate, other.conversionRate) && currency == other.currency
				&& Objects.equals(date, other.date);
	}

}
