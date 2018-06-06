package com.fh.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatData {
	public static String getFormatNowTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME).replace("T", " ").substring(0, 19);
	}
}
