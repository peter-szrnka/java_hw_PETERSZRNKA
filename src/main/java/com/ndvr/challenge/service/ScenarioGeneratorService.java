package com.ndvr.challenge.service;

import java.math.BigDecimal;
import java.util.List;

import com.ndvr.challenge.model.Scenario;

public interface ScenarioGeneratorService {

	/**
	 * Generates N scenarios, and each scenario will contain "numberOfMonths" months of prices changes.
	 * 
	 * @param inputData
	 * @param numberOfMonths
	 * @return A list of {@link Scenario} instances.
	 */
	List<Scenario> generateScenarios(List<BigDecimal> inputData, Integer numberOfMonths);
}