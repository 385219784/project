package com.jeeplus.common.utils.xls;



import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.jeeplus.common.utils.EgUtil;
import com.jeeplus.common.utils.xls.usermodel.FieldMapping;
import com.jeeplus.common.utils.xls.usermodel.ImpModel;

public class ExcelImp<T> {
	
	private SimpleDateFormat sdf = new SimpleDateFormat();
	
	private InputStream in;

	private ImpModel impModel;

	private Class<?> classType;

	public ExcelImp(InputStream in, ImpModel impModel, Class<?> class1) {
		super();
		this.in = in;
		this.impModel = impModel;
		this.classType = class1;
	}

	public ExcelImp(InputStream in, ImpModel fms, String className) throws Exception {

		this(in, fms, Class.forName(className));
	}

	public List<T> getList() throws Exception {

		List<T> list = new ArrayList<T>();

		Workbook wb = WorkbookFactory.create(in);

		Sheet sheet = wb.getSheetAt(impModel.getSheetAt());

		for (int i = impModel.getFieldAt(); i <= sheet.getLastRowNum(); i++) {

			Row row = sheet.getRow(i);

			if (row != null) {
				
				@SuppressWarnings("unchecked")
				T bean = (T) classType.newInstance();
				
				for (FieldMapping fieldMapping : impModel.getFms()) {

					Cell cell = row.getCell(fieldMapping.getCellAt());
					
					if(cell!=null) {
						
						Object value = getCellValue(cell, fieldMapping);
						
						if(value!=null) {
							
							BeanUtils.setProperty(bean, fieldMapping.getProperty(), value);
						}
					}
				}
				
				list.add(bean);
			}
		}

		return list;
	}
	
	private Object getCellValue(Cell cell, FieldMapping fieldMapping) throws Exception {
		
		if("date".equalsIgnoreCase(fieldMapping.getType())) {
			
			if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				
				return cell.getDateCellValue();
			} else {
				
				String c = cell.getStringCellValue();
				sdf.applyPattern(fieldMapping.getFormart());
				return EgUtil.isNullOrEmpty(c)? null: sdf.parse(c);
			}			
		} else if("BigDecimal".equalsIgnoreCase(fieldMapping.getType())){
			
			if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				
				return new BigDecimal(String.valueOf(cell.getNumericCellValue()));
			} else if(cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA){
				
				return new BigDecimal(String.valueOf(cell.getNumericCellValue()));
			} else {
				
				String v = cell.getStringCellValue();
				
				if(EgUtil.isNullOrEmpty(v)) {
					
					return null;
				} else {
					
					return new BigDecimal(v);
				}
			}
		} else if("int".equalsIgnoreCase(fieldMapping.getType())){
			
			return Integer.valueOf(cell.getStringCellValue());
		} else {
			
			return cell.getStringCellValue();
		}
	}
}
