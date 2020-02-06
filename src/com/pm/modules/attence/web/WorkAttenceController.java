/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.attence.web;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.math.MathUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.h2.util.New;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.jeeplus.modules.iim.entity.MyCalendar;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.DictUtils;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.pm.modules.attence.entity.ProWorkSum;
import com.pm.modules.attence.entity.WorkAttence;
import com.pm.modules.attence.entity.WorkItem;
import com.pm.modules.attence.service.WorkAttenceService;
import com.pm.modules.project.entity.Specialty;
import com.pm.modules.project.service.ProjectService;
import com.pm.modules.project.service.SpecialtyService;

/**
 * 项目工日Controller
 * @author scc
 * @version 2018-10-15
 */
@Controller
@RequestMapping(value = "${adminPath}/attence/workAttence")
public class WorkAttenceController extends BaseController {
	
	private static String kc="1";
	private static String px ="2";
	private static String ny ="3";
	private static String wy ="4";
	private static String dgt ="5";
	private static String lxsw ="6";
	private static String sj ="7";
	private static String nj ="8";
	private static String bj ="9";
	
	private static String cw ="10";
	private static String sw ="11";
	private static String jy ="12";
	private static String rcgl ="13";
	private static String cc ="14";
	private static String xx ="15";
	//汇总不包含的类别
	private  static  List<String> hz=new ArrayList<String>(){{
		add("7");
		add("8");
		add("9");
		add("15");
	}
	};
	

	@Autowired
	private WorkAttenceService workAttenceService;
	
	@Autowired
	private SpecialtyService specialtyService;

	@Autowired
	private ProjectService projectService;
	
