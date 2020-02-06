package com.jeeplus.common.utils.xls;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.springframework.core.io.ClassPathResource;

import com.jeeplus.common.utils.xls.usermodel.DataMargin;
import com.jeeplus.common.utils.xls.usermodel.FileldConverter;
import com.jeeplus.common.utils.xls.usermodel.LoopArea;
import com.jeeplus.common.utils.xls.usermodel.LoopDataModel;
import com.jeeplus.common.utils.xls.usermodel.PmsPicture;
import com.jeeplus.common.utils.xls.usermodel.RichTextModel;
import com.jeeplus.common.utils.xls.usermodel.TemplateSheet;

public class ExcelExp {
	// 模版工作薄，在构造ExcelExp对象时，初始化一次
	private Workbook wb_t;

	// 模版sheet， 在调用绘制模版时进行初始化
	private Sheet sheet_t;

	private int rownum = 0;

	// 实际正在处理的数据对应的sheet
	private Sheet sheet;

	// 需要合并的区域
	private Map<Integer, List<CellRangeAddress>> cellRangeAddressMap = new HashMap<Integer, List<CellRangeAddress>>();

	// 所有的模版名称，主要用于调用删除模版时使用
	private Set<String> templateNames = new HashSet<String>();

	// 创建帮助
	private CreationHelper helper = null;

	// 默认保护sheet密码
	private String protectPwd = "njgs2018";

	/**
	 * 构造Excel导出对象
	 * 
	 * @param templateFile
	 *            模版文件路径名称
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ExcelExp(String templateFile) throws EncryptedDocumentException, InvalidFormatException, IOException {

		ClassPathResource classPathResource = new ClassPathResource(templateFile);

		wb_t = WorkbookFactory.create(classPathResource.getInputStream());
		helper = wb_t.getCreationHelper();
	}

	public ExcelExp(String templateFile, String protectPwd)
			throws EncryptedDocumentException, InvalidFormatException, IOException {

		this.protectPwd = protectPwd;

		ClassPathResource classPathResource = new ClassPathResource(templateFile);
		wb_t = WorkbookFactory.create(classPathResource.getInputStream());
		helper = wb_t.getCreationHelper();
	}

	/**
	 * 绘制工作表
	 * 
	 * @param templateSheet
	 *            模版信息
	 * @return
	 * @throws Exception
	 */
	public Workbook draw(TemplateSheet templateSheet) throws Exception {

		sheet_t = wb_t.getSheetAt(templateSheet.getSheetAt());

		templateNames.add(sheet_t.getSheetName());

		cellRangeAddressMap.clear();

		// 初始化模版中合并的区域， 以结束的行号作为键值
		List<CellRangeAddress> cellRangeAddresss = new ArrayList<CellRangeAddress>();

		for (int i = 0; i < sheet_t.getNumMergedRegions(); i++) {

			cellRangeAddresss.add(sheet_t.getMergedRegion(i));
		}

		for (CellRangeAddress cellRangeAddress : cellRangeAddresss) {

			List<CellRangeAddress> list = cellRangeAddressMap.get(cellRangeAddress.getLastRow());
			if (list == null) {

				list = new ArrayList<CellRangeAddress>();
				cellRangeAddressMap.put(cellRangeAddress.getLastRow(), list);
			}

			list.add(cellRangeAddress);
		}

		draw_(templateSheet);

		return wb_t;
	}

	/**
	 * 删除模版
	 */
	public void deleteTemplate() {

		for (String templateName : templateNames) {

			wb_t.removeSheetAt(wb_t.getSheetIndex(templateName));
		}
	}

