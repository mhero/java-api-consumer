package com.mac.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyUpdate {
	@Id
	@Enumerated(EnumType.STRING)
	private Currency currency;

	@Column
	private LocalDateTime date;

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
		CurrencyUpdate other = (CurrencyUpdate) obj;
		return currency == other.currency && Objects.equals(date, other.date);
	}

}
