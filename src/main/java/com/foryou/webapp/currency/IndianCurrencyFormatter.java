package com.foryou.webapp.currency;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.stereotype.Component;

@Component
public class IndianCurrencyFormatter {
	 public static String format(double value) {
	        NumberFormat formatter = DecimalFormat.getInstance(new Locale("en", "IN"));
	        return formatter.format(value);
	    }
}

