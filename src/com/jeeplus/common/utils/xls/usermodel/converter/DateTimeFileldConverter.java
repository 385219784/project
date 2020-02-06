package com.jeeplus.common.utils.xls.usermodel.converter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;

import com.jeeplus.common.utils.xls.ExcelExpUtil;
import com.jeeplus.common.utils.xls.usermodel.FileldConverter;

public class DateTimeFileldConverter implements FileldConverter {
	
	private SimpleDateFormat format;
	
	public DateTimeFileldConverter() {
		
		this.format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	public DateTimeFileldConverter(String format) {
		
		this.format = new SimpleDateFormat(format);
	}

	public String convert(Cell templateCell, Cell dataCell, String field,
			Object m) {
		
		Object o = ExcelExpUtil.getObjValue(m, field);
		
		if(o!=null) {
			
			if(o instanceof Date) {
				
				return format.format((Date)o);
			} else if(o instanceof Calendar) {
				
				Calendar calendar = (Calendar) o;
				
				return format.format(calendar.getTime());
			}
		}
		
		return null;
	}	
}
