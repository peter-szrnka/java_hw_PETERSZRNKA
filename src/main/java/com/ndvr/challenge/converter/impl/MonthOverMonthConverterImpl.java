package com.ndvr.challenge.converter.impl;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.ndvr.challenge.converter.MonthOverMonthConverter;
import com.ndvr.challenge.model.Pricing;

@Component
public class MonthOverMonthConverterImpl implements MonthOverMonthConverter {
	
	private static final String DATE_FORMAT_Y_M = "yyyy-MM";

	@Override
	public List<BigDecimal> toPriceChangesList(List<Pricing> pricingHistoryData) {
		Map<String, List<BigDecimal>> datePriceMapping = new TreeMap<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_Y_M);
		
		for (Pricing pricing : pricingHistoryData) {
			datePriceMapping
				.computeIfAbsent(formatter.format(pricing.getTradeDate()), k -> new ArrayList<>())
				.add(pricing.getClosePrice());
		}

		List<BigDecimal> monthlyAverages = datePriceMapping.entrySet().stream().map(entry -> getMonthlyAverage(entry.getValue())).toList();
		List<BigDecimal> priceChanges = new ArrayList<>();
		
		for (int i = 1; i < monthlyAverages.size(); i++) {
			priceChanges.add(monthlyAverages.get(i).subtract(monthlyAverages.get(i - 1)));
		}

		return priceChanges;
	}

	private BigDecimal getMonthlyAverage(List<BigDecimal> dailyChanges) {
		return BigDecimal.valueOf(dailyChanges.stream().mapToDouble(BigDecimal::doubleValue).average().getAsDouble());
	}
}