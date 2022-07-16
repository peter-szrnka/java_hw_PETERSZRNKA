package com.ndvr.challenge.converter;

import java.math.BigDecimal;
import java.util.List;

import com.ndvr.challenge.model.Pricing;

public interface MonthOverMonthConverter {

	List<BigDecimal> toPriceChangesList(List<Pricing> pricingHistoryData);
}