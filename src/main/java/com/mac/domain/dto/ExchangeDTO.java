package com.mac.domain.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.mac.domain.Currency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Currency sourceCurrency;
	private Double sourceAmount;
	private LocalDate date;
	private Double exchangeAmount;
	private Currency exchangeCurrency;

}
