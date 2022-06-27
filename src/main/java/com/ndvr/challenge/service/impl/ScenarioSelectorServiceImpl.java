package com.ndvr.challenge.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ndvr.challenge.model.Scenario;
import com.ndvr.challenge.service.ScenarioSelectorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ScenarioSelectorServiceImpl implements ScenarioSelectorService {

	@Override
	public Scenario calculateBestScenario(List<Scenario> generatedScenarios) {
		Optional<Scenario> lowest = Optional.empty();
		Optional<Scenario> highest = Optional.empty();

		for (Scenario scenario : generatedScenarios) {
			if (lowest.isEmpty() || scenario.getLowestClosingPrice().compareTo(lowest.get().getLowestClosingPrice()) < 0) {
				lowest = Optional.of(scenario);
			}

			if (highest.isEmpty() || scenario.getHighestClosingPrice().compareTo(highest.get().getHighestClosingPrice()) > 0) {
				highest = Optional.of(scenario);
			}
		}

		lowest.ifPresent(lowestValue -> log.info("Lowest closing price: {}", lowestValue.getLowestClosingPrice()));
		lowest.ifPresent(highestValue -> log.info("Highest closing price: {}", highestValue.getHighestClosingPrice()));
		log.info("Median of all projected price change scenarios: {}", getMedian(generatedScenarios
				.stream()
				.flatMap(scenario -> scenario.getPriceChanges().stream())
				.sorted().toList()));

		return highest.orElseThrow();
	}
	
	private static BigDecimal getMedian(List<BigDecimal> input) {
		int n = input.size();	
		return (input.get(n/2-1).add(input.get(n/2))).divide(BigDecimal.valueOf(2));
	}
}