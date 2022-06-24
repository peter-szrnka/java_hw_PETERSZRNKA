package com.ndvr.challenge.util;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ndvr.challenge.model.Scenario;

public class TestDataGenerator {
	
	public static List<Scenario> generateScenarios() {
		List<Scenario> resultList = new ArrayList<>();

		Random r = new SecureRandom();
		// Generate random data for the last 5 years if every month contains only 20
		// input data.
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
}