package com.ndvr.challenge.service;

import java.util.List;

import com.ndvr.challenge.model.Scenario;

public interface ScenarioSelectorService {

	/**
	 * Calculates:
	 * - one with the highest closing price at the end of the very last month,
	 * - lowest as well,
	 * - median ending value. 
	 * 
	 * @param generatedScenarios The generated scenarios
	 */
	Scenario calculateBestScenario(List<Scenario> generatedScenarios);
}