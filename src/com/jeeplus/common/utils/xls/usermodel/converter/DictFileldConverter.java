package com.jeeplus.common.utils.xls.usermodel.converter;

import org.apache.poi.ss.usermodel.Cell;

import com.jeeplus.common.utils.xls.ExcelExpUtil;
import com.jeeplus.common.utils.xls.usermodel.FileldConverter;

public class DictFileldConverter implements FileldConverter {
	
	private String dictTypeId;
	
	public DictFileldConverter(String dictTypeId) {
		super();
		this.dictTypeId = dictTypeId;
	}

	public String convert(Cell templateCell, Cell dataCell, String field,
			Object m) {
		
		Object v = ExcelExpUtil.getObjValue(m, field);
		
		if(v!=null) {
			//#TODO
			return "";//BusinessDictUtil.getDictName(dictTypeId, v.toString());
		}
		
		return null;
	}
}