	@ModelAttribute
	public WorkAttence get(@RequestParam(required=false) String id) {
		WorkAttence entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = workAttenceService.get(id);
		}
		if (entity == null){
			entity = new WorkAttence();
		}
		return entity;
	}
	
	/**
	 * 工作日报列表页面
	 */
	@RequiresPermissions("attence:workAttence:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/attence/workAttenceList";
	}
	
		/**
	 * 工作日报列表数据
		 * @throws ParseException 
	 */
	@ResponseBody
	@RequiresPermissions("attence:workAttence:list")
	@RequestMapping(value = "data")
	public List<MyCalendar> data(HttpServletRequest request, HttpServletResponse response, Model model,String startStr,String endStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start=sdf.parse(startStr);
		Date end=sdf.parse(endStr);
		User user=UserUtils.getUser();
		List<MyCalendar>   list=workAttenceService.getList(start,end,user.getId());
		return list;
	}
	
	/**
	 * 工作日报列表页面
	 */
	@RequiresPermissions("attence:workDateStatistics:list")
	@RequestMapping(value = {"workDateStatistics"})
	public String workDateStatistics() {
		return "modules/attence/workDateStatisticsList";
	}
	
	/**
	 * 工日统计汇总
	 */
	@ResponseBody
	@RequiresPermissions("attence:workDateStatistics:list")
	@RequestMapping(value = "datas")
	public Map<String, Object> datas(WorkAttence workAttence, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WorkAttence> page = workAttenceService.findPages(new Page<WorkAttence>(request, response), workAttence); 
		return getBootstrapData(page);
	}
	
	
	
	/**
	 * 项目工日统计汇总
	 */
	@RequiresPermissions("attence:proSumList:list")
	@RequestMapping(value = {"proSumList"})
	public String proSumList(WorkAttence  workAttence, Model model) {
		model.addAttribute("workAttence", workAttence);
	/*	List<Map<String, Object>>   specList=specialtyService.getAllSpec();
		model.addAttribute("specList", specList);*/
		return "modules/attence/proSumList";
	}
	
	/**
	 * 个人项目工日统计汇总
	 */
	@RequiresPermissions("attence:proPersonSumList:list")
	@RequestMapping(value = {"proPersonSumList"})
	public String proPersonSumList(WorkAttence  workAttence, Model model) {
		model.addAttribute("workAttence", workAttence);
		return "modules/attence/proPersonSumList";
	}
	
	/**
	 *  获取所有的在用专业
	 */
	@ResponseBody
	@RequestMapping(value = {"getAllSpec"})
	public List<Specialty> getAllSpec(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		
		List<Specialty>   specList=specialtyService.findAllSpecialtyList();
		
		return specList;
	}
	
	/**
	 * 项目统计汇总
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	@ResponseBody
	@RequiresPermissions("attence:proSumList:list")
	@RequestMapping(value = "proSumData")
	public Map<String, Object> proSumData(WorkAttence workAttence, HttpServletRequest request, HttpServletResponse response, Model model) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		
		ProWorkSum   proWorkSum= new ProWorkSum();
		proWorkSum.setProName(workAttence.getProName());
		proWorkSum.setProNum(workAttence.getProNum());
		proWorkSum.setProShortened(workAttence.getProShortened());
		proWorkSum.setStart(workAttence.getWorkDateStart());
		proWorkSum.setEnd(workAttence.getWorkDateEnd());
		proWorkSum.setSupervisor(workAttence.getSupervisor());
		proWorkSum.setPrincipal(workAttence.getPrincipal());
		Page<ProWorkSum> page = workAttenceService.findProWorkNum(new Page<ProWorkSum>(request, response), proWorkSum); 
		
		
		return getBootstrapData(page);
	}
	
	/**
	 * 个人项目统计汇总
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	@ResponseBody
	@RequiresPermissions("attence:proPersonSumList:list")
	@RequestMapping(value = "proPersonSumData")
	public Map<String, Object> proPersonSumData(WorkAttence workAttence, HttpServletRequest request, HttpServletResponse response, Model model) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		
		ProWorkSum   proWorkSum= new ProWorkSum();
		proWorkSum.setUser(workAttence.getUser());
		proWorkSum.setProName(workAttence.getProName());
		proWorkSum.setProNum(workAttence.getProNum());
		proWorkSum.setStart(workAttence.getWorkDateStart());
		proWorkSum.setEnd(workAttence.getWorkDateEnd());
		Page<ProWorkSum> page = workAttenceService.findProPerSonWorkNum(new Page<ProWorkSum>(request, response), proWorkSum); 
		
		
		return getBootstrapData(page);
	}
	
	
	
	
	
	
	

	/**
	 * 查看，增加，编辑工作日报表单页面
	 * @throws ParseException 
	 */
	@RequiresPermissions(value={"attence:workAttence:view","attence:workAttence:add","attence:workAttence:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form( Model model,String  dateStr) throws ParseException {
		User user =UserUtils.getUser();
		SimpleDateFormat formatStr=new SimpleDateFormat("yyyy-MM-dd");
		Date  date=formatStr.parse(dateStr);	
		
		
		
		Date   before = workAttenceService.getLateDay(user.getId(),date);
	
		String beforeStr=formatStr.format(before);
		
		WorkItem  workItem=new WorkItem();
		List<WorkAttence> nyList=Lists.newArrayList();
		List<WorkAttence> wyList=Lists.newArrayList();
		List<WorkAttence> dgtList=Lists.newArrayList();
		List<WorkAttence> ccList=Lists.newArrayList();
		
		List<WorkAttence>  list=   workAttenceService.getUserRecordByDateNo(user.getId(), dateStr);	
		//如果当天没有数据,取前一天的数据,并将ID设置为空返回给页面
		if(list==null||list.size()==0) {
			list=workAttenceService.getUserRecordByDateNo(user.getId(), beforeStr);	
			if (list!=null&&list.size()>0) {
				for( WorkAttence   work: list ) {
					work.setIsNewRecord(true);
					work.setId(null);
					
				}
			}
			
		}
		
		
		
		if (list!=null&&list.size()>0) {
			workItem.setIsTrip(list.get(0).getIsTrip());
			for( WorkAttence attence :list) {
				 if(ny.equals(attence.getItemType())) {
					 nyList.add(attence);
				 }else if(wy.equals(attence.getItemType())) {
					 wyList.add(attence);
				 }else if(dgt.equals(attence.getItemType())) {
					 dgtList.add(attence);
				 }else if(cc.equals(attence.getItemType())) {
					 ccList.add(attence);
				 }else  if (kc.equals(attence.getItemType())) {
					 workItem.setInspect(attence);
				}else  if (px.equals(attence.getItemType())) {
					 workItem.setTrain(attence);
				}else  if (nj.equals(attence.getItemType())) {
					 workItem.setYearLeave(attence);
				}
				else  if (sj.equals(attence.getItemType())) {
					 workItem.setCasualLeave(attence);
				}else  if (bj.equals(attence.getItemType())) {
					 workItem.setSickLeave(attence);
				}else  if (lxsw.equals(attence.getItemType())) {
					 workItem.setSiteWork(attence);
				}else  if (cw.equals(attence.getItemType())) {
					 workItem.setCw(attence);
				}else  if (sw.equals(attence.getItemType())) {
					 workItem.setSw(attence);
				}else  if (jy.equals(attence.getItemType())) {
					 workItem.setJy(attence);
				}else  if (rcgl.equals(attence.getItemType())) {
					 workItem.setRcgl(attence);
				}else  if (xx.equals(attence.getItemType())) {
					 workItem.setXx(attence);
				}else {
					
				}
			}
			
			if(nyList!=null&&nyList.size()>0) {
				workItem.setOfficeProList(nyList);
			}
			
			if(wyList!=null&&wyList.size()>0) {
				workItem.setOutProList(wyList);
			}
			
			if(dgtList!=null&&dgtList.size()>0) {
				workItem.setOrgProList(dgtList);
			}
			
			if(ccList!=null&&ccList.size()>0) {
				workItem.setCcList(ccList);
			}
			
		//默认显示昨天的	
		}else {
			workItem.setIsTrip("0");
		}
		
		
		
		// 返回时显示那些勾选
		StringBuilder   checkStr=new  StringBuilder();
		if (workItem!=null) {
			if (workItem.getInspect()!=null) {
				checkStr.append("1").append(",");
			}
			if (workItem.getTrain()!=null) {
				checkStr.append("5").append(",");
			}
			if (workItem.getOfficeProList()!=null&&workItem.getOfficeProList().size()>0) {
				checkStr.append("10").append(",");
			}
			if (workItem.getOutProList()!=null&&workItem.getOutProList().size()>0) {
				checkStr.append("15").append(",");
			}
			if (workItem.getOrgProList()!=null&&workItem.getOrgProList().size()>0) {
				checkStr.append("20").append(",");
			}
			
			if (workItem.getCcList()!=null&&workItem.getCcList().size()>0) {
				checkStr.append("65").append(",");
			}
			
			if (workItem.getSiteWork()!=null) {
				checkStr.append("25").append(",");
			}
			
			if (workItem.getCasualLeave()!=null) {
				checkStr.append("30").append(",");
			}
			if (workItem.getYearLeave()!=null) {
				checkStr.append("35").append(",");
			}
			
			if (workItem.getSickLeave()!=null) {
				checkStr.append("40").append(",");
			}
			

			if (workItem.getCw()!=null) {
				checkStr.append("45").append(",");
			}

			if (workItem.getSw()!=null) {
				checkStr.append("50").append(",");
			}

			if (workItem.getJy()!=null) {
				checkStr.append("55").append(",");
			}

			if (workItem.getRcgl()!=null) {
				checkStr.append("60").append(",");
			}
			if (workItem.getXx()!=null) {
				checkStr.append("70").append(",");
			}
			
			
		}
		model.addAttribute("checkStr", checkStr.toString());
		
		// 返回用户ID
		model.addAttribute("userId", user.getId());
		// 返回日期
		model.addAttribute("dateStr", dateStr);
		
		model.addAttribute("workItem", workItem);
		
		return "modules/attence/workAttenceDetail";
	}
	
	/**
	 * 查看项目工日详情
	 * @throws ParseException 
	 */
	@RequiresPermissions(value={"attence:workAttence:showView"},logical=Logical.OR)
	@RequestMapping(value = "showView")
	public String showView(String id, Model model,String startDate,String endDate) throws ParseException {
		if(startDate != null && !startDate.equals("")){
			startDate = startDate + "-01";
		}
		if(endDate != null && !endDate.equals("")){
			endDate = endDate + "-01";
		}
		List<WorkAttence> workAttenceList = workAttenceService.findWorkAttence(id,startDate,endDate);
		model.addAttribute("workAttenceList", workAttenceList);
		return "modules/attence/proSumView";
	}

	
	
	/**
 * 工作日报列表数据
	 * @throws ParseException 
 */
@ResponseBody
@RequiresPermissions("attence:workAttence:list")
@RequestMapping(value = "getDetail")
public Map<String, Object> getDetail( HttpServletRequest request, HttpServletResponse response, Model model,String  userId,String dateStr) throws ParseException {
	// 1,根据用户和日期在日程管理表中查看是否有记录,如果有记录则,获取记录表
	// 2,如果没记录,则从项目人员专业表中获取,需要判断时间
	//  3,将获取的记录返回 
	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	 Date  date=sdf.parse(dateStr);
	User user =UserUtils.getUser();
	List<WorkAttence>   record=workAttenceService.getUserRecordByDate(user.getId(),date);
	if (record==null||record.size()==0) {
		record=workAttenceService.getUserSpec(user.getId(),date);
	}
	Page<WorkAttence> page=new  Page<WorkAttence>();
	page.setList(record);
	page.setPageNo(1);
	page.setCount(record.size());
	page.setPageSize(record.size());
	return getBootstrapData(page);
}
	



/**
* 月度统计
 * @throws ParseException 
*/
@ResponseBody
@RequiresPermissions("attence:workAttence:list")
@RequestMapping(value = "monthList")
public Map<String, Object> monthList(WorkAttence  workAttence, HttpServletRequest request, HttpServletResponse response, Model model,String dateStr) throws ParseException {
	dateStr=dateStr.replace(",", "");
	
	if(StringUtils.isBlank(dateStr)) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
		calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat firstDay= new SimpleDateFormat("yyyy-MM-dd");
		dateStr=firstDay.format(calendar.getTime());				
	}
	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	 
	 	Date date =sdf.parse(dateStr);
		User user =UserUtils.getUser();
		Date  workDateStart=workAttence.getWorkDateStart();
		Date  workDateEnd=workAttence.getWorkDateEnd();
		List<WorkAttence>   record=workAttenceService.getUserMonthList(user.getId(),date,workDateStart,workDateEnd);

        if(record==null||record.size()==0){

        }else{
            WorkAttence   sum=new WorkAttence();
            sum.setItemName("汇总");
            sum.setItemType("999");
            double   sumWorkTime=0;
            double  sumOverTime=0;

          for(WorkAttence work:record){
          	//汇总不包括请假和学习
			if (hz.contains(work.getItemType())) {
				continue;
			}else {
				if (work.getWorkTime()!=null){
					// sumWorkTime=sumWorkTime+work.getWorkTime();
					sumWorkTime=MathUtils.sum(sumWorkTime,work.getWorkTime());
				}
				if (work.getOverTime()!=null){
					//sumOverTime=sumOverTime+work.getOverTime();
					sumOverTime=MathUtils.sum(sumOverTime,work.getOverTime());
				}
			}
          }
            sum.setWorkTime(sumWorkTime);
            sum.setOverTime(sumOverTime);
            record.add(sum);

        }
			Page<WorkAttence> page=new  Page<WorkAttence>();
				page.setList(record);
				page.setPageNo(1);
				page.setCount(record.size());
				page.setPageSize(record.size());
				return getBootstrapData(page);
}

	
	/*
	*//**
	 * 保存工作日报
	 *//*
	@ResponseBody
	@RequiresPermissions(value={"attence:workAttence:add","attence:workAttence:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(@RequestBody List<AttenceData> attenceData, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		try {
			workAttenceService.save(attenceData);//新建或者编辑保存
			j.setSuccess(true);
			j.setMsg("保存工作日报成功");
			return j;
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("保存工作日报失败");
			return j;
		}
		
	}
	*/
	
	
	
	/**
	 * 保存工作日报
	 */
	@ResponseBody
	@RequiresPermissions(value={"attence:workAttence:add","attence:workAttence:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save( WorkItem item, String dateStr, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		try {
			workAttenceService.saveItem(item,dateStr);//新建或者编辑保存
			j.setSuccess(true);
			j.setMsg("保存工作日报成功");
			return j;
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("保存工作日报失败");
			return j;
		}
		
	}
	/**
	 * 删除工作日报
	 */
	@ResponseBody
	@RequiresPermissions("attence:workAttence:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WorkAttence workAttence, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		workAttenceService.delete(workAttence);
		j.setMsg("删除工作日报成功");
		return j;
	}
	
	/**
	 * 批量删除工作日报
	 */
	@ResponseBody
	@RequiresPermissions("attence:workAttence:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			workAttenceService.delete(workAttenceService.get(id));
		}
		j.setMsg("删除工作日报成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("attence:workAttence:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(WorkAttence workAttence, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工作日报"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WorkAttence> page = workAttenceService.findPage(new Page<WorkAttence>(request, response, -1), workAttence);
    		new ExportExcel("工作日报", WorkAttence.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出工作日报记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("attence:workDateStatistics:export")
    @RequestMapping(value = "exportFile", method=RequestMethod.POST)
    public AjaxJson export(WorkAttence workAttence, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工日统计汇总"+DateUtils.getDate("yyyyMMdd")+".xlsx";
            Page<WorkAttence> page = workAttenceService.findPages(new Page<WorkAttence>(request, response, -1), workAttence);
    		new ExportExcel("工日统计汇总", WorkAttence.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出工日统计汇总失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("attence:workAttence:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WorkAttence> list = ei.getDataList(WorkAttence.class);
			for (WorkAttence workAttence : list){
				try{
					workAttenceService.save(workAttence);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条工作日报记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条工作日报记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入工作日报失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/attence/workAttence/?repage";
    }
	
	/**
	 * 下载导入工作日报数据模板
	 */
	@RequiresPermissions("attence:workAttence:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "工作日报数据导入模板.xlsx";
    		List<WorkAttence> list = Lists.newArrayList(); 
    		new ExportExcel("工作日报数据", WorkAttence.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/attence/workAttence/?repage";
    }
	
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("attence:workAttence:export")
    @RequestMapping(value = "exportProSum", method=RequestMethod.POST)
    public AjaxJson exportProSum(WorkAttence workAttence, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		
		AjaxJson j = new AjaxJson();
		ProWorkSum   proWorkSum= new ProWorkSum();
		proWorkSum.setProName(workAttence.getProName());
		proWorkSum.setProNum(workAttence.getProNum());
		proWorkSum.setStart(workAttence.getWorkDateStart());
		proWorkSum.setEnd(workAttence.getWorkDateEnd());
		String headStr="";
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		if (proWorkSum.getStart()==null&&proWorkSum.getEnd()==null) {
			Calendar calendar = Calendar.getInstance();
	        String year = String.valueOf(calendar.get(Calendar.YEAR));
			 headStr="项目工日"+year+"年";
		}else if (proWorkSum.getStart()!=null&&proWorkSum.getEnd()!=null&&proWorkSum.getStart().before(proWorkSum.getEnd())) {
			 headStr="项目工日"+sdf.format(proWorkSum.getStart())+"月-"+sdf.format(proWorkSum.getEnd())+"月";
			
		}else if (proWorkSum.getStart()!=null&&proWorkSum.getEnd()!=null&&proWorkSum.getEnd().before(proWorkSum.getStart())) {
			 headStr="项目工日"+sdf.format(proWorkSum.getEnd())+"月-"+sdf.format(proWorkSum.getStart())+"月";
			proWorkSum.setEnd(workAttence.getWorkDateStart());
			proWorkSum.setStart(workAttence.getWorkDateEnd());
		}
		else if (proWorkSum.getStart()!=null&&proWorkSum.getEnd()!=null&&proWorkSum.getEnd().getTime()==proWorkSum.getStart().getTime()) {
			 headStr="项目工日"+sdf.format(proWorkSum.getEnd())+"月";
		
		}else if (proWorkSum.getStart()==null&&proWorkSum.getEnd()!=null) {
			 headStr="项目工日至"+sdf.format(proWorkSum.getEnd())+"月";
		} else if (proWorkSum.getEnd()==null&&proWorkSum.getStart()!=null) {
			 headStr="项目工日"+sdf.format(proWorkSum.getStart())+"月至今";
		} 
		try {
			String fileName = StringUtils.isBlank(headStr)?"项目工日":headStr+".xls";
			//所有在用的专业
			List<Specialty>   specList=specialtyService.findAllSpecialtyList();
			
			List<ProWorkSum>  resultList=workAttenceService.findProWorkNumList(proWorkSum);
	
			//创建HSSFWorkbook对象(excel的文档对象)  
			HSSFWorkbook workbook = new HSSFWorkbook(); 
			 CellRangeAddress biaoti = new CellRangeAddress(0,2,0,7+specList.size()*3);//起始行,结束行,起始列,结束列
			
			
			//项目编号
			 CellRangeAddress callRange_bh = new CellRangeAddress(3,4,0,0);//起始行,结束行,起始列,结束列
			// 项目名称
	          CellRangeAddress callRange_mc = new CellRangeAddress(3,4,1,1);//起始行,结束行,起始列,结束列
	       // 项目类别
	          CellRangeAddress callRange_xmlb = new CellRangeAddress(3,4,2,2);//起始行,结束行,起始列,结束列
	       // 设计阶段
	         // CellRangeAddress callRange_sjlb = new CellRangeAddress(3,4,3,3);//起始行,结束行,起始列,结束列
	       // 项目负责人
	          CellRangeAddress callRange_fzr = new CellRangeAddress(3,4,3,3);//起始行,结束行,起始列,结束列
	       // 项目主管
	          CellRangeAddress callRange_zg = new CellRangeAddress(3,4,4,4);//起始行,结束行,起始列,结束列
	          // 总工日
	          CellRangeAddress callRange_zgr = new CellRangeAddress(3,4,5,5);//起始行,结束行,起始列,结束列
	          // 总加班小时
	          CellRangeAddress callRange_jbxs = new CellRangeAddress(3,4,6,6);//起始行,结束行,起始列,结束列

			// 总加班工日
			CellRangeAddress callRange_jbgr = new CellRangeAddress(3,4,7,7);//起始行,结束行,起始列,结束列



			//内容样式
	            HSSFCellStyle cellStyle = createCellStyle(workbook,(short)10,true,true);
	            
	            //大标题
	            HSSFCellStyle headStyle = createCellStyle(workbook,(short)20,true,true);
	       
	          HSSFSheet sheet = workbook.createSheet("项目工日");
	          //头部样式
	         /* HSSFCellStyle headStyle = headStyle(workbook);*/
	            //2.1加载合并单元格对象
	          	sheet.addMergedRegion(biaoti);
	            sheet.addMergedRegion(callRange_bh);
	            sheet.addMergedRegion(callRange_mc);
	            sheet.addMergedRegion(callRange_xmlb);
	            //sheet.addMergedRegion(callRange_sjlb);
	            sheet.addMergedRegion(callRange_fzr);
	            
	            sheet.addMergedRegion(callRange_zg);
	            sheet.addMergedRegion(callRange_zgr);
	            sheet.addMergedRegion(callRange_jbxs);
			sheet.addMergedRegion(callRange_jbgr);

	            int  i=3;
	         for(Specialty spec:specList) {
	        	 sheet.addMergedRegion (new CellRangeAddress(3,3,7+i-2,7+i));
	        	
	        	 i=i+3;
	         }
			
	         //第一行
	         HSSFRow row0 = sheet.createRow(0); 
	         HSSFCell cellHead=row0.createCell(0);
	         cellHead.setCellValue(StringUtils.isBlank(headStr)?"项目工日":headStr);
	         cellHead.setCellStyle(headStyle);
	         HSSFRow row = sheet.createRow(3); 
	         HSSFRow row2 = sheet.createRow(4); 
	         HSSFCell cell = row.createCell(0);
	         cell.setCellValue("项目编号");
	         cell.setCellStyle(cellStyle);
	         HSSFCell cell_bh = row.createCell(1);
	         cell_bh.setCellValue("项目简称");
	         cell_bh.setCellStyle(cellStyle);
	         
	         HSSFCell cell_xmlb = row.createCell(2);
	         cell_xmlb.setCellValue("项目类别");
	         cell_xmlb.setCellStyle(cellStyle);
	         
	         /*HSSFCell cell_sjlb = row.createCell(3);
	         cell_sjlb.setCellValue("设计阶段");
	         cell_sjlb.setCellStyle(cellStyle);*/
	         
	         HSSFCell cell_xmzg = row.createCell(3);
	         cell_xmzg.setCellValue("主管领导");
	         cell_xmzg.setCellStyle(cellStyle);

	         HSSFCell cell_fzr = row.createCell(4);
	         cell_fzr.setCellValue("项目负责人");
	         cell_fzr.setCellStyle(cellStyle);
	         
	         HSSFCell cell_zgr = row.createCell(5);
	         cell_zgr.setCellValue("总工日");
	         cell_zgr.setCellStyle(cellStyle);
	         
	         HSSFCell cell_zjbxs = row.createCell(6);
	         cell_zjbxs.setCellValue("总加班(小时)");
	         cell_zjbxs.setCellStyle(cellStyle);

			HSSFCell cell_jbgr = row.createCell(7);
			cell_jbgr.setCellValue("折算总工日");
			cell_jbgr.setCellStyle(cellStyle);


			int z=0;
	         for(int k=0;k<specList.size();k++) {	        	 
	        	 HSSFCell cell1 = row.createCell(8+z);
	        	 cell1.setCellValue(specList.get(k).getName());
	        	 cell1.setCellStyle(cellStyle);
	        	 HSSFCell cell2 = row2.createCell(8+z);
	        	 HSSFCell cell3 = row2.createCell(9+z);
				 HSSFCell cell4 = row2.createCell(10+z);
	        		 cell2.setCellValue("工日");
	        		 cell2.setCellStyle(cellStyle);
	        		 cell3.setCellValue("加班");
	        		 cell3.setCellStyle(cellStyle);
				 cell4.setCellValue("折算工日");
				 cell4.setCellStyle(cellStyle);
	        	 z=z+3;
	         }
	         ProWorkSum   proWorkSum2;
	         i=0;
	         int y=0;
	         for(int x=0; x<resultList.size(); x++){ 
	        	 proWorkSum2=resultList.get(x);
	        	 HSSFRow row3 = sheet.createRow(5+x);
                 HSSFCell cell0 = row3.createCell(0);
                 //项目编号
                 cell0.setCellValue(proWorkSum2.getPro().getNum());
                 //项目简称
                 HSSFCell cell1 = row3.createCell(1);
                 cell1.setCellValue(proWorkSum2.getPro().getProShortened());
                 //项目类别
                 HSSFCell proType = row3.createCell(2);
                 proType.setCellValue(DictUtils.getDictLabel(proWorkSum2.getPro().getType(), "pro_type", ""));
                 
                 //设计阶段
                 //HSSFCell cell2 = row3.createCell(3);
                 //cell2.setCellValue(DictUtils.getDictLabel(proWorkSum2.getPro().getDesignType(), "design_type", ""));

                 //项目主管
                 HSSFCell cell4 = row3.createCell(3);
                 cell4.setCellValue(proWorkSum2.getSupervisor());
                 
                 //项目负责人
                 HSSFCell cell3 = row3.createCell(4);
                 //查找项目负责人
                 cell3.setCellValue(proWorkSum2.getPrincipal());
                 
                 HSSFCell cell5 = row3.createCell(5);
                 cell5.setCellValue(proWorkSum2.getSumWorkTime());
                 
                 HSSFCell cell6 = row3.createCell(6);
                 cell6.setCellValue(proWorkSum2.getSumOverTime());

				 HSSFCell cell77 = row3.createCell(7);
				 cell77.setCellValue(proWorkSum2.getOverTimeDay());



				 y=0;
                
                 int colNum=0;
                 for(Specialty spec:specList) {
                	 	//反射绑定赋值
                	  colNum=spec.getColNum();
                	  Field workField = proWorkSum2.getClass().getDeclaredField("work"+colNum);
                      //设置对象的访问权限，保证对private的属性的访问
                	  workField.setAccessible(true);
                      Field overField = proWorkSum2.getClass().getDeclaredField("over"+colNum);
					 //设置对象的访问权限，保证对private的属性的访问
					 overField.setAccessible(true);

					 Field dayField = proWorkSum2.getClass().getDeclaredField("ovday"+colNum);
					 //设置对象的访问权限，保证对private的属性的访问
					 dayField.setAccessible(true);

                	  HSSFCell cell7 = row3.createCell(8+y);
                	  cell7.setCellValue((double)workField.get(proWorkSum2));
                      HSSFCell cell8 = row3.createCell(9+y);
                      cell8.setCellValue((double)overField.get(proWorkSum2));

					 HSSFCell cell9 = row3.createCell(10+y);
					 cell9.setCellValue((double)dayField.get(proWorkSum2));
                      y=y+3;
                    
    	         } 
              
	        	 
	        	i++; 
	        	 
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
    		j.setMsg("导出项目工日成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出项目工日记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
	
	
	/**
	 * 个人项目工日导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("attence:workAttence:exportProPersonSum")
    @RequestMapping(value = "exportProPersonSum", method=RequestMethod.POST)
    public AjaxJson exportProPersonSum(WorkAttence workAttence, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		
		AjaxJson j = new AjaxJson();
		ProWorkSum   proWorkSum= new ProWorkSum();
		proWorkSum.setUser(workAttence.getUser());
		proWorkSum.setProName(workAttence.getProName());
		proWorkSum.setProNum(workAttence.getProNum());
		proWorkSum.setStart(workAttence.getWorkDateStart());
		proWorkSum.setEnd(workAttence.getWorkDateEnd());
		String headStr="";
		Date date=new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		if (proWorkSum.getStart()==null&&proWorkSum.getEnd()==null) {
			 headStr="项目工日详表"+sdf.format(date)+"月";;
		}else if (proWorkSum.getStart()!=null&&proWorkSum.getEnd()!=null&&proWorkSum.getStart().before(proWorkSum.getEnd())) {
			 headStr="项目工日详表"+sdf.format(proWorkSum.getStart())+"月-"+sdf.format(proWorkSum.getEnd())+"月";
			
		}else if (proWorkSum.getStart()!=null&&proWorkSum.getEnd()!=null&&proWorkSum.getEnd().before(proWorkSum.getStart())) {
			 headStr="项目工日详表"+sdf.format(proWorkSum.getEnd())+"月-"+sdf.format(proWorkSum.getStart())+"月";
			proWorkSum.setEnd(workAttence.getWorkDateStart());
			proWorkSum.setStart(workAttence.getWorkDateEnd());
		}
		else if (proWorkSum.getStart()!=null&&proWorkSum.getEnd()!=null&&proWorkSum.getEnd().getTime()==proWorkSum.getStart().getTime()) {
			 headStr="项目工日详表"+sdf.format(proWorkSum.getEnd())+"月";
		
		}else if (proWorkSum.getStart()==null&&proWorkSum.getEnd()!=null) {
			 headStr="项目工日详表至"+sdf.format(proWorkSum.getEnd())+"月";
		} else if (proWorkSum.getEnd()==null&&proWorkSum.getStart()!=null) {
			 headStr="项目工日详表"+sdf.format(proWorkSum.getStart())+"月至今";
		} 
		try {
			String fileName = StringUtils.isBlank(headStr)?"项目工日详表":headStr+".xls";
			//所有在用的专业
			List<Specialty>   specList=specialtyService.findAllSpecialtyList();
			
			List<ProWorkSum>  resultList=workAttenceService.findProPerSonWorkNumList(proWorkSum);
	
			//创建HSSFWorkbook对象(excel的文档对象)  
			HSSFWorkbook workbook = new HSSFWorkbook(); 
			 CellRangeAddress biaoti = new CellRangeAddress(0,2,0,7+(specList.size())*2);//起始行,结束行,起始列,结束列

			//专业人员
			 CellRangeAddress callRange_zyry = new CellRangeAddress(3,4,0,0);//起始行,结束行,起始列,结束列
			
			//项目编号
			 CellRangeAddress callRange_bh = new CellRangeAddress(3,4,1,1);//起始行,结束行,起始列,结束列
			// 项目名称
	          CellRangeAddress callRange_mc = new CellRangeAddress(3,4,2,2);//起始行,结束行,起始列,结束列
			
	       // 设计阶段
	          CellRangeAddress callRange_sjlb = new CellRangeAddress(3,4,3,3);//起始行,结束行,起始列,结束列
	       // 项目负责人
	          CellRangeAddress callRange_fzr = new CellRangeAddress(3,4,4,4);//起始行,结束行,起始列,结束列
	       // 项目主管
	          CellRangeAddress callRange_zg = new CellRangeAddress(3,4,5,5);//起始行,结束行,起始列,结束列
	          // 总工日
	          CellRangeAddress callRange_zgr = new CellRangeAddress(3,4,6,6);//起始行,结束行,起始列,结束列
	          // 总加班小时
	          CellRangeAddress callRange_jbxs = new CellRangeAddress(3,4,7,7);//起始行,结束行,起始列,结束列
			 
	   
	          
	          //内容样式
	            HSSFCellStyle cellStyle = createCellStyle(workbook,(short)10,true,true);
	            
	            //大标题
	            HSSFCellStyle headStyle = createCellStyle(workbook,(short)20,true,true);
	       
	          HSSFSheet sheet = workbook.createSheet("项目工日详表");
	          //头部样式
	         /* HSSFCellStyle headStyle = headStyle(workbook);*/
	            //2.1加载合并单元格对象
	          	sheet.addMergedRegion(biaoti);
	            sheet.addMergedRegion(callRange_zyry);
	            sheet.addMergedRegion(callRange_bh);
	            sheet.addMergedRegion(callRange_mc);
	            sheet.addMergedRegion(callRange_sjlb);
	            sheet.addMergedRegion(callRange_fzr);
	            
	            sheet.addMergedRegion(callRange_zg);
	            sheet.addMergedRegion(callRange_zgr);
	            sheet.addMergedRegion(callRange_jbxs);
	            int  i=2;
	         for(Specialty spec:specList) {
	        	 sheet.addMergedRegion (new CellRangeAddress(3,3,7+i-1,7+i));
	        	
	        	 i=i+2;
	         }
			
	         //第一行
	         HSSFRow row0 = sheet.createRow(0); 
	         HSSFCell cellHead=row0.createCell(0);
	         cellHead.setCellValue(StringUtils.isBlank(headStr)?"项目工日详表":headStr);
	         cellHead.setCellStyle(headStyle);
	         HSSFRow row = sheet.createRow(3); 
	         HSSFRow row2 = sheet.createRow(4); 
	         HSSFCell cell_zyry = row.createCell(0);
	         cell_zyry.setCellValue("专业人员");
	         cell_zyry.setCellStyle(cellStyle); 
	         HSSFCell cell = row.createCell(1);
	         cell.setCellValue("项目编号");
	         cell.setCellStyle(cellStyle);
	         HSSFCell cell_bh = row.createCell(2);
	         cell_bh.setCellValue("项目名称");
	         cell_bh.setCellStyle(cellStyle);
	         HSSFCell cell_sjlb = row.createCell(3);
	         cell_sjlb.setCellValue("设计阶段");
	         cell_sjlb.setCellStyle(cellStyle);
	         
	         HSSFCell cell_xmzg = row.createCell(4);
	         cell_xmzg.setCellValue("主管领导");
	         cell_xmzg.setCellStyle(cellStyle);

	         HSSFCell cell_fzr = row.createCell(5);
	         cell_fzr.setCellValue("项目负责人");
	         cell_fzr.setCellStyle(cellStyle);
	         
	         HSSFCell cell_zgr = row.createCell(6);
	         cell_zgr.setCellValue("总工日");
	         cell_zgr.setCellStyle(cellStyle);
	         
	         HSSFCell cell_zjbxs = row.createCell(7);
	         cell_zjbxs.setCellValue("总加班(小时)");
	         cell_zjbxs.setCellStyle(cellStyle);
	         
	         int z=0;
	         for(int k=0;k<specList.size();k++) {	        	 
	        	 HSSFCell cell1 = row.createCell(8+z);
	        	 cell1.setCellValue(specList.get(k).getName());
	        	 cell1.setCellStyle(cellStyle);
	        	 HSSFCell cell2 = row2.createCell(8+z);
	        	 HSSFCell cell3 = row2.createCell(9+z);
	        		 cell2.setCellValue("工日");
	        		 cell2.setCellStyle(cellStyle);
	        		 cell3.setCellValue("加班");
	        		 cell3.setCellStyle(cellStyle);
	        	 z=z+2;
	         }
	         ProWorkSum   proWorkSum2;
	         i=0;
	         int y=0;
	         for(int x=0; x<resultList.size(); x++){ 
	        	 proWorkSum2=resultList.get(x);
	        	 HSSFRow row3 = sheet.createRow(5+x);
	        	 //专业人员
                 HSSFCell cellzyry = row3.createCell(0);
                 cellzyry.setCellValue(proWorkSum2.getUser().getName());
                 //项目编号
                 HSSFCell cell0 = row3.createCell(1);
                 //项目编号
                 cell0.setCellValue(proWorkSum2.getPro().getNum());
                 //项目名称
                 HSSFCell cell1 = row3.createCell(2);
                 cell1.setCellValue(proWorkSum2.getPro().getName());
                 //设计阶段
                 HSSFCell cell2 = row3.createCell(3);
                 
                 cell2.setCellValue(DictUtils.getDictLabel(proWorkSum2.getPro().getDesignType(), "design_type", ""));

                 //项目主管
                 HSSFCell cell4 = row3.createCell(4);
                 cell4.setCellValue(proWorkSum2.getPro().getSupervisor());
                 
                 //项目负责人
                 HSSFCell cell3 = row3.createCell(5);
                 //查找项目负责人
 				 String principal = projectService.getPrincipal(proWorkSum2.getPro().getId());
                 cell3.setCellValue(principal);
                 
                 HSSFCell cell5 = row3.createCell(6);
                 cell5.setCellValue(proWorkSum2.getSumWorkTime());
                 
                 HSSFCell cell6 = row3.createCell(7);
                 cell6.setCellValue(proWorkSum2.getSumOverTime());
                 
                 y=0;
                
                 int colNum=0;
                 for(Specialty spec:specList) {
                	 	//反射绑定赋值
                	  colNum=spec.getColNum();
                	  Field workField = proWorkSum2.getClass().getDeclaredField("work"+colNum);
                      //设置对象的访问权限，保证对private的属性的访问
                	  workField.setAccessible(true);
                      Field overField = proWorkSum2.getClass().getDeclaredField("over"+colNum);
                      //设置对象的访问权限，保证对private的属性的访问
                      overField.setAccessible(true);
                	  HSSFCell cell7 = row3.createCell(8+y);
                	  cell7.setCellValue((double)workField.get(proWorkSum2));
                      HSSFCell cell8 = row3.createCell(9+y);
                      cell8.setCellValue((double)overField.get(proWorkSum2));
                      y=y+2;
                    
    	         } 
              
	        	 
	        	i++; 
	        	 
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
    		j.setMsg("导出项目工日详表成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出项目工日详表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
	
	/**
	 * 表头样式
	 * @param wkb
	 * @return
	 */
	public static HSSFCellStyle headStyle(HSSFWorkbook wkb){
		HSSFCellStyle style = wkb.createCellStyle();
	    //设置背景颜色
	    style.setFillForegroundColor(HSSFColor.LIME.index);
	    //solid 填充  foreground  前景色
	    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    //拿到palette颜色板
	    HSSFPalette palette = wkb.getCustomPalette();
	    //把之前的颜色 HSSFColor.LIME.index改成你想要的颜色
	    palette.setColorAtIndex(HSSFColor.LIME.index, (byte) 128, (byte) 128, (byte) 128);
	
		//设置字体
		HSSFFont font1 = wkb.createFont();    
		font1.setFontName("黑体");    
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
		font1.setFontHeightInPoints((short) 11);
		font1.setColor(HSSFColor.WHITE.index);
		//选择需要用到的字体格式
		style.setFont(font1);
		return style;
	}
	
	/**
	 * 边框样式
	 * @param wkb
	 * @return
	 */
	public static HSSFCellStyle borderStyle(HSSFWorkbook wkb){
		HSSFCellStyle style = wkb.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		//设置边框颜色
		style.setTopBorderColor(HSSFColor.LIME.index);
		style.setBottomBorderColor(HSSFColor.LIME.index);
		style.setLeftBorderColor(HSSFColor.LIME.index);
		style.setRightBorderColor(HSSFColor.LIME.index);
		//拿到palette颜色板
	    HSSFPalette palette = wkb.getCustomPalette();
	    //把之前的颜色 HSSFColor.LIME.index改成你想要的颜色
	    palette.setColorAtIndex(HSSFColor.LIME.index, (byte) 128, (byte) 128, (byte) 128);
		return style;
	}
	
	/**
	 * 标题样式
	 * @param wkb
	 * @return
	 */
	public static HSSFCellStyle titleStyle(HSSFWorkbook wkb){
		HSSFCellStyle style = wkb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		//设置字体
		HSSFFont font1 = wkb.createFont();    
		font1.setFontName("黑体");    
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
		font1.setFontHeightInPoints((short) 20);
		//选择需要用到的字体格式
		style.setFont(font1);
		return style;
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