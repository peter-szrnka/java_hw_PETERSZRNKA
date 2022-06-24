package com.ndvr.challenge.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ndvr.challenge.model.Scenario;
import com.ndvr.challenge.service.ScenarioSelectorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ScenarioSelectorServiceImpl implements ScenarioSelectorService {

	@Override
	public Scenario calculateBestScenario(List<Scenario> generatedScenarios) {
		Scenario lowest = null;
		Scenario highest = null;

		for (Scenario scenario : generatedScenarios) {
			if (lowest == null || scenario.getLowestClosingPrice().compareTo(lowest.getLowestClosingPrice()) < 0) {
				lowest = scenario;
			}

			if (highest == null || scenario.getHighestClosingPrice().compareTo(highest.getHighestClosingPrice()) > 0) {
				highest = scenario;
			}
		}

		log.info("Lowest closing price: {}", lowest);
		log.info("Highest closing price: {}", highest);
		// TODO Median

		return highest;
	}
}