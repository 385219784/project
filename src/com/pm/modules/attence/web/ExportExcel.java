package com.pm.modules.attence.web;


import java.io.FileOutputStream;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.jeeplus.common.utils.DateUtils;
import com.pm.modules.attence.entity.ProWorkSum;
import com.pm.modules.attence.entity.WorkSheetDetail;
import com.pm.modules.project.entity.Specialty;
public class ExportExcel {
	
	
	
	public void getValue(List<WorkSheetDetail> userList,FileOutputStream fout){
		  try{
			  String fileName = "项目工日统计"+DateUtils.getDate("yyyyMMdd")+".xls";
				//所有在用的专业
				//List<Specialty>   specList=specialtyService.findAllSpecialtyList();
				
				//List<ProWorkSum>  resultList=workAttenceService.findProWorkNumList(proWorkSum);
		
				//创建HSSFWorkbook对象(excel的文档对象)  
				HSSFWorkbook workbook = new HSSFWorkbook(); 
				//项目编号
				 CellRangeAddress callRange_bh = new CellRangeAddress(0,1,0,0);//起始行,结束行,起始列,结束列
				// 项目名称
		          CellRangeAddress callRange_mc = new CellRangeAddress(0,1,1,1);//起始行,结束行,起始列,结束列
				
		       // 设计类别
		          CellRangeAddress callRange_sjlb = new CellRangeAddress(0,1,2,2);//起始行,结束行,起始列,结束列
		       // 项目负责人
		          CellRangeAddress callRange_fzr = new CellRangeAddress(0,1,3,3);//起始行,结束行,起始列,结束列
		       // 项目主管
		          CellRangeAddress callRange_zg = new CellRangeAddress(0,1,4,4);//起始行,结束行,起始列,结束列
		          // 总工日
		          CellRangeAddress callRange_zgr = new CellRangeAddress(0,1,5,5);//起始行,结束行,起始列,结束列
		          // 总加班小时
		          CellRangeAddress callRange_jbxs = new CellRangeAddress(0,1,6,6);//起始行,结束行,起始列,结束列
				
		          HSSFSheet sheet = workbook.createSheet("项目工日统计");
		            //2.1加载合并单元格对象
		            sheet.addMergedRegion(callRange_bh);
		            sheet.addMergedRegion(callRange_mc);
		            sheet.addMergedRegion(callRange_sjlb);
		            sheet.addMergedRegion(callRange_fzr);
		            sheet.addMergedRegion(callRange_zg);
		            sheet.addMergedRegion(callRange_zgr);
		            sheet.addMergedRegion(callRange_jbxs);
		            int  i=2;
		         for(int j=0;j<10;j++) {
		        	 sheet.addMergedRegion (new CellRangeAddress(0,0,6+i-1,6+i));
		        	
		        	 i=i+2;
		         }
		         
		         //第一行
		         HSSFRow row = sheet.createRow(0); 
		         HSSFRow row2 = sheet.createRow(1); 
		         HSSFCell cell = row.createCell(0);
		         cell.setCellValue("项目编号");
		         HSSFCell cell_bh = row.createCell(1);
		         cell_bh.setCellValue("项目名称");
		         HSSFCell cell_sjlb = row.createCell(2);
		         cell_sjlb.setCellValue("设计类别");
		         HSSFCell cell_fzr = row.createCell(3);
		         cell_fzr.setCellValue("项目负责人");
		         
		         HSSFCell cell_xmzg = row.createCell(4);
		         cell_xmzg.setCellValue("项目主管");
		         HSSFCell cell_zgr = row.createCell(5);
		         cell_zgr.setCellValue("总工日");
		         HSSFCell cell_zjbxs = row.createCell(6);
		         cell_zjbxs.setCellValue("总加班小时");
		         
		         int z=0;
		         for(int j=0;j<10;j++) {
		        	 HSSFCell cell1 = row.createCell(7+z);
		        	 cell1.setCellValue("路面");
		        	 HSSFCell cell2 = row2.createCell(7+z);
		        	 HSSFCell cell3 = row2.createCell(8+z);
		        		 cell2.setCellValue("工日");
		        		 cell3.setCellValue("加班"); 
		        	 z=z+2;
		         }
		         
		         
		         
		         HSSFCellStyle erStyle = createCellStyle(workbook,(short)13,true,true);
		         
	            workbook.write(fout);
//	            workbook.close();
	            //out.close();
	        }catch(Exception e)
	        {
	            e.printStackTrace();
	        }
	}
	
    /**
     * 
     * @param workbook
     * @param fontsize
     * @return 单元格样式
     */
    private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontsize,boolean flag,boolean flag1) {
        // TODO Auto-generated method stub
        HSSFCellStyle style = workbook.createCellStyle();
        //是否水平居中
        if(flag1){
        	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        }
       
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        //创建字体
        HSSFFont font = workbook.createFont();
        //是否加粗字体
        if(flag){
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        font.setFontHeightInPoints(fontsize);
        //加载字体
        style.setFont(font);
        return style;
    }
}
