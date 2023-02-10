package com.mac.domain.dto;

import java.io.Serializable;

import com.mac.domain.Currency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String label;
	private String details;

	public CurrencyDTO(Currency currency) {
		this.id = currency.name();
		this.label = currency.label;
		this.details = currency.details;
	}

}
