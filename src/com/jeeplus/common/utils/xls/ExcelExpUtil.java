package com.jeeplus.common.utils.xls;


import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jeeplus.common.utils.xls.usermodel.RichTextModel;

public class ExcelExpUtil {
	
	public static String temp = System.getProperty("java.io.tmpdir");
	
	public static String getTempFile4Xls() throws Exception {

		String filepath = temp + getUUID() + ".xls";
		return filepath;
	}

	public static String getTempFile4Xlsx() throws Exception {

		String filepath = temp + getUUID() + ".xlsx";
		return filepath;
	}

	public static String createTempFile4Xls() throws Exception {

		String filepath = getTempFile4Xls();

		Workbook wb = new HSSFWorkbook();
		FileOutputStream fileOut = new FileOutputStream(filepath);
		wb.write(fileOut);
		fileOut.close();

		return filepath;
	}

	public static String createTempFile4Xlsx() throws Exception {

		String filepath = getTempFile4Xlsx();

		Workbook wb = new XSSFWorkbook();
		FileOutputStream fileOut = new FileOutputStream(filepath);
		wb.write(fileOut);
		fileOut.close();

		return filepath;
	}

	public static boolean isExists$(String str) {

		if (str == null)
			return false;

		Pattern p = Pattern.compile("\\$\\{(.*?)}");
		Matcher m = p.matcher(str);

		return m.find();
	}

	public static boolean isExistsNo(String str) {

		if (str == null)
			return false;

		Pattern p = Pattern.compile("\\#\\{(.*?)}");
		Matcher m = p.matcher(str);

		return m.find();
	}

	public static boolean isExists$OrNo(String str) {

		return isExistsNo(str) || isExists$(str);
	}

	public static boolean isOnly$OrNo(String str) {

		if (str == null)
			return false;

		Pattern p = Pattern.compile("\\#\\{(.*?)}|\\$\\{(.*?)}");
		Matcher m = p.matcher(str);

		return m.replaceAll("").length() == 0;
	}

	public static Object getObjValue(Object obj, String property) {

		Object rv = null;

		if (obj != null) {

			try {
				//rv = BeanUtils.getProperty(obj, property);
				rv = PropertyUtils.getProperty(obj, property);
			} catch (IllegalAccessException e) {
				
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				
				e.printStackTrace();
			}
		}

		return rv;
	}

	public static boolean isRichTextStringCell(Cell cell) {

		return cell != null && cell.getRichStringCellValue() != null
				&& cell.getRichStringCellValue().numFormattingRuns() > 0;
	}

	public static List<RichTextModel> getRichTextModel(Cell cell) {

		List<RichTextModel> rlist = new ArrayList<RichTextModel>();

		HSSFRichTextString rich = (HSSFRichTextString) cell.getRichStringCellValue();
		String v = rich.getString();
		int num = rich.numFormattingRuns();

		if (rich.getIndexOfFormattingRun(0) > 0) {

			rlist.add(new RichTextModel(0, cell.getCellStyle().getFontIndex(),
					v.substring(0, rich.getIndexOfFormattingRun(0))));
		}

		for (int i = 0; i < num; i++) {

			int b = rich.getIndexOfFormattingRun(i);

			int e = 0;

			if (i + 1 >= num) {

				e = v.length();
			} else {

				e = rich.getIndexOfFormattingRun(i + 1);
			}

			rlist.add(new RichTextModel(b, rich.getFontOfFormattingRun(i), v.substring(b, e)));
		}

		return rlist;
	}

	private static String getUUID() {

		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
