package com.ndvr.challenge.service.impl;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ndvr.challenge.model.Scenario;
import com.ndvr.challenge.service.ScenarioGeneratorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ScenarioGeneratorServiceImpl implements ScenarioGeneratorService {

	@Value("${config.scenario.generate.limit}")
	private int generateLimit;

	@Override
	public List<Scenario> generateScenarios(List<BigDecimal> inputData, Integer numberOfMonths) {
		List<Scenario> futureScenarios = new ArrayList<>();

		for (int i = 0; i < generateLimit; i++) {
			futureScenarios.add(generateScenario(inputData, numberOfMonths));
		}

		return futureScenarios;
	}

	private Scenario generateScenario(List<BigDecimal> inputData, Integer numberOfMonths) {
		List<BigDecimal> futurePriceChangeList = new ArrayList<>();

		// start with the current price
		futurePriceChangeList.add(inputData.get(inputData.size() - 1));

		BigDecimal lowest = null;
		BigDecimal highest = null;

		Random random = new SecureRandom();
		for (int i = 0; i < numberOfMonths - 1; i++) {
			int randomNumber = random.nextInt(inputData.size() - 1);
			BigDecimal randomPriceChange = inputData.get(randomNumber);
			futurePriceChangeList.add(randomPriceChange);

			if (lowest == null || randomPriceChange.compareTo(lowest) < 0) {
				log.debug("New lowest price found: {}", randomPriceChange);
				lowest = randomPriceChange;
			}

			if (highest == null || randomPriceChange.compareTo(highest) > 0) {
				log.debug("New highest price found: {}", randomPriceChange);
				highest = randomPriceChange;
			}
		}

		return new Scenario(futurePriceChangeList, lowest, highest);
	}
}