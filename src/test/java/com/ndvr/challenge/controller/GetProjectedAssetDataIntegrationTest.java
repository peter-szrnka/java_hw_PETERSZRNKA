package com.ndvr.challenge.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import com.ndvr.challenge.dataprovider.YahooFinanceClient;
import com.ndvr.challenge.util.TestDataGenerator;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class GetProjectedAssetDataIntegrationTest {
	
	private static final String BASE_URL = "http://localhost:";
	private static final String URL_POSTFIX = "/market-data/TSLA/projected";
	
	@LocalServerPort
	protected int port;

	@MockBean
	YahooFinanceClient dataProvider;
	
	@Autowired
	protected RestTemplate restTemplate;

	@Test
	@SuppressWarnings({ "rawtypes" }) // This can be removed if we use a wrapper class instead of returning collections.
	void shouldReturnProjectedAssetDataWithoutSettingNumberOfMonths() {
		// arrange
		HttpEntity<Void> requestEntity = new HttpEntity<>(getApiHttpHeaders());
		when(dataProvider.fetchPriceData(anyString(), any(LocalDate.class), any(LocalDate.class))).thenReturn(TestDataGenerator.generateRandomTestInput());
		
		// act
		ResponseEntity<List> response = restTemplate.exchange(BASE_URL + port + URL_POSTFIX, HttpMethod.GET, requestEntity, List.class);
		
		// assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(240, response.getBody().size());
	}
	
	@Test
	@SuppressWarnings({ "rawtypes" }) // This can be removed if we use a wrapper class instead of returning collections.
	void shouldReturnProjectedAssetData() {
		// arrange
		when(dataProvider.fetchPriceData(anyString(), any(LocalDate.class), any(LocalDate.class))).thenReturn(TestDataGenerator.generateFixTestInput());

		// act
		ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL + port + URL_POSTFIX + "?numberOfMonths=10", List.class);
		
		// assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(10, response.getBody().size());
	}
	
	private static HttpHeaders getApiHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}