package com.mac.currency;

import static org.hamcrest.Matchers.hasSize;
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
public class RateControllerTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private CurrencyConversionRateRepository currencyConversionRateRepository;

	@BeforeEach
	public void setUp() {
		insertCurrency();
	}

	@Test
	public void givenCurrencies_whenGetRates_thenStatus200() throws Exception {

		mvc.perform(get("/api/rates").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.content", hasSize(3)))
				.andExpect(jsonPath("$.content[0].currency", is(Currency.AUD.toString())))
				.andExpect(jsonPath("$.content[0].conversionRate", is(2.1)))
				.andExpect(jsonPath("$.content[0].date", is(LocalDate.now().toString())))
				.andExpect(jsonPath("$.content[1].currency", is(Currency.AUD.toString())))
				.andExpect(jsonPath("$.content[1].conversionRate", is(1.76)))
				.andExpect(jsonPath("$.content[1].date", is(LocalDate.now().plusDays(1).toString())))
				.andExpect(jsonPath("$.content[2].currency", is(Currency.USD.toString())))
				.andExpect(jsonPath("$.content[2].conversionRate", is(3.86)))
				.andExpect(jsonPath("$.content[2].date", is(LocalDate.now().toString()))).andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void givenCurrencies_whenGetRatesWithCurrency_thenStatus200() throws Exception {

		mvc.perform(get("/api/rates").param("currency", Currency.AUD.toString())
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$.content", hasSize(2)))
				.andExpect(jsonPath("$.content[0].currency", is(Currency.AUD.toString())))
				.andExpect(jsonPath("$.content[0].conversionRate", is(2.1)))
				.andExpect(jsonPath("$.content[0].date", is(LocalDate.now().toString())))
				.andExpect(jsonPath("$.content[1].currency", is(Currency.AUD.toString())))
				.andExpect(jsonPath("$.content[1].conversionRate", is(1.76)))
				.andExpect(jsonPath("$.content[1].date", is(LocalDate.now().plusDays(1).toString())))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void givenCurrencies_whenGetRatesWithDate_thenStatus200() throws Exception {

		mvc.perform(get("/api/rates").param("date", LocalDate.now().toString())
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$.content", hasSize(2)))
				.andExpect(jsonPath("$.content[0].currency", is(Currency.AUD.toString())))
				.andExpect(jsonPath("$.content[0].conversionRate", is(2.1)))
				.andExpect(jsonPath("$.content[0].date", is(LocalDate.now().toString())))
				.andExpect(jsonPath("$.content[1].currency", is(Currency.USD.toString())))
				.andExpect(jsonPath("$.content[1].conversionRate", is(3.86)))
				.andExpect(jsonPath("$.content[1].date", is(LocalDate.now().toString()))).andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void givenCurrencies_whenGetRatesWithDateAndCurrency_thenStatus200() throws Exception {

		mvc.perform(get("/api/rates").param("date", LocalDate.now().toString())
				.param("currency", Currency.AUD.toString()).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.content", hasSize(1)))
				.andExpect(jsonPath("$.content[0].currency", is(Currency.AUD.toString())))
				.andExpect(jsonPath("$.content[0].conversionRate", is(2.1)))
				.andExpect(jsonPath("$.content[0].date", is(LocalDate.now().toString()))).andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void givenCurrencies_whenGetRatesWithNoAvailableCurrency_thenStatus200() throws Exception {

		mvc.perform(get("/api/rates").param("currency", Currency.SEK.toString())
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$.content", hasSize(0)))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void givenCurrencies_whenGetRatesWithNoAvailableDate_thenStatus200() throws Exception {

		mvc.perform(get("/api/rates").param("date", LocalDate.now().plusDays(2).toString())
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$.content", hasSize(0)))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void givenCurrencies_whenGetCurrencyDoesNotExist_thenStatus400() throws Exception {

		mvc.perform(get("/api/rates").param("currency", "XXX").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is4xxClientError()).andReturn();
	}

	@Test
	public void givenCurrencies_whenGetDateisNotValid_thenStatus400() throws Exception {

		mvc.perform(get("/api/rates").param("date", "2023-13-13").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is4xxClientError()).andReturn();
	}

	private void insertCurrency() {
		List<CurrencyConversionRate> currencyConversionRate = new ArrayList<>();
		currencyConversionRate.add(new CurrencyConversionRate(Currency.AUD, 2.1, LocalDate.now()));
		currencyConversionRate.add(new CurrencyConversionRate(Currency.AUD, 1.76, LocalDate.now().plusDays(1)));
		currencyConversionRate.add(new CurrencyConversionRate(Currency.USD, 3.86, LocalDate.now()));
		currencyConversionRateRepository.saveAll(currencyConversionRate);
	}
}
