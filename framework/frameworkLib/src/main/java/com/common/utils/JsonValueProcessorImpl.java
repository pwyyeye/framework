package com.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonValueProcessorImpl implements JsonValueProcessor {
	private String format = "yyyy-MM-dd";

	public JsonValueProcessorImpl() {

	}

	public JsonValueProcessorImpl(String format) {
		this.format = format;
	}

	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		String[] obj = {};
		if (value instanceof Calendar[]) {
			Calendar[] dates = (Calendar[]) value;
			obj = new String[dates.length];
			for (int i = 0; i < dates.length; i++) {
				Calendar cal = (Calendar) dates[i];
				Date date = cal.getTime();
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				obj[i] = sdf.format(date);
			}
		}
		return obj;
	}

	public Object processObjectValue(String key, Object value,
			JsonConfig jsonConfig) {
		if (value instanceof Calendar) {
			Calendar cal = (Calendar) value;
			Date date = cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return (sdf.format(date));
		}
		return value == null ? "" : value.toString();
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
