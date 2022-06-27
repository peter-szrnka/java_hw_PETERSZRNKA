package com.ndvr.challenge.util;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ndvr.challenge.model.Pricing;
import com.ndvr.challenge.model.Scenario;

public class TestDataGenerator {
	
	public static List<Scenario> generateScenarios() {
		List<Scenario> resultList = new ArrayList<>();

		Random r = new SecureRandom();
		// input data
		// Generate random data for the last 5 years if every month contains only 20
		for (int i = 0; i < 20 * 12 * 5; i++) {
			BigDecimal randomLowestPriceChange = BigDecimal.valueOf(r.nextDouble(0, 50));
			BigDecimal randomHighestPriceChange = BigDecimal.valueOf(r.nextDouble(51, 100));
			resultList.add(new Scenario(generateRandomTestData(), randomLowestPriceChange, randomHighestPriceChange));
		}

		return resultList;
	}

	public static List<BigDecimal> generateRandomTestData() {
		List<BigDecimal> resultList = new ArrayList<>();

		Random r = new SecureRandom();
		// Generate random data for the last 5 years if every month contains only 20
		// input data.
		for (int i = 0; i < 20 * 12 * 5; i++) {
			resultList.add(BigDecimal.valueOf(r.nextDouble(0, 100)));
		}

		return resultList;
	}

	public static List<Pricing> generateRandomTestInput() {
		List<Pricing> resultList = new ArrayList<>();

		// Generate random data for the last 5 years if every month contains only 20
		// input data.
		Random r = new SecureRandom();
		for (int i = 0; i < 20 * 12 * 5; i++) {
			BigDecimal openPrice = BigDecimal.valueOf(r.nextDouble(0, 100));
			BigDecimal closePrice = BigDecimal.valueOf(r.nextDouble(0, 100));
			BigDecimal lowPrice = BigDecimal.valueOf(r.nextDouble(0, 100));
			BigDecimal highPrice = BigDecimal.valueOf(r.nextDouble(0, 100));
			resultList.add(new Pricing(openPrice, closePrice, lowPrice, highPrice, LocalDate.now().minusDays(1200-i)));
		}
		
		return resultList;
	}

	public static List<Pricing> generateFixTestInput() {
		List<Pricing> resultList = new ArrayList<>();
		
		resultList.add(new Pricing(null, BigDecimal.valueOf(1), null, null, LocalDate.now().minusMonths(2).minusDays(5)));
		resultList.add(new Pricing(null, BigDecimal.valueOf(3), null, null, LocalDate.now().minusMonths(2).minusDays(4)));
		resultList.add(new Pricing(null, BigDecimal.valueOf(2), null, null, LocalDate.now().minusMonths(2).minusDays(3)));
		resultList.add(new Pricing(null, BigDecimal.valueOf(6), null, null, LocalDate.now().minusMonths(2).minusDays(2)));
		resultList.add(new Pricing(null, BigDecimal.valueOf(4), null, null, LocalDate.now().minusMonths(2).minusDays(1)));
		resultList.add(new Pricing(null, BigDecimal.valueOf(5), null, null, LocalDate.now().minusMonths(2)));
		
		resultList.add(new Pricing(null, BigDecimal.valueOf(5), null, null, LocalDate.now().minusMonths(1).minusDays(5)));
		resultList.add(new Pricing(null, BigDecimal.valueOf(7), null, null, LocalDate.now().minusMonths(1).minusDays(4)));
		resultList.add(new Pricing(null, BigDecimal.valueOf(3), null, null, LocalDate.now().minusMonths(1).minusDays(3)));
		resultList.add(new Pricing(null, BigDecimal.valueOf(9), null, null, LocalDate.now().minusMonths(1).minusDays(2)));
		resultList.add(new Pricing(null, BigDecimal.valueOf(17), null, null, LocalDate.now().minusMonths(1).minusDays(1)));
		resultList.add(new Pricing(null, BigDecimal.valueOf(10), null, null, LocalDate.now().minusMonths(1)));
		
		resultList.add(new Pricing(null, BigDecimal.valueOf(12), null, null, LocalDate.now().minusDays(5)));
		resultList.add(new Pricing(null, BigDecimal.valueOf(14), null, null, LocalDate.now().minusDays(4)));
		resultList.add(new Pricing(null, BigDecimal.valueOf(13), null, null, LocalDate.now().minusDays(3)));
		resultList.add(new Pricing(null, BigDecimal.valueOf(11), null, null, LocalDate.now().minusDays(2)));
		resultList.add(new Pricing(null, BigDecimal.valueOf(33), null, null, LocalDate.now().minusDays(1)));
		resultList.add(new Pricing(null, BigDecimal.valueOf(40), null, null, LocalDate.now()));
		
		return resultList;
	}
};