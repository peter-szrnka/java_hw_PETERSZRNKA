package com.ndvr.challenge.service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.ndvr.challenge.dataprovider.YahooFinanceClient;
import com.ndvr.challenge.model.Pricing;
import com.ndvr.challenge.model.Scenario;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class ChallengeService {

	// 20 years
    private static final int DEFAULT_NUMBER_OF_MONTHS = 240;
	private final YahooFinanceClient dataProvider;
    private final ScenarioGeneratorService scenarioGeneratorService;
    private final ScenarioSelectorService scenarioSelectorService;

    public List<Pricing> getHistoricalAssetData(String symbol, LocalDate fromDate, LocalDate toDate) {
        log.info("Fetching historical price data for {}", symbol);
        return dataProvider.fetchPriceData(symbol, fromDate, toDate);
    }
    
    public List<BigDecimal> getProjectedAssetData(String symbol, Integer numberOfMonths) {
    	Integer nrOfMonths = (numberOfMonths == null) ? DEFAULT_NUMBER_OF_MONTHS : numberOfMonths;
        log.info("Generating {} months of projected price data for {}", nrOfMonths, symbol);

        // a.Extract historical price movements: Calculate a list of month-over-month price changes from the available historical data
        List<BigDecimal> priceChanges = getPriceChanges(symbol, nrOfMonths);
        
        // b. Generate 1000 scenarios 
        List<Scenario> generatedScenarios = scenarioGeneratorService.generateScenarios(priceChanges, nrOfMonths);
        
        // return the one with the highest closing price at the end of the very last month. 
        // Also log the lowest as well as the median ending value.
        Scenario bestScenario = scenarioSelectorService.calculateBestScenario(generatedScenarios);
        
        return bestScenario.getPriceChanges();
    }

    /**
     * TODO This is under clarification. Refactor this method to query historical data from the getHistoricalAssetData() method.
     */
	private List<BigDecimal> getPriceChanges(String symbol, Integer numberOfMonths) {
        //Double result = getHistoricalAssetData(symbol, LocalDate.now().minusMonths(numberOfMonths), LocalDate.now())
        //.stream().map(pricing -> pricing.getClosePrice()).collect(Collectors.averagingDouble(BigDecimal::doubleValue));
		List<BigDecimal> resultList = new ArrayList<>();
		
		Random r = new SecureRandom();
		// Generate random data for the last 5 years if every month contains only 20 input data.
		for (int i=0; i < 20 * 12 * 5; i++) {
			resultList.add(BigDecimal.valueOf(r.nextDouble(0, 100)));
		}
		
		return resultList;
	}
}