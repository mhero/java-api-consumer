package com.mac.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyConversionRateKey implements Serializable {

	private static final long serialVersionUID = 1L;

	private Currency currency;

	private LocalDate date;

	@Override
	public int hashCode() {
		return Objects.hash(currency, date);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurrencyConversionRateKey other = (CurrencyConversionRateKey) obj;
		return currency == other.currency && Objects.equals(date, other.date);
	}

}
