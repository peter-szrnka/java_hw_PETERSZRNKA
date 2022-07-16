package com.ndvr.challenge.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.ndvr.challenge.model.Scenario;
import com.ndvr.challenge.service.ScenarioGeneratorService;
import com.ndvr.challenge.util.TestDataGenerator;

/**
 * Unit test of {@link ScenarioGeneratorServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
class ScenarioGeneratorServiceImplTest {

	private ScenarioGeneratorService service = new ScenarioGeneratorServiceImpl();

	@BeforeEach
	void setup() {
		ReflectionTestUtils.setField(service, "generateLimit", 100);
	}

	@Test
	void shouldGenerate100Scenarios() {
		// arrange
		List<BigDecimal> randomTestData = TestDataGenerator.generateRandomTestData();
		// act
		List<Scenario> response = service.generateScenarios(randomTestData, 240);

		// assert
		assertNotNull(response);
		assertEquals(100, response.size());
		assertEquals(24000, response.stream().flatMap(l -> l.getPriceChanges().stream()).count());
		
		response.forEach(scenario -> {
			assertNotNull(scenario.getLowestClosingPrice());
			assertNotNull(scenario.getHighestClosingPrice());
		});
	}
}