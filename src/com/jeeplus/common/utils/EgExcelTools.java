package com.jeeplus.common.utils;

import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.ResourceUtils;

import com.jeeplus.common.utils.xls.ExcelExp;
import com.jeeplus.common.utils.xls.ExcelImp;
import com.jeeplus.common.utils.xls.usermodel.ImpModel;
import com.jeeplus.common.utils.xls.usermodel.TemplateSheet;

public class EgExcelTools {
	
	public static String getWebFilePath(String filePath) throws Exception {
		
		String p = ResourceUtils.getFile("classpath:" + filePath).getAbsolutePath();
		
		return p;
	}
	
	public static void main(String[] args) throws Exception {
		
		System.out.println(getWebFilePath("templates/business/tabulation/tabulation_template.xls"));
	}
	
	/**
	 * @return
	 * @throws Exception
	 */	
	public static Workbook expData(String templateFile, TemplateSheet templateSheet) throws Exception {
		
		ExcelExp excelExp = new ExcelExp(templateFile);
		
		Workbook workbook = null;
		
		workbook = excelExp.draw(templateSheet);
		
		excelExp.deleteTemplate();
		
		return workbook;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */	
	public static Workbook expData(String templateFile, TemplateSheet[] templateSheets) throws Exception {
		
		ExcelExp excelExp = new ExcelExp(templateFile);
		
		Workbook workbook = null;
		
		for(TemplateSheet templateSheet: templateSheets) {
			
			workbook = excelExp.draw(templateSheet);
		}
		
		excelExp.deleteTemplate();

		return workbook;
	}
	
	@SuppressWarnings("unchecked")
	public static List<?> getList4Excel(InputStream in, Class<?> className, ImpModel impModel) throws Exception {
		
		@SuppressWarnings("rawtypes")
		ExcelImp<?> excelImp = new ExcelImp(in, impModel, className);
		
		return excelImp.getList();
	}
}
