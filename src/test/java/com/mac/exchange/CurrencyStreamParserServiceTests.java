package com.mac.exchange;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.mac.domain.Currency;
import com.mac.domain.CurrencyConversionRate;
import com.mac.exchange.api.CurrencyStreamParserService;
import com.mac.util.DateParser;

@SpringBootTest
@ActiveProfiles("test")
public class CurrencyStreamParserServiceTests {

	@Autowired
	CurrencyStreamParserService streamReader;

	private File initialFile;
	private InputStream targetStream;

	@Test
	void should_ReturnWholeFile_When_NoFilterApplied() throws FileNotFoundException {
		initialFile = new File("src/main/resources/fixture/full_data.csv");
		targetStream = new FileInputStream(initialFile);

		List<CurrencyConversionRate> data = streamReader.asList(targetStream, Currency.AUD);
		assertEquals(8793, data.size());
		assertEquals(new CurrencyConversionRate(Currency.AUD, null, DateParser.parseToDate("1999-01-01")), data.get(0));

	}

	@Test
	void should_ReturnEmptyList_When_StreamIsNull() {

		assertEquals(0, streamReader.asList(null, null).size());
	}

	@Test
	void should_ReturnEmptyList_When_StreamHasOnlyHeaders() throws FileNotFoundException {
		initialFile = new File("src/main/resources/fixture/no_data_no_headers.csv");
		targetStream = new FileInputStream(initialFile);

		assertEquals(0, streamReader.asList(targetStream, Currency.AUD).size());
	}

	@Test
	void should_ReturnEmptyList_When_StreamIsEmpty() throws FileNotFoundException {
		initialFile = new File("src/main/resources/fixture/no_data_with_headers.csv");
		targetStream = new FileInputStream(initialFile);

		assertEquals(0, streamReader.asList(targetStream, Currency.AUD).size());
	}

	@Test
	void should_LatestUpdateList_When_StreamIsComplete() throws FileNotFoundException {
		initialFile = new File("src/main/resources/fixture/full_data.csv");
		targetStream = new FileInputStream(initialFile);

		assertEquals(DateParser.parseToDateTime("2023-01-27 16:01:39"),
				streamReader.filterLatestUpdateStream(targetStream));
	}

}