	/**
	 * 绘制表格
	 * 
	 * @param templateSheet
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void draw_(TemplateSheet templateSheet) throws FileNotFoundException, IOException {

		String _sheetName = "";

		if (templateSheet.isFirstSheet()) {

			_sheetName = templateSheet.getSheetName();
		} else {

			// 判断是否是分页的非第一个sheet时，修改第一个sheet的名称
			if (templateSheet.getSheetNo() == 2) {

				wb_t.setSheetName(wb_t.getSheetIndex(sheet), templateSheet.getSheetName() + "(1)");
			}

			_sheetName = templateSheet.getSheetName() + "(" + templateSheet.getSheetNo() + ")";
		}

		sheet = wb_t.createSheet();
		wb_t.setSheetName(wb_t.getSheetIndex(sheet), _sheetName);

		// 设置宽度, 高度不需要设置已经直接在CellStyle中包含高度
		setColumnWidth(sheet_t, sheet);

		if (sheet_t.getProtect()) {

			sheet.protectSheet(protectPwd);
		}

		rownum = sheet_t.getFirstRowNum();

		for (int i = sheet_t.getFirstRowNum(); i <= sheet_t.getLastRowNum(); i++) {

			// 获取模版中是否存在需要循环的行信息
			LoopArea loopArea = getLoopAreaWithStartRow(templateSheet.getLoopAreas(), i);

			if (loopArea != null) {

				LoopDataModel loopDatas = loopArea.getLoopDataModel();

				// 循环数据不为空
				if (loopDatas != null && loopDatas.hasNext()) {

					// 获取模版的行对象
					Row[] row_ts = new Row[loopArea.getRowEnd() - loopArea.getRowStart() + 1];
					for (int rt = loopArea.getRowStart(); rt <= loopArea.getRowEnd(); rt++) {

						row_ts[rt - loopArea.getRowStart()] = sheet_t.getRow(rt);
					}

					while (loopDatas.hasNext()) {

						Object loopData = loopDatas.next();

						for (int j = 0; j < row_ts.length; j++) {

							Row row = sheet.createRow(rownum++);
							setRowValue(row_ts[j], row, templateSheet, loopArea, loopData);
						}

						// 分页判断，是否大于最大行
						if (templateSheet.getMaxRow() > 0 && templateSheet.getMaxRow() < rownum
								&& loopDatas.hasNext()) {

							// 初始化生成新的工作表，默认带整个表头信息
							templateSheet.setSheetNo(templateSheet.getSheetNo() + 1);
							draw_(templateSheet);

							// 合并数据
							sheetMargins4Data(sheet, loopArea.getDataMargins(), i, rownum - 1);
							return;
						}
					}

					// 合并数据
					sheetMargins4Data(sheet, loopArea.getDataMargins(), i, rownum - 1);
				}

				// 模版跳过循环区域继续处理
				i += loopArea.getRowEnd() - loopArea.getRowStart();
			} else {

				// 基础数据处理
				Row row_t = sheet_t.getRow(i);

				Row row = sheet.createRow(rownum++);

				if (row_t != null) {

					row.setHeight(row_t.getHeight());

					setRowValue(row_t, row, templateSheet, loopArea, null);
				}
			}
		}

		/**
		 * 自定义图片的插入简单实现，后期可以针对图片根据单元格大小进行不同的展示 参考：
		 * https://svn.apache.org/repos/asf/poi/trunk/src/examples/src/org/apache/poi/hssf/usermodel/examples/AddDimensionedImage.java
		 */
		if (templateSheet.getPicture() != null && templateSheet.getPicture().length > 0) {

			Drawing drawing = sheet.createDrawingPatriarch();
			ClientAnchor anchor = helper.createClientAnchor();

			for (PmsPicture picture : templateSheet.getPicture()) {

				int pictureIdx = wb_t.addPicture(IOUtils.toByteArray(new FileInputStream(picture.getImgPath())),
						picture.getPictureType());

				anchor.setRow1(picture.getBeginRow());
				anchor.setCol1(picture.getBeginCell());
				anchor.setDx1(picture.getBeginDx());
				anchor.setDy1(picture.getBeginDy());

				anchor.setRow2(picture.getEndRow());
				anchor.setCol2(picture.getEndCell());
				anchor.setDx2(picture.getEndDx());
				anchor.setDy2(picture.getEndDy());

				Picture p = drawing.createPicture(anchor, pictureIdx);

				if (picture.isResize()) {

					p.resize();
				}
			}
		}

