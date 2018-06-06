package com.fh.util;

import java.text.NumberFormat;
import java.util.UUID;
import java.util.regex.Pattern;

public class DigitalNumGenerate {

	public static String generateCode() {
		int i=5;
		NumberFormat nf = NumberFormat.getInstance();
		// Setting the seperate group
		nf.setGroupingUsed(false);
		// Setting the max-digit
		nf.setMaximumIntegerDigits(i);
		// Setting the min-digit
		nf.setMinimumIntegerDigits(i);
		// print
		return nf.format(Integer.valueOf(Pattern.compile("\\D").matcher(UUID.randomUUID().toString()).replaceAll("").trim().substring(0, i)));
	}
}
