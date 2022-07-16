package com.ndvr.challenge.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import com.ndvr.challenge.converter.MonthOverMonthConverter;
import com.ndvr.challenge.dataprovider.YahooFinanceClient;
import com.ndvr.challenge.model.Scenario;
import com.ndvr.challenge.util.TestDataGenerator;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import lombok.AllArgsConstructor;

/**
 * Unit test of {@link ChallengeService}
 */
@ExtendWith(MockitoExtension.class)
class ChallengeServiceTest {

	private ListAppender<ILoggingEvent> logAppender;

	@Mock
	private YahooFinanceClient dataProvider;
	@Mock
	private ScenarioGeneratorService scenarioGeneratorService;
	@Mock
	private ScenarioSelectorService scenarioSelectorService;
	@Mock
	private MonthOverMonthConverter monthOverMonthConverter;
	@InjectMocks
	private ChallengeService service;

	@BeforeEach
	public void setup() {
		logAppender = new ListAppender<>();
		logAppender.start();
		((Logger) LoggerFactory.getLogger(ChallengeService.class)).addAppender(logAppender);
	}

	@AfterEach
	public void teardown() {
		logAppender.stop();
	}	

	@ParameterizedTest
	@MethodSource("inputData")
	void shouldSucceedwithoutNumberOfMonthsInput(InputData inputData) {
		// arrange
		List<Scenario> mockScenarioList = TestDataGenerator.generateScenarios();
		when(scenarioGeneratorService.generateScenarios(anyList(), eq(inputData.expected))).thenReturn(mockScenarioList);
		when(scenarioSelectorService.calculateBestScenario(anyList())).thenReturn(mockScenarioList.get(0));
		when(dataProvider.fetchPriceData(anyString(), any(LocalDate.class), any(LocalDate.class)))
			.thenReturn(TestDataGenerator.generateRandomTestInput());
		when(monthOverMonthConverter.toPriceChangesList(anyList())).thenReturn(TestDataGenerator.generateRandomTestData());

		// act
		List<BigDecimal> response = service.getProjectedAssetData("TSLA", inputData.input);

		// assert
		assertNotNull(response);
		verify(scenarioGeneratorService).generateScenarios(anyList(), eq(inputData.expected));
		verify(scenarioSelectorService).calculateBestScenario(anyList());
		verify(dataProvider).fetchPriceData(anyString(), any(LocalDate.class), any(LocalDate.class));
		verify(monthOverMonthConverter).toPriceChangesList(anyList());
		assertTrue(logAppender.list.stream().anyMatch(log -> log.getFormattedMessage().equals("Got 1200 items of historical asset data to process")));
		assertTrue(logAppender.list.stream().anyMatch(log -> log.getFormattedMessage()
				.equals("Generating " + inputData.expected + " months of projected price data for TSLA")));
	}
	
	private static InputData[] inputData() {
		return new InputData[] { new InputData(null, 240), new InputData(10, 10) };
	}

	@AllArgsConstructor
	private static class InputData {
		private Integer input;
		private Integer expected;
	}
}