		sheet.setMargin(Sheet.BottomMargin, sheet_t.getMargin(Sheet.BottomMargin)); // 页边距（下）
		sheet.setMargin(Sheet.LeftMargin, sheet_t.getMargin(Sheet.LeftMargin)); // 页边距（左）
		sheet.setMargin(Sheet.RightMargin, sheet_t.getMargin(Sheet.RightMargin)); // 页边距（右）
		sheet.setMargin(Sheet.TopMargin, sheet_t.getMargin(Sheet.TopMargin)); // 页边距（上）
		sheet.setMargin(Sheet.HeaderMargin, sheet_t.getMargin(Sheet.HeaderMargin));// 页眉
		sheet.setMargin(Sheet.FooterMargin, sheet_t.getMargin(Sheet.FooterMargin));// 页脚
	}

	/**
	 * 设置列宽度
	 * 
	 * @param templateSheet
	 * @param dataSheet
	 */
	private void setColumnWidth(Sheet templateSheet, Sheet dataSheet) {

		Row row = templateSheet.getRow(0);

		for (int i = 0; i <= row.getLastCellNum(); i++) {

			if (templateSheet.isColumnHidden(i)) {

				dataSheet.setColumnHidden(i, true);
			} else {

				dataSheet.setColumnWidth(i, templateSheet.getColumnWidth(i));
			}
		}
	}

	/**
	 * 根据模版行查找是否是循环处理对象
	 * 
	 * @param loopAreas
	 * @param rownum
	 * @return
	 */
	private LoopArea getLoopAreaWithStartRow(LoopArea[] loopAreas, int rownum) {

		LoopArea loopArea = null;

		if (loopAreas == null)
			return null;

		for (LoopArea l : loopAreas) {

			if (l.getRowStart() == rownum) {

				loopArea = l;
				break;
			}
		}

		return loopArea;
	}

	/**
	 * 设置单元格值 注意：如果需要扩展字段类型，可以在该处增加判断，可以在TemplateSheet类，何Loop类中增加字段类型映射，不可写死
	 * 
	 * @param templateRow
	 * @param dataRow
	 * @param templateSheet
	 * @param loopArea
	 * @param loopData
	 */
	private void setRowValue(Row templateRow, Row dataRow, TemplateSheet templateSheet, LoopArea loopArea,
			Object loopData) {

		for (int j = templateRow.getFirstCellNum(); j <= templateRow.getLastCellNum(); j++) {

			Cell templateCell = templateRow.getCell(j);

			if (templateCell != null) {

				Cell dataCell = dataRow.createCell(j);
				dataCell.setCellStyle(templateCell.getCellStyle());
				dataCell.setCellType(templateCell.getCellType());

				if (templateCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {

					dataCell.setCellValue(templateCell.getNumericCellValue());
				} else if (templateCell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {

					String cellFormula = templateCell.getCellFormula();
					dataCell.setCellFormula(cellFormula);
				} else {

					String templateCellValue = templateCell.getStringCellValue();

					// 增加对单元格中字符串带有特定格式的额外处理

					if (ExcelExpUtil.isExists$OrNo(templateCellValue)) {

						if (ExcelExpUtil.isRichTextStringCell(templateCell)
								&& !ExcelExpUtil.isOnly$OrNo(templateCellValue)) {

							// RichText的链表结构数据
							List<RichTextModel> rlist = ExcelExpUtil.getRichTextModel(templateCell);

							String ov = "";

							for (RichTextModel r : rlist) {

								String v = getCellValue(templateCell, dataCell, templateSheet, loopArea, loopData,
										r.getMoban()).toString();

								ov += v;
								r.setText(v);
							}

							HSSFRichTextString newRich = new HSSFRichTextString(ov);

							int b = 0;

							for (RichTextModel r : rlist) {

								newRich.applyFont(b, b + r.getText().length(), r.getFont());
								b += r.getText().length();
							}

							dataCell.setCellValue(newRich);
						} else {

							Object obj = getCellValue(templateCell, dataCell, templateSheet, loopArea, loopData);

							if (obj instanceof Date) {

								dataCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
								dataCell.setCellValue((Date) obj);
							} else {

								dataCell.setCellValue(obj.toString());
							}
						}
					} else {

						if (ExcelExpUtil.isRichTextStringCell(templateCell)) {

							dataCell.setCellValue(templateCell.getRichStringCellValue());
						} else {

							dataCell.setCellValue(templateCellValue);
						}
					}
				}
			}
		}

		// 合并单元格
		mergedRegion(templateRow, dataRow);
	}

	/**
	 * 获取Cell的Value值
	 * 
	 * @param templateCell
	 * @param dataCell
	 * @param templateSheet
	 * @param loopArea
	 * @param loopData
	 * @return
	 */
	private Object getCellValue(Cell templateCell, Cell dataCell, TemplateSheet templateSheet, LoopArea loopArea,
			Object loopData) {

		String filedValue = templateCell.getStringCellValue();

		return getCellValue(templateCell, dataCell, templateSheet, loopArea, loopData, filedValue);
	}

	/**
	 * 获取Cell的Value值
	 * 
	 * @param templateCell
	 * @param dataCell
	 * @param templateSheet
	 * @param loopArea
	 * @param loopData
	 * @return
	 */
	private Object getCellValue(Cell templateCell, Cell dataCell, TemplateSheet templateSheet, LoopArea loopArea,
			Object loopData, String filedValue) {

		if (ExcelExpUtil.isOnly$OrNo(filedValue)) {

		}

		// 处理主体内容
		filedValue = dealSubjectString(templateCell, dataCell, filedValue, templateSheet, loopArea, loopData);

		// 处理循环体内容
		filedValue = dealLoopString(templateCell, dataCell, filedValue, templateSheet, loopArea, loopData);

		return filedValue;
	}

	// 处理主体内容
	private String dealSubjectString(Cell templateCell, Cell dataCell, String filedValue, TemplateSheet templateSheet,
			LoopArea loopArea, Object loopData) {

		Pattern p = Pattern.compile("\\#\\{(.*?)}");
		Matcher m = p.matcher(filedValue);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {

			String property = m.group(1);

			m.appendReplacement(sb, getObjValue(templateCell, dataCell, property, templateSheet.getSubject(),
					templateSheet.getFileldConverters()).replaceAll("\\$", "\\\\\\$"));
		}
		m.appendTail(sb);

		return sb.toString();
	}

	// 处理循环体内容
	private String dealLoopString(Cell templateCell, Cell dataCell, String filedValue, TemplateSheet templateSheet,
			LoopArea loopArea, Object loopData) {

		Pattern p = Pattern.compile("\\$\\{(.*?)}");
		Matcher m = p.matcher(filedValue);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {

			String property = m.group(1);

			m.appendReplacement(sb, getObjValue(templateCell, dataCell, property, loopData,
					loopArea == null ? null : loopArea.getFileldConverters()).replaceAll("\\$", "\\\\\\$"));
		}
		m.appendTail(sb);

		return sb.toString();
	}

	private String getObjValue(Cell templateCell, Cell dataCell, String property, Object obj,
			Map<String, FileldConverter> fileldConverters) {

		FileldConverter fileldConverter = fileldConverters == null ? null : fileldConverters.get(property);

		String objValue = null;

		if (null != fileldConverter) {

			objValue = fileldConverter.convert(templateCell, dataCell, property, obj);
		} else {

			Object o = ExcelExpUtil.getObjValue(obj, property);
			if (o != null) {

				objValue = o.toString();
			}
		}

		if (objValue == null) {

			objValue = "";
		}

		return objValue;
	}

	/**
	 * 根据模版合并单元格， 注意合并的规则使用合并的cellRangeAddressMap的键值为结束行，因为只有已经生成的单元格才可以进行合并
	 * 
	 * @param templateRow
	 * @param dataRow
	 */
	private void mergedRegion(Row templateRow, Row dataRow) {

		List<CellRangeAddress> cellRangeAddresss = cellRangeAddressMap.get(templateRow.getRowNum());

		if (cellRangeAddresss != null && !cellRangeAddresss.isEmpty()) {

			for (CellRangeAddress cellRangeAddress : cellRangeAddresss) {

				CellRangeAddress ca = new CellRangeAddress(

						dataRow.getRowNum() - (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()),
						dataRow.getRowNum(), cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn());

				sheet.addMergedRegion(ca);
			}
		}
	}

	/**
	 * 数据合并单元格
	 * 
	 * @param sheet
	 * @param dataMargins
	 * @param startRownum
	 * @param endRownum
	 */
	private void sheetMargins4Data(Sheet sheet, DataMargin[] dataMargins, int startRownum, int endRownum) {

		if (startRownum == endRownum || dataMargins == null || dataMargins.length == 0)
			return;

		for (DataMargin dataMargin : dataMargins) {

			sheetMargin4Data_(sheet, dataMargin, startRownum, endRownum);
		}
	}

	private void sheetMargin4Data_(Sheet sheet, DataMargin dataMargin, int startRownum, int endRownum) {

		if (startRownum == endRownum)
			return;

		int b = startRownum;

		for (int i = startRownum + 1; i <= endRownum; i++) {

			if (i == 64) {

				System.out.println(i);
			}

			if (dataMargin.getDataMrginCompare().compare(
					sheet.getRow(b).getCell(dataMargin.getCellNo()).getStringCellValue(),
					sheet.getRow(i).getCell(dataMargin.getCellNo()).getStringCellValue())) {

				// 最后一行特殊处理,直接进行合并
				if (i == endRownum) {

					CellRangeAddress cellRangeAddress = new CellRangeAddress(b, i, dataMargin.getCellNo(),
							dataMargin.getCellNo());
					sheet.addMergedRegion(cellRangeAddress);
					sheetMargins4Data(sheet, dataMargin.getChildDataMargins(), b, i);
				}

				continue;
			} else {

				if (i - b > 1) {

					CellRangeAddress cellRangeAddress = new CellRangeAddress(b, i - 1, dataMargin.getCellNo(),
							dataMargin.getCellNo());
					sheet.addMergedRegion(cellRangeAddress);

					sheetMargins4Data(sheet, dataMargin.getChildDataMargins(), b, i - 1);
				}
				b = i;
			}
		}
	}
}
