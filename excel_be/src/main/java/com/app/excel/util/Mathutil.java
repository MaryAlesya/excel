package com.app.excel.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Mathutil {
	
	public Float roundingValue(double value) {
		// Round average to 2 decimal places using BigDecimal
		Float result = BigDecimal.valueOf(value)
		        .setScale(2, RoundingMode.HALF_UP)
		        .floatValue();
		return result;
	
	}

}
