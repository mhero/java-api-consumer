package com.mac.currency;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.mac.Application;
import com.mac.domain.Currency;
import com.mac.domain.CurrencyConversionRate;
import com.mac.repository.CurrencyConversionRateRepository;

@ActiveProfiles("test")

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource("../../resources/test.properties")
public class ExchangeControllerTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private CurrencyConversionRateRepository currencyConversionRateRepository;

	@BeforeEach
	public void setUp() {
		insertCurrency();
	}

	@Test
	public void givenCurrencies_whenSendAmount_thenStatus200() throws Exception {
		mvc.perform(get("/api/exchange").param("currency", Currency.AUD.toString())
				.param("date", LocalDate.now().toString()).param("amount", "500")
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$.exchangeAmount", is(500 / 1.56)))
				.andExpect(jsonPath("$.exchangeCurrency", is(Currency.EUR.toString())))
				.andExpect(jsonPath("$.sourceAmount", is(500.0)))
				.andExpect(jsonPath("$.sourceCurrency", is(Currency.AUD.toString()))).andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void givenCurrencies_whenNoAmount_thenStatus400() throws Exception {
		mvc.perform(get("/api/exchange").param("currency", Currency.AUD.toString())
				.param("date", LocalDate.now().toString()).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is4xxClientError()).andReturn();
	}

	@Test
	public void givenCurrencies_whenNoDate_thenStatus400() throws Exception {
		mvc.perform(get("/api/exchange").param("currency", Currency.AUD.toString()).param("amount", "2.2")
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().is4xxClientError()).andReturn();
	}

	@Test
	public void givenCurrencies_whenNoCurrency_thenStatus$00() throws Exception {
		mvc.perform(get("/api/exchange").param("date", LocalDate.now().toString()).param("amount", "2.2")
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().is4xxClientError()).andReturn();
	}

	private void insertCurrency() {
		List<CurrencyConversionRate> currencyConversionRate = new ArrayList<>();
		currencyConversionRate.add(new CurrencyConversionRate(Currency.AUD, 1.56, LocalDate.now()));
		currencyConversionRate.add(new CurrencyConversionRate(Currency.AUD, 1.76, LocalDate.now().plusDays(1)));
		currencyConversionRate.add(new CurrencyConversionRate(Currency.USD, 3.86, LocalDate.now()));
		currencyConversionRateRepository.saveAll(currencyConversionRate);
	}
}
