/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.attence.web;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.pm.modules.attence.entity.Item;
import com.pm.modules.attence.entity.WorkAttence;
import com.pm.modules.attence.entity.WorkItem;
import com.pm.modules.attence.service.ItemService;

/**
 * 个人工日汇总Controller
 * @author yt
 * @version 2018-10-24
 */
@Controller
@RequestMapping(value = "${adminPath}/attence/item")
public class ItemController extends BaseController {
	
	private static final String KC ="1";//考察id
	private static final String PX ="2";//培训id
	private static final String NY ="3";//内业id
	private static final String WY ="4";//设代id
	private static final String DGT ="5";//党工团id
	private static final String LXSW ="6";//零星事务id
	private static final String SJ ="7";//事假id
	private static final String NJ ="8";//年假id
	private static final String BJ ="9";//病假id
	private static final String CW ="10";//财务id
	private static final String SW ="11";//商务id
	private static final String JY ="12";//经营id
	private static final String RCGL ="13";//日常管理id
	private static final String CC ="14";//出差id
	private static final String XX ="15";//学习id
	

	@Autowired
	private ItemService itemService;
	
	@ModelAttribute
	public Item get(@RequestParam(required=false) String id) {
		Item entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = itemService.get(id);
		}
		if (entity == null){
			entity = new Item();
		}
		return entity;
	}
	
	/**
	 * 考勤事项列表页面
	 */
	@RequiresPermissions("attence:item:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/attence/itemList";
	}
	
	/**
	 * 工日统计汇总
	 */
	@RequiresPermissions("attence:item:worklist")
	@RequestMapping(value = {"worklist"})
	public String worklist(WorkItem workItem,Model model) {
		model.addAttribute("workItem", workItem);
		return "modules/attence/workItemList";
	}
	
		/**
	 * 考勤事项列表数据
	 */
	@ResponseBody
	@RequiresPermissions("attence:item:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Item item, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Item> page = itemService.findPage(new Page<Item>(request, response), item); 
		return getBootstrapData(page);
	}
	
	/**
	 * 工日统计汇总列表数据
	 */
	@ResponseBody
	@RequiresPermissions("attence:item:worklist")
	@RequestMapping(value = "workData")
	public Map<String, Object> workData(WorkItem workItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WorkItem> page = itemService.findWorkItem(new Page<WorkItem>(request, response), workItem); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑考勤事项表单页面
	 */
	@RequiresPermissions(value={"attence:item:view","attence:item:add","attence:item:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Item item, Model model) {
		model.addAttribute("item", item);
		return "modules/attence/itemForm";
	}

	/**
	 * 保存考勤事项
	 */
	@ResponseBody
	@RequiresPermissions(value={"attence:item:add","attence:item:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Item item, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, item)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		itemService.save(item);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存考勤事项成功");
		return j;
	}
	
	/**
	 * 删除考勤事项
	 */
	@ResponseBody
	@RequiresPermissions("attence:item:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Item item, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		itemService.delete(item);
		j.setMsg("删除考勤事项成功");
		return j;
	}
	
	/**
	 * 批量删除考勤事项
	 */
	@ResponseBody
	@RequiresPermissions("attence:item:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			itemService.delete(itemService.get(id));
		}
		j.setMsg("删除考勤事项成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("attence:item:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Item item, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "考勤事项"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Item> page = itemService.findPage(new Page<Item>(request, response, -1), item);
    		new ExportExcel("考勤事项", Item.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出考勤事项记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
	
	@ResponseBody
	@RequiresPermissions("attence:item:exportWorkData")
    @RequestMapping(value = "exportWorkData", method=RequestMethod.POST)
    public AjaxJson exportWorkData(WorkItem workItem, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String headStr="";
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		 Calendar cale = Calendar.getInstance();  
	     int year = cale.get(Calendar.YEAR); 
		if (workItem.getWorkDateStart()==null&&workItem.getWorkDateEnd()==null) {
			 headStr="个人工日汇总"+year+"年";
		}else if (workItem.getWorkDateStart()!=null&&workItem.getWorkDateEnd()!=null&&workItem.getWorkDateStart().before(workItem.getWorkDateEnd())) {
			 headStr="个人工日汇总"+sdf.format(workItem.getWorkDateStart())+"月-"+sdf.format(workItem.getWorkDateEnd())+"月";
			
		}else if (workItem.getWorkDateStart()!=null&&workItem.getWorkDateEnd()!=null&&workItem.getWorkDateEnd().before(workItem.getWorkDateStart())) {
			 headStr="个人工日汇总"+sdf.format(workItem.getWorkDateEnd())+"月-"+sdf.format(workItem.getWorkDateStart())+"月";
		}
		else if (workItem.getWorkDateStart()!=null&&workItem.getWorkDateEnd()!=null&&workItem.getWorkDateEnd().getTime()==workItem.getWorkDateStart().getTime()) {
			 headStr="个人工日汇总"+sdf.format(workItem.getWorkDateEnd())+"月";
		
		}else if (workItem.getWorkDateStart()==null&&workItem.getWorkDateEnd()!=null) {
			 headStr="个人工日汇总统计至"+sdf.format(workItem.getWorkDateEnd())+"月";
		} else if (workItem.getWorkDateEnd()==null&&workItem.getWorkDateStart()!=null) {
			 headStr="个人工日汇总统计"+sdf.format(workItem.getWorkDateStart())+"月至今";
		} 
		try {
			String fileName = StringUtils.isBlank(headStr)?"个人工日汇总":headStr+".xls";
			
			//查找个人工日汇总数据
			List<WorkItem> workItemList= itemService.findWorkItem(workItem);

	
			//创建HSSFWorkbook对象(excel的文档对象)  
			HSSFWorkbook workbook = new HSSFWorkbook(); 
			CellRangeAddress biaoti = new CellRangeAddress(0,2,0,27);//起始行,结束行,起始列,结束列
			
			//专业人员
			CellRangeAddress workItem_zyry = new CellRangeAddress(3,4,0,0);//起始行,结束行,起始列,结束列
			//总工时
			CellRangeAddress workItem_zgs = new CellRangeAddress(3,4,1,1);//起始行,结束行,起始列,结束列
			//总加班
			CellRangeAddress workItem_zjb = new CellRangeAddress(3,4,2,2);//起始行,结束行,起始列,结束列


			//总加班工日
			CellRangeAddress workItem_zjbgr = new CellRangeAddress(3,4,3,3);//起始行,结束行,起始列,结束列




			//内业
			CellRangeAddress workItem_ny = new CellRangeAddress(3,3,4,5);//起始行,结束行,起始列,结束列
			//设代
			CellRangeAddress workItem_wy = new CellRangeAddress(3,3,6,7);//起始行,结束行,起始列,结束列
			/*//党工团
			CellRangeAddress workItem_dgt = new CellRangeAddress(3,3,7,8);//起始行,结束行,起始列,结束列
*/			//零星事务
			CellRangeAddress workItem_lxsw = new CellRangeAddress(3,3,8,9);//起始行,结束行,起始列,结束列
			//培训
			CellRangeAddress workItem_px = new CellRangeAddress(3,3,10,11);//起始行,结束行,起始列,结束列
			

			//办公室
			CellRangeAddress workItem_rcgl = new CellRangeAddress(3,3,12,13);//起始行,结束行,起始列,结束列
			//出差
			CellRangeAddress workItem_cc = new CellRangeAddress(3,3,14,15);//起始行,结束行,起始列,结束列
			//学习
			CellRangeAddress workItem_xx = new CellRangeAddress(3,3,16,17);//起始行,结束行,起始列,结束列

			//团支部
			CellRangeAddress workItem_cw = new CellRangeAddress(3,3,18,19);//起始行,结束行,起始列,结束列
			//党支部
			CellRangeAddress workItem_sw = new CellRangeAddress(3,3,20,21);//起始行,结束行,起始列,结束列
			//工会
			CellRangeAddress workItem_jy = new CellRangeAddress(3,3,22,23);//起始行,结束行,起始列,结束列



			//考察
			CellRangeAddress workItem_kc = new CellRangeAddress(3,4,24,24);//起始行,结束行,起始列,结束列
			//年假
			CellRangeAddress workItem_nj = new CellRangeAddress(3,4,25,25);//起始行,结束行,起始列,结束列
			//病假
			CellRangeAddress workItem_bj = new CellRangeAddress(3,4,26,26);//起始行,结束行,起始列,结束列
			//事假
			CellRangeAddress workItem_sj = new CellRangeAddress(3,4,27,27);//起始行,结束行,起始列,结束列
			
			//内容样式
            HSSFCellStyle cellStyle = createCellStyle(workbook,(short)10,true,true);
            
            //大标题
            HSSFCellStyle headStyle = createCellStyle(workbook,(short)20,true,true);
       
            HSSFSheet sheet = workbook.createSheet("个人工日汇总");
            
            //加载合并单元格对象
          	sheet.addMergedRegion(biaoti);
            sheet.addMergedRegion(workItem_zyry);
            sheet.addMergedRegion(workItem_zgs);
            sheet.addMergedRegion(workItem_zjb);
			sheet.addMergedRegion(workItem_zjbgr);
            sheet.addMergedRegion(workItem_ny);
            sheet.addMergedRegion(workItem_wy);
           // sheet.addMergedRegion(workItem_dgt);
            sheet.addMergedRegion(workItem_lxsw);
            sheet.addMergedRegion(workItem_px);
            sheet.addMergedRegion(workItem_kc);

            sheet.addMergedRegion(workItem_rcgl);
            sheet.addMergedRegion(workItem_cc);
            sheet.addMergedRegion(workItem_xx);
			sheet.addMergedRegion(workItem_cw);
			sheet.addMergedRegion(workItem_sw);
			sheet.addMergedRegion(workItem_jy);
            sheet.addMergedRegion(workItem_nj);
            sheet.addMergedRegion(workItem_bj);
            sheet.addMergedRegion(workItem_sj);
            
            //第一行
	        HSSFRow row0 = sheet.createRow(0); 
	        HSSFCell cellHead = row0.createCell(0);
	        cellHead.setCellValue(StringUtils.isBlank(headStr)?"个人工日汇总":headStr);
	        cellHead.setCellStyle(headStyle);
	        HSSFRow row = sheet.createRow(3); 
	        HSSFRow row2 = sheet.createRow(4); 
	        HSSFCell cell_zyry = row.createCell(0);
	        cell_zyry.setCellValue("专业人员");
	        cell_zyry.setCellStyle(cellStyle); 
	        HSSFCell cell_zgs = row.createCell(1);
	        cell_zgs.setCellValue("总工时");
	        cell_zgs.setCellStyle(cellStyle);
	        HSSFCell cell_zjb = row.createCell(2);
	        cell_zjb.setCellValue("总加班(小时)");
	        cell_zjb.setCellStyle(cellStyle);

			HSSFCell cell_zjbgr = row.createCell(3);
			cell_zjbgr.setCellValue("折算总工日");
			cell_zjbgr.setCellStyle(cellStyle);

	        	        	 
	        HSSFCell cell_ny = row.createCell(4);
	        cell_ny.setCellValue("内业");
	        cell_ny.setCellStyle(cellStyle);
	        HSSFCell cell_ny_gr = row2.createCell(4);
	        HSSFCell cell_ny_jb = row2.createCell(5);
	        cell_ny_gr.setCellValue("工日");
	        cell_ny_gr.setCellStyle(cellStyle);
	        cell_ny_jb.setCellValue("加班");
	        cell_ny_jb.setCellStyle(cellStyle);
        	
	        HSSFCell cell_wy = row.createCell(6);
	        cell_wy.setCellValue("设代");
	        cell_wy.setCellStyle(cellStyle);
	        HSSFCell cell_wy_gr = row2.createCell(6);
	        HSSFCell cell_wy_jb = row2.createCell(7);
	        cell_wy_gr.setCellValue("工日");
	        cell_wy_gr.setCellStyle(cellStyle);
	        cell_wy_jb.setCellValue("加班");
	        cell_wy_jb.setCellStyle(cellStyle);
	        
	   /*     HSSFCell cell_dgt = row.createCell(7);
	        cell_dgt.setCellValue("党工团");
	        cell_dgt.setCellStyle(cellStyle);
	        HSSFCell cell_dgt_gr = row2.createCell(7);
	        HSSFCell cell_dgt_jb = row2.createCell(8);
	        cell_dgt_gr.setCellValue("工日");
	        cell_dgt_gr.setCellStyle(cellStyle);
	        cell_dgt_jb.setCellValue("加班");
	        cell_dgt_jb.setCellStyle(cellStyle);*/
	        
	        HSSFCell cell_lxsw = row.createCell(8);
	        cell_lxsw.setCellValue("零星事务");
	        cell_lxsw.setCellStyle(cellStyle);
	        HSSFCell cell_lxsw_gr = row2.createCell(8);
	        HSSFCell cell_lxsw_jb = row2.createCell(9);
	        cell_lxsw_gr.setCellValue("工日");
	        cell_lxsw_gr.setCellStyle(cellStyle);
	        cell_lxsw_jb.setCellValue("加班");
	        cell_lxsw_jb.setCellStyle(cellStyle);
	        
	        
	        
	        
	      
	        HSSFCell cell_px = row.createCell(10);
	        cell_px.setCellValue("培训事务");
	        cell_px.setCellStyle(cellStyle);
	        HSSFCell cell_px_gr = row2.createCell(10);
	        HSSFCell cell_px_jb = row2.createCell(11);
	        cell_px_gr.setCellValue("工日");
	        cell_px_gr.setCellStyle(cellStyle);
	        cell_px_jb.setCellValue("加班");
	        cell_px_jb.setCellStyle(cellStyle);
	        
	        
	       


	        
	        HSSFCell cell_rcgl = row.createCell(12);
	        cell_rcgl.setCellValue("办公室");
	        cell_rcgl.setCellStyle(cellStyle);
	        HSSFCell cell_rcgl_gr = row2.createCell(12);
	        HSSFCell cell_rcgl_jb = row2.createCell(13);
	        cell_rcgl_gr.setCellValue("工日");
	        cell_rcgl_gr.setCellStyle(cellStyle);
	        cell_rcgl_jb.setCellValue("加班");
	        cell_rcgl_jb.setCellStyle(cellStyle);
	        
	        HSSFCell cell_cc = row.createCell(14);
	        cell_cc.setCellValue("出差");
	        cell_cc.setCellStyle(cellStyle);
	        HSSFCell cell_cc_gr = row2.createCell(14);
	        HSSFCell cell_cc_jb = row2.createCell(15);
	        cell_cc_gr.setCellValue("工日");
	        cell_cc_gr.setCellStyle(cellStyle);
	        cell_cc_jb.setCellValue("加班");
	        cell_cc_jb.setCellStyle(cellStyle);
	        
	        HSSFCell cell_xx = row.createCell(16);
	        cell_xx.setCellValue("学习");
	        cell_xx.setCellStyle(cellStyle);
	        HSSFCell cell_xx_gr = row2.createCell(16);
	        HSSFCell cell_xx_jb = row2.createCell(17);
	        cell_xx_gr.setCellValue("工日");
	        cell_xx_gr.setCellStyle(cellStyle);
	        cell_xx_jb.setCellValue("加班");
	        cell_xx_jb.setCellStyle(cellStyle);

			HSSFCell cell_cw = row.createCell(18);
			cell_cw.setCellValue("其他1");
			cell_cw.setCellStyle(cellStyle);
			HSSFCell cell_cw_gr = row2.createCell(18);
			HSSFCell cell_cw_jb = row2.createCell(19);
			cell_cw_gr.setCellValue("工日");
			cell_cw_gr.setCellStyle(cellStyle);
			cell_cw_jb.setCellValue("加班");
			cell_cw_jb.setCellStyle(cellStyle);


			HSSFCell cell_sw = row.createCell(20);
			cell_sw.setCellValue("其他2");
			cell_sw.setCellStyle(cellStyle);
			HSSFCell cell_sw_gr = row2.createCell(20);
			HSSFCell cell_sw_jb = row2.createCell(21);
			cell_sw_gr.setCellValue("工日");
			cell_sw_gr.setCellStyle(cellStyle);
			cell_sw_jb.setCellValue("加班");
			cell_sw_jb.setCellStyle(cellStyle);

			HSSFCell cell_jy = row.createCell(22);
			cell_jy.setCellValue("其他2");
			cell_jy.setCellStyle(cellStyle);
			HSSFCell cell_jy_gr = row2.createCell(22);
			HSSFCell cell_jy_jb = row2.createCell(23);
			cell_jy_gr.setCellValue("工日");
			cell_jy_gr.setCellStyle(cellStyle);
			cell_jy_jb.setCellValue("加班");
			cell_jy_jb.setCellStyle(cellStyle);
	        
	        
	        HSSFCell cell_kc = row.createCell(24);
	        cell_kc.setCellValue("考察");
	        cell_kc.setCellStyle(cellStyle);
	        
	        HSSFCell cell_nj = row.createCell(25);
	        cell_nj.setCellValue("年假");
	        cell_nj.setCellStyle(cellStyle);
	        HSSFCell cell_bj = row.createCell(26);
	        cell_bj.setCellValue("病假");
	        cell_bj.setCellStyle(cellStyle);
	        HSSFCell cell_sj = row.createCell(27);
	        cell_sj.setCellValue("事假");
	        cell_sj.setCellStyle(cellStyle);
	        
	        for(int i=0; i<workItemList.size(); i++){
	        	WorkItem workItemObj = workItemList.get(i);
	        	workItemObj.setWorkDateStart(workItem.getWorkDateStart());
	        	workItemObj.setWorkDateEnd(workItem.getWorkDateEnd());
	        	HSSFRow row3 = sheet.createRow(5+i);
	        	//专业人员
                HSSFCell cell0 = row3.createCell(0);
                cell0.setCellValue(workItemObj.getUser().getName());
                
                //总工日
                HSSFCell cell1 = row3.createCell(1);
                cell1.setCellValue((double)workItemObj.getSumWorkTime());
                
                //总加班(小时)
                HSSFCell cell2 = row3.createCell(2);
                cell2.setCellValue((double)workItemObj.getSumOverTime());


				//总加班工日
				HSSFCell cellgr = row3.createCell(3);
				cellgr.setCellValue((double)workItemObj.getOverTimeDay());
                
                //业内工日
                workItemObj.setItemType(NY);
                HSSFCell cell3 = row3.createCell(4);
                cell3.setCellValue((double)getWorkAttence(workItemObj).getWorkTime());
                //业内加班
                HSSFCell cell4 = row3.createCell(5);
                cell4.setCellValue((double)getWorkAttence(workItemObj).getOverTime());
                
                //设代工日
                workItemObj.setItemType(WY);
                HSSFCell cell5 = row3.createCell(6);
                cell5.setCellValue((double)getWorkAttence(workItemObj).getWorkTime());
                //设代加班
                HSSFCell cell6 = row3.createCell(7);
                cell6.setCellValue((double)getWorkAttence(workItemObj).getOverTime());
             /*   
                //党工团工日
                workItemObj.setItemType(DGT);
                HSSFCell cell7 = row3.createCell(7);
                cell7.setCellValue((double)getWorkAttence(workItemObj).getWorkTime());
                //党工团加班
                HSSFCell cell8 = row3.createCell(8);
                cell8.setCellValue((double)getWorkAttence(workItemObj).getOverTime());
                */
                //零星事务工日
                workItemObj.setItemType(LXSW);
                HSSFCell cell7 = row3.createCell(8);
                cell7.setCellValue((double)getWorkAttence(workItemObj).getWorkTime());
                //零星事务加班
                HSSFCell cell8 = row3.createCell(9);
                cell8.setCellValue((double)getWorkAttence(workItemObj).getOverTime());
                
                //培训工日
                workItemObj.setItemType(PX);
                HSSFCell cell9 = row3.createCell(10);
                cell9.setCellValue((double)getWorkAttence(workItemObj).getWorkTime());
                HSSFCell cell10 = row3.createCell(11);
                cell10.setCellValue((double)getWorkAttence(workItemObj).getOverTime());
              
                

                
                //办公室
                workItemObj.setItemType(RCGL);
                HSSFCell cell17 = row3.createCell(12);
                cell17.setCellValue((double)getWorkAttence(workItemObj).getWorkTime());
                //日常管理加班
                HSSFCell cell18 = row3.createCell(13);
                cell18.setCellValue((double)getWorkAttence(workItemObj).getOverTime());
                
                //出差工日
                workItemObj.setItemType(CC);
                HSSFCell cell19 = row3.createCell(14);
                cell19.setCellValue((double)getWorkAttence(workItemObj).getWorkTime());
                //出差加班
                HSSFCell cell20 = row3.createCell(15);
                cell20.setCellValue((double)getWorkAttence(workItemObj).getOverTime());
                
                //学习工日
                workItemObj.setItemType(XX);
                HSSFCell cell21 = row3.createCell(16);
                cell21.setCellValue((double)getWorkAttence(workItemObj).getWorkTime());
                //学习加班
                HSSFCell cell22 = row3.createCell(17);
                cell22.setCellValue((double)getWorkAttence(workItemObj).getOverTime());

				//其他1
				workItemObj.setItemType(CW);
				HSSFCell cell11 = row3.createCell(18);
				cell11.setCellValue((double)getWorkAttence(workItemObj).getWorkTime());
				//其他1加班
				HSSFCell cell12 = row3.createCell(19);
				cell12.setCellValue((double)getWorkAttence(workItemObj).getOverTime());

				//其他2
				workItemObj.setItemType(SW);
				HSSFCell cell13 = row3.createCell(20);
				cell13.setCellValue((double)getWorkAttence(workItemObj).getWorkTime());
				//其他2加班
				HSSFCell cell14 = row3.createCell(21);
				cell14.setCellValue((double)getWorkAttence(workItemObj).getOverTime());

				//其他3
				workItemObj.setItemType(JY);
				HSSFCell cell15 = row3.createCell(22);
				cell15.setCellValue((double)getWorkAttence(workItemObj).getWorkTime());
				//其他3加班
				HSSFCell cell16 = row3.createCell(23);
				cell16.setCellValue((double)getWorkAttence(workItemObj).getOverTime());



                
                //考察工日
                workItemObj.setItemType(KC);
                HSSFCell cell23 = row3.createCell(24);
                cell23.setCellValue((double)getWorkAttence(workItemObj).getWorkTime());
                //年假
                workItemObj.setItemType(NJ);
                HSSFCell cell24 = row3.createCell(25);
                cell24.setCellValue((double)getWorkAttence(workItemObj).getWorkTime());
                //病假
                workItemObj.setItemType(BJ);
                HSSFCell cell25 = row3.createCell(26);
                cell25.setCellValue((double)getWorkAttence(workItemObj).getWorkTime());
                //事假
                workItemObj.setItemType(SJ);
                HSSFCell cell26 = row3.createCell(27);
                cell26.setCellValue((double)getWorkAttence(workItemObj).getWorkTime());
	        }
    	    //输出Excel文件  
    	    OutputStream output=response.getOutputStream();
			response.reset();
	        response.setContentType("application/octet-stream; charset=utf-8");
	        //20180424修改：输出文件名乱码的处理
	        response.setHeader("Content-Disposition", "attachment; filename="+ new String(fileName.getBytes(),"iso-8859-1")); 
	        workbook.write(output);
    	    output.close();  
    		j.setSuccess(true);
    		j.setMsg("导出个人工日汇总成功！");
    		return j;
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("导出个人工日汇总记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
	
	
	/**
	 * 得到相应考勤事项的天数
	 * @param workItemObj
	 * @return
	 */
	private WorkAttence getWorkAttence(WorkItem workItemObj) {
		WorkAttence workAttence = new WorkAttence();
		WorkItem workItem = itemService.getWorkItem(workItemObj);//得到相应考勤事项的天数
		if(workItem == null){
			workAttence.setWorkTime(0D);
			workAttence.setOverTime(0D);
		}else{
			workAttence.setWorkTime(workItem.getSumWorkTime());
			workAttence.setOverTime(workItem.getSumOverTime());
		}
		return workAttence;
		
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
	
	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("attence:item:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Item> list = ei.getDataList(Item.class);
			for (Item item : list){
				try{
					itemService.save(item);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条考勤事项记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条考勤事项记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入考勤事项失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/attence/item/?repage";
    }
	
	/**
	 * 下载导入考勤事项数据模板
	 */
	@RequiresPermissions("attence:item:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "考勤事项数据导入模板.xlsx";
    		List<Item> list = Lists.newArrayList(); 
    		new ExportExcel("考勤事项数据", Item.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/attence/item/?repage";
    }
	
	
	
	@ResponseBody
	@RequestMapping(value = "allItem")
	public List<Item> allItem(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Item>    listItem =itemService.findList(new Item());
		return listItem;
	}
	
	
	
	

}