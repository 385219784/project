/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.project.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.pm.modules.project.entity.ProjectWorkCase;
import com.pm.modules.project.service.ProjectService;
import com.pm.modules.project.service.ProjectWorkCaseService;

/**
 * 个人项目Controller
 * @author yt
 * @version 2018-10-16
 */
@Controller
@RequestMapping(value = "${adminPath}/project/projectWorkCase")
public class ProjectWorkCaseController extends BaseController {

	@Autowired
	private ProjectWorkCaseService projectWorkCaseService;
	
	@Autowired
	private ProjectService projectService;
	
	@ModelAttribute
	public ProjectWorkCase get(@RequestParam(required=false) String id) {
		ProjectWorkCase entity = null;
		if (StringUtils.isNotBlank(id)){
			String[] idStr = id.split(",");
			entity = new ProjectWorkCase();
			entity.setProId(idStr[0]);
			entity.setSpecId(idStr[1]);
			entity = projectWorkCaseService.get(entity);
			entity.setId(projectWorkCaseService.getId(entity));
			//根据项目id和专业id,用户id查找工作情况
			ProjectWorkCase projectWork = projectWorkCaseService.getProjectWork(entity);
			if(projectWork != null){
				entity.setContent(projectWork.getContent());
				entity.setPlan(projectWork.getPlan());
				entity.setFinishDate(projectWork.getFinishDate());
				entity.setDifficulty(projectWork.getDifficulty());
				entity.setSpecState(projectWork.getSpecState());
			}
		}
		if (entity == null){
			entity = new ProjectWorkCase();
		}
		return entity;
	}
	
	/**
	 * 个人工作情况列表页面
	 */
	@RequiresPermissions("project:projectWorkCase:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/project/projectWorkCaseList";
	}
	
	/**
	 * 工作情况汇总列表页面
	 */
	@RequiresPermissions("project:projectCollect:list")
	@RequestMapping(value = {"Collect"})
	public String Collect() {
		return "modules/project/projectCollectList";
	}
	
		/**
	 * 个人工作情况列表数据
	 */
	@ResponseBody
	@RequiresPermissions("project:projectWorkCase:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ProjectWorkCase projectWorkCase, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectWorkCase> page = projectWorkCaseService.findPage(new Page<ProjectWorkCase>(request, response), projectWorkCase); 
		return getBootstrapData(page);
	}
	
	/**
	 * 工作情况汇总列表数据
	 */
	@ResponseBody
	@RequiresPermissions("project:projectCollect:list")
	@RequestMapping(value = "datas")
	public Map<String, Object> datas(ProjectWorkCase projectWorkCase, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectWorkCase> page = projectWorkCaseService.findPages(new Page<ProjectWorkCase>(request, response), projectWorkCase); 
		return getBootstrapData(page);
	}

	/**
	 * 增加，编辑个人工作情况表单页面
	 */
	@RequiresPermissions(value={"project:projectWorkCase:add","project:projectWorkCase:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProjectWorkCase projectWorkCase, Model model) {
		model.addAttribute("projectWorkCase", projectWorkCase);
		return "modules/project/projectWorkCaseForm";
	}
	
	/**
	 * 查看个人工作情况表单页面
	 */
	@RequiresPermissions(value={"project:projectWorkCase:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(ProjectWorkCase projectWorkCase, Model model) {
		model.addAttribute("projectWorkCase", projectWorkCase);
		return "modules/project/projectWorkCaseView";
	}

	/**
	 * 保存个人工作情况
	 */
	@ResponseBody
	@RequiresPermissions(value={"project:projectWorkCase:add","project:projectWorkCase:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ProjectWorkCase projectWorkCase, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, projectWorkCase)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		try{
			projectWorkCaseService.save(projectWorkCase);//新建或者编辑保存
			j.setSuccess(true);
			j.setMsg("保存成功");
		}catch(Exception e){
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("保存个人工作情况失败");
		}
		return j;
	}
	
	/**
	 * 删除个人工作情况
	 */
	@ResponseBody
	@RequiresPermissions("project:projectWorkCase:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ProjectWorkCase projectWorkCase, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		projectWorkCaseService.delete(projectWorkCase);
		j.setMsg("删除个人工作情况成功");
		return j;
	}
	
	/**
	 * 批量删除个人工作情况
	 */
	@ResponseBody
	@RequiresPermissions("project:projectWorkCase:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			projectWorkCaseService.delete(projectWorkCaseService.get(id));
		}
		j.setMsg("删除个人工作情况成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("project:projectWorkCase:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ProjectWorkCase projectWorkCase, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "我的项目"+DateUtils.getDate("yyyyMMdd")+".xlsx";
            Page<ProjectWorkCase> page = projectWorkCaseService.findPage(new Page<ProjectWorkCase>(request, response, -1), projectWorkCase);
    		new ExportExcel("我的项目", ProjectWorkCase.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出我的项目记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("project:projectCollect:export")
    @RequestMapping(value = "exportFile", method=RequestMethod.POST)
    public AjaxJson export(ProjectWorkCase projectWorkCase, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "个人项目汇总"+DateUtils.getDate("yyyyMMdd")+".xlsx";
            Page<ProjectWorkCase> page = projectWorkCaseService.findPages(new Page<ProjectWorkCase>(request, response, -1), projectWorkCase);
    		new ExportExcel("个人项目汇总", ProjectWorkCase.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出个人项目汇总记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("project:projectWorkCase:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProjectWorkCase> list = ei.getDataList(ProjectWorkCase.class);
			for (ProjectWorkCase projectWorkCase : list){
				try{
					projectWorkCaseService.save(projectWorkCase);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条个人工作情况记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条个人工作情况记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入个人工作情况失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/project/projectWorkCase/?repage";
    }
	
	/**
	 * 下载导入个人工作情况数据模板
	 */
	@RequiresPermissions("project:projectWorkCase:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "个人工作情况数据导入模板.xlsx";
    		List<ProjectWorkCase> list = Lists.newArrayList(); 
    		new ExportExcel("个人工作情况数据", ProjectWorkCase.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/project/projectWorkCase/?repage";
    }
	
	/**
	 * 得到项目状态
	 */
	@ResponseBody
    @RequestMapping(value = "getProState")
    public int getProState(String ids, RedirectAttributes redirectAttributes) {
		String id = ids.split(",")[0];
		int num = projectService.getProState(id);
		if(num == 5){
			return num;
		}
		return 0;
    }
}