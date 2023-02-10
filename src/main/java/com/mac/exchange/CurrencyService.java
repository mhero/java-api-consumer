package com.mac.exchange;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.mac.domain.Currency;
import com.mac.domain.dto.CurrencyDTO;

@Service
public class CurrencyService {
	public ArrayList<CurrencyDTO> getRates() {
		ArrayList<CurrencyDTO> currencies = new ArrayList<>();
		for (Currency currency : Currency.values()) {
			currencies.add(new CurrencyDTO(currency));
		}
		return currencies;
	}

}
