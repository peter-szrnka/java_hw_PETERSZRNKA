package com.ndvr.challenge.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import com.ndvr.challenge.model.Scenario;
import com.ndvr.challenge.service.ScenarioSelectorService;
import com.ndvr.challenge.util.TestDataGenerator;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

/**
 * Unit test of {@link ScenarioSelectorServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
class ScenarioSelectorServiceImplTest {

	private ListAppender<ILoggingEvent> logAppender;
	private ScenarioSelectorService service = new ScenarioSelectorServiceImpl();
	
	@BeforeEach
	public void setup() {
		logAppender = new ListAppender<>();
		logAppender.start();
		((Logger) LoggerFactory.getLogger(ScenarioSelectorServiceImpl.class)).addAppender(logAppender);
	}
	
	@AfterEach
	public void teardown() {
		logAppender.stop();
	}
	
	@Test
	void shouldCalculateBestScenario() {
		// act
		Scenario response = service.calculateBestScenario(TestDataGenerator.generateScenarios());
		
		// assert
		assertNotNull(response);
		assertTrue(logAppender.list.stream().anyMatch(log -> log.getFormattedMessage().startsWith("Lowest closing price: ")));
		assertTrue(logAppender.list.stream().anyMatch(log -> log.getFormattedMessage().startsWith("Highest closing price: ")));
		assertTrue(logAppender.list.stream().anyMatch(log -> log.getFormattedMessage().startsWith("Median of all projected price change scenarios: ")));
	}
}