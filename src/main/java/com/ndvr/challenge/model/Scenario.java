package com.ndvr.challenge.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Scenario {

	private List<BigDecimal> priceChanges;
	private BigDecimal lowestClosingPrice;
	private BigDecimal highestClosingPrice;
}