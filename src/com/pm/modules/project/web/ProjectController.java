/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.project.web;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.DataFormat;
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
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.DictUtils;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.pm.modules.project.entity.Principal;
import com.pm.modules.project.entity.Project;
import com.pm.modules.project.entity.Specialty;
import com.pm.modules.project.entity.SpecialtyUser;
import com.pm.modules.project.entity.Supervisor;
import com.pm.modules.project.service.ProjectService;
import com.pm.modules.project.service.SpecialtyService;

/**
 * 项目管理Controller
 * @author yt
 * @version 2018-10-15
 */
@Controller
@RequestMapping(value = "${adminPath}/project/project")
public class ProjectController extends BaseController {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private SpecialtyService specialtyService;
	
	@ModelAttribute
	public Project get(@RequestParam(required=false) String id) {
		Project entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectService.get(id);
		}
		if (entity == null){
			entity = new Project();
		}
		return entity;
	}
	
	/**
	 * 项目信息列表页面
	 */
	@RequiresPermissions("project:project:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/project/projectList";
	}
	
		/**
	 * 项目信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("project:project:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		//项目类别不为空
		if(project.getType() != null && !project.getType().equals("")){
			project.setProType(null);
		}
		Page<Project> page = projectService.findPage(new Page<Project>(request, response), project); 
		return getBootstrapData(page);
	}
	
	
	
	/**
 * 个人非党工团项目
 */
	@ResponseBody

	@RequestMapping(value = "dataUser")
	public Map<String, Object> dataUser(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		User  user=UserUtils.getUser();
		project.setPrincipal(user.getId());
	Page<Project> page = projectService.findPagedataUser(new Page<Project>(request, response), project); 
	return getBootstrapData(page);
}
	
	
	
	
	
	/**
 * 个人党工团项目
 */
	@ResponseBody

	@RequestMapping(value = "dataUserDgt")
	public Map<String, Object> dataUserDgt(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		User  user=UserUtils.getUser();
		project.setPrincipal(user.getId());
	Page<Project> page = projectService.findPagedataUserDgt(new Page<Project>(request, response), project); 
	return getBootstrapData(page);
}
	

	/**
	 * 增加，编辑项目信息表单页面
	 */
	@RequiresPermissions(value={"project:project:add","project:project:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Project project, Model model) {
		//1.根据项目id查找项目主管
		List<Supervisor> supervisorList = projectService.getSupervisorListByProId(project.getId());
		if(supervisorList != null && supervisorList.size() > 0){
			project.setSupervisorList(supervisorList);
		}
		//2.根据项目id查找专业人员
		List<SpecialtyUser> specialtyUserLists = projectService.getSpecialtyUserListByProId(project.getId());
		if(specialtyUserLists != null && specialtyUserLists.size() > 0){
			project.setSpecialtyUserList(specialtyUserLists);
		}
		
		//3.根据项目id查找项目负责人
		List<Principal> principalLists = projectService.getPrincipalListsByProId(project.getId());
		if(principalLists != null && principalLists.size() > 0){
			project.setPrincipalList(principalLists);
		}
		
		
		//所有专业,人员信息
		List<SpecialtyUser> specialtyUserList = Lists.newArrayList();
		//查找所有专业信息
		List<Specialty> specialtyList = specialtyService.findAllSpecialtyList();
		for(Specialty specialty : specialtyList){
			//专业人员
			SpecialtyUser specialtyUser = new SpecialtyUser();
			//专业名称
			specialtyUser.setSpecName(specialty.getName());
			 List<Supervisor> upervisorList= projectService.getAllUser(specialty.getId());
			 for(Supervisor supervisor : upervisorList){
				 //用户id = 用户id + 专业id
				 supervisor.setUserId(supervisor.getUserId()+"#"+specialty.getId());
			 }
			specialtyUser.setSupervisorList(upervisorList);
			specialtyUserList.add(specialtyUser);
		}
		
		model.addAttribute("project", project);
		//全部主管
		model.addAttribute("allSupervisor", projectService.getAllSuperUser());
		//全部项目负责人
		model.addAttribute("allPrincipal", projectService.getAllPrincipalUser());
		model.addAttribute("allSpecialtyUser", specialtyUserList);
		return "modules/project/projectForm";
	}
	
	/**
	 * 查看项目信息表单页面
	 */
	@RequiresPermissions(value={"project:project:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(Project project, Model model) {
		//查找项目主管 
		String supervisor = projectService.getSupervisors(project.getId());
		project.setSupervisor(supervisor);
		
		//查找项目负责人
		String principal = projectService.getPrincipal(project.getId());
		project.setPrincipal(principal);
		
		//所有专业,人员信息
		List<SpecialtyUser> specialtyUserList = Lists.newArrayList();
		//查找所有专业信息
		List<Specialty> specialtyList = specialtyService.findAllSpecialtyList();
		for(Specialty specialty : specialtyList){
			SpecialtyUser specialtyUser = new SpecialtyUser();
			//专业名称
			specialtyUser.setSpecName(specialty.getName());
			//根据项目id和专业id查找专业人员
			String userName = projectService.getSpecialtyUserListByProIdAndSpecId(project.getId(),specialty.getId());
			specialtyUser.setUserName(userName);
			specialtyUserList.add(specialtyUser);
		}
		
		model.addAttribute("project", project);
		model.addAttribute("allSpecialtyUser", specialtyUserList);
		return "modules/project/projectView";
	}

	/**
	 * 保存项目信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"project:project:add","project:project:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Project project, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		projectService.save(project);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存项目信息成功");
		return j;
	}
	
	/**
	 * 删除项目信息
	 */
	@ResponseBody
	@RequiresPermissions("project:project:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Project project, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		projectService.delete(project);
		j.setMsg("删除项目信息成功");
		return j;
	}
	
	/**
	 * 批量删除项目信息
	 */
	@ResponseBody
	@RequiresPermissions("project:project:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			projectService.delete(projectService.get(id));
		}
		j.setMsg("删除项目信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("project:project:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Project project, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "项目一览表"+DateUtils.getDate("yyyyMMdd")+".xls";
			Page<Project> page = projectService.findPages(new Page<Project>(request, response, -1), project);
			List<Project> projectList = page.getList();
			//创建HSSFWorkbook对象(excel的文档对象)  
			HSSFWorkbook wkb = new HSSFWorkbook(); 
			
			//建立新的sheet对象（excel的表单）  
			HSSFSheet sheet=wkb.createSheet(fileName); 
		
			//在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个  
			HSSFRow row = sheet.createRow(0);  
			//创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个  
			HSSFCell cell=row.createCell(0);
			//标题样式
			HSSFCellStyle titleStyle = titleStyle(wkb);
			cell.setCellStyle(titleStyle);
			//设置单元格内容  
			cell.setCellValue("项目一览表");  
			//查找项目所有专业
			List<Specialty> specialtyList = specialtyService.findAllSpecialtyList();
			//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列  
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,11+specialtyList.size()));  
			//在sheet里创建第二行  
			row = sheet.createRow(1);
			//单元格宽度
			int width = 256*15+184;
			//头部样式
			HSSFCellStyle headStyle = headStyle(wkb);
			//创建单元格并设置单元格内容  
			row.createCell(0).setCellValue("序号"); 
			row.getCell(0).setCellStyle(headStyle);
			sheet.setColumnWidth(0, width);
			row.createCell(1).setCellValue("项目编号"); 
			row.getCell(1).setCellStyle(headStyle);
			sheet.setColumnWidth(1, width);
			row.createCell(2).setCellValue("项目简称"); 
			row.getCell(2).setCellStyle(headStyle);
			sheet.setColumnWidth(2, width);     
			row.createCell(3).setCellValue("项目类别"); 
			row.getCell(3).setCellStyle(headStyle);
			sheet.setColumnWidth(3, width); 
			/*row.createCell(4).setCellValue("设计阶段"); 
			row.getCell(4).setCellStyle(headStyle);
			sheet.setColumnWidth(4, width);*/
			row.createCell(4).setCellValue("主管领导");
			row.getCell(4).setCellStyle(headStyle);
			sheet.setColumnWidth(4, width);
			row.createCell(5).setCellValue("项目负责人"); 
			row.getCell(5).setCellStyle(headStyle);
			sheet.setColumnWidth(5, width);
			row.createCell(6).setCellValue("进度要求"); 
			sheet.setColumnWidth(6, width);
			row.getCell(6).setCellStyle(headStyle);
			row.createCell(7).setCellValue("状态"); 
			sheet.setColumnWidth(7, width);
			row.getCell(7).setCellStyle(headStyle);
			row.createCell(8).setCellValue("进度"); 
			sheet.setColumnWidth(8, width);
			row.getCell(8).setCellStyle(headStyle);
			for(int i=0; i<specialtyList.size(); i++){
				row.createCell(9+i).setCellValue(specialtyList.get(i).getName());
				sheet.setColumnWidth(9+i, width); 
				row.getCell(9+i).setCellStyle(headStyle);
			} 
			row.createCell(specialtyList.size()+9).setCellValue("上年度计奖比例"); 
			sheet.setColumnWidth(specialtyList.size()+9, 256*25+184);
			row.getCell(specialtyList.size()+9).setCellStyle(headStyle);
			row.createCell(specialtyList.size()+10).setCellValue("归档状态"); 
			sheet.setColumnWidth(specialtyList.size()+10, 256*25+184);
			row.getCell(specialtyList.size()+10).setCellStyle(headStyle);
			row.createCell(specialtyList.size()+11).setCellValue("任务单编号");
			sheet.setColumnWidth(specialtyList.size()+11, 256*60+184);
			row.getCell(specialtyList.size()+11).setCellStyle(headStyle);
			
			//边框样式
			HSSFCellStyle borderStyle = borderStyle(wkb);
			for(int k=0; k<projectList.size(); k++){
				//查找项目主管 
				//String supervisor = projectService.getUsers(projectList.get(k).getId());
				//查找项目负责人
				//String principal = projectService.getPrincipal(projectList.get(k).getId());
				//在sheet里创建第三行  
				row = sheet.createRow(k+2); 
				for(int i=0; i<=12; i++){
					if(i == 0){
						row.createCell(i).setCellValue(projectList.get(k).getSerial());
						row.getCell(i).setCellStyle(borderStyle);
					}else if(i == 1){
						row.createCell(i).setCellValue(projectList.get(k).getNum());
						row.getCell(i).setCellStyle(borderStyle);
					}else if(i == 2){
						row.createCell(i).setCellValue(projectList.get(k).getProShortened());
						row.getCell(i).setCellStyle(borderStyle);
					}else if(i == 3){
						row.createCell(i).setCellValue(DictUtils.getDictLabel(projectList.get(k).getType(), "pro_type", ""));
						row.getCell(i).setCellStyle(borderStyle);
					}/*else if(i == 4){
						row.createCell(i).setCellValue(DictUtils.getDictLabel(projectList.get(k).getDesignType(), "design_type", ""));
						row.getCell(i).setCellStyle(borderStyle);
					}*/else if(i == 4){
						row.createCell(i).setCellValue(projectList.get(k).getSupervisor());
						row.getCell(i).setCellStyle(borderStyle);
					}else if(i == 5){
						row.createCell(i).setCellValue(projectList.get(k).getPrincipal());
						row.getCell(i).setCellStyle(borderStyle);
					}else if(i == 6){
						if(projectList.get(k).getDemand() != null && !projectList.get(k).getDemand().equals("")){
							row.createCell(i).setCellValue(projectList.get(k).getDemand());
							row.getCell(i).setCellStyle(borderStyle2(wkb));
						}else{
							row.createCell(i);
							row.getCell(i).setCellStyle(borderStyle);
						}
					}else if(i == 7){
						row.createCell(i).setCellValue(DictUtils.getDictLabel(projectList.get(k).getState(), "pro_state", ""));
						row.getCell(i).setCellStyle(borderStyle);
					}else if(i == 8){
						row.createCell(i).setCellValue(DictUtils.getDictLabel(projectList.get(k).getPlanState(), "plan_state", ""));
						row.getCell(i).setCellStyle(borderStyle);
					}else if(i == 9){
						for(int idx=0; idx<specialtyList.size(); idx++){
							//根据项目id和专业id查找专业人员
							String userName = projectService.getSpecialtyUserListByProIdAndSpecId(projectList.get(k).getId(),specialtyList.get(idx).getId());
							row.createCell(i+idx).setCellValue(userName);
							row.getCell(i+idx).setCellStyle(borderStyle);
						}
					}else if(i == 10){
						row.createCell(specialtyList.size() + 9).setCellValue(projectList.get(k).getContent());
						row.getCell(specialtyList.size() +9).setCellStyle(borderStyle);
					}else if(i == 11){
						row.createCell(specialtyList.size() + 10).setCellValue(projectList.get(k).getQuestion());
						row.getCell(specialtyList.size() + 10).setCellStyle(borderStyle);
					}else if(i == 12){
						row.createCell(specialtyList.size() + 11).setCellValue(projectList.get(k).getFeature());
						row.getCell(specialtyList.size() + 11).setCellStyle(borderStyle);
					}
					
				}
			}  

    	    //输出Excel文件  
    	    OutputStream output=response.getOutputStream();
			response.reset();
	        response.setContentType("application/octet-stream; charset=utf-8");
	        //20180424修改：输出文件名乱码的处理
	        response.setHeader("Content-Disposition", "attachment; filename="+ new String(fileName.getBytes(),"iso-8859-1")); 
	        wkb.write(output);
    	    output.close();  
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出项目信息记录失败！失败信息："+e.getMessage());
			e.printStackTrace();
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
	 * 边框样式
	 * @param wkb
	 * @return
	 */
	public static HSSFCellStyle borderStyle2(HSSFWorkbook wkb){
		HSSFCellStyle style = wkb.createCellStyle();
		DataFormat format = wkb.createDataFormat();
        style.setDataFormat(format.getFormat("yyyy-MM-dd"));
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
	 * 导入Excel数据

	 */
	@RequiresPermissions("project:project:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Project> list = ei.getDataList(Project.class);
			for (Project project : list){
				try{
					projectService.save(project);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条项目信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条项目信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入项目信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/project/project/?repage";
    }
	
	/**
	 * 下载导入项目信息数据模板
	 */
	@RequiresPermissions("project:project:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "项目信息数据导入模板.xlsx";
    		List<Project> list = Lists.newArrayList(); 
    		new ExportExcel("项目信息数据", Project.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/project/project/?repage";
    }
	
	/**
	 * 得到项目状态
	 */
	@ResponseBody
    @RequestMapping(value = "getProState")
    public int getProState(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			int num = projectService.getProState(id);
			if(num == 5){
				return num;
			}
		}
		return 0;
    }
	
	/**
	 * 改变项目状态
	 */
	@ResponseBody
    @RequestMapping(value = "updateState")
    public AjaxJson updateState(String ids, String state, RedirectAttributes redirectAttributes) {
    	AjaxJson j = new AjaxJson();
		try {
			String idArray[] =ids.split(",");
			for(String id : idArray){
				Project project = new Project(id);
				project.setState(state);
				project.preUpdate();
				//项目状态为已完成
				if(state != null && state.equals("5")){
					//项目进度改成已完成
					project.setPlanState("10");
					//根据项目id修改所有专业状态和进度为已完成
					projectService.updateSpecialtyState(project.getId());
				}
				projectService.updateState(project);
			}
			j.setSuccess(true);
			j.setMsg("改变项目状态成功!");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("改变项目状态失败!");
			addMessage(redirectAttributes, "改变项目信息失败！失败信息："+e.getMessage());
		}
		return j;
    }

}