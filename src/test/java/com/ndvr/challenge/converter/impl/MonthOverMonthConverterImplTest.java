package com.ndvr.challenge.converter.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ndvr.challenge.converter.MonthOverMonthConverter;
import com.ndvr.challenge.util.TestDataGenerator;

/**
 * Unit test of {@link MonthOverMonthConverterImpl}
 */
@ExtendWith(MockitoExtension.class)
class MonthOverMonthConverterImplTest {

	private MonthOverMonthConverter converter = new MonthOverMonthConverterImpl();
	
	@Test
	void shouldReturnWith2PriceChanges() {
		// act
		List<BigDecimal> response = converter.toPriceChangesList(TestDataGenerator.generateFixTestInput());
		
		// assert
		assertNotNull(response);
		assertEquals(2, response.size());
		assertEquals(BigDecimal.valueOf(5.0), response.get(0));
		assertEquals(BigDecimal.valueOf(12.0), response.get(1));
	}
}