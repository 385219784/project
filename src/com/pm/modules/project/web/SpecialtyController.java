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
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.pm.modules.project.entity.Specialty;
import com.pm.modules.project.service.SpecialtyService;

/**
 * 专业管理Controller
 * @author yt
 * @version 2018-10-15
 */
@Controller
@RequestMapping(value = "${adminPath}/project/specialty")
public class SpecialtyController extends BaseController {

	@Autowired
	private SpecialtyService specialtyService;
	
	@ModelAttribute
	public Specialty get(@RequestParam(required=false) String id) {
		Specialty entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = specialtyService.get(id);
		}
		if (entity == null){
			entity = new Specialty();
		}
		return entity;
	}
	
	/**
	 * 专业类别列表页面
	 */
	@RequiresPermissions("project:specialty:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/project/specialtyList";
	}
	
		/**
	 * 专业类别列表数据
	 */
	@ResponseBody
	@RequiresPermissions("project:specialty:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Specialty specialty, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Specialty> page = specialtyService.findPage(new Page<Specialty>(request, response), specialty); 
		return getBootstrapData(page);
	}

	/**
	 * 增加，编辑专业类别表单页面
	 */
	@RequiresPermissions(value={"project:specialty:add","project:specialty:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Specialty specialty, Model model) {
		model.addAttribute("specialty", specialty);
		return "modules/project/specialtyForm";
	}
	
	/**
	 * 查看专业类别表单页面
	 */
	@RequiresPermissions(value={"project:specialty:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(Specialty specialty, Model model) {
		model.addAttribute("specialty", specialty);
		return "modules/project/specialtyView";
	}

	/**
	 * 保存专业类别
	 */
	@ResponseBody
	@RequiresPermissions(value={"project:specialty:add","project:specialty:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Specialty specialty, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, specialty)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		specialtyService.save(specialty);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存专业类别成功");
		return j;
	}
	
	/**
	 * 删除专业类别
	 */
	@ResponseBody
	@RequiresPermissions("project:specialty:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Specialty specialty, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		specialtyService.delete(specialty);
		j.setMsg("删除专业类别成功");
		return j;
	}
	
	/**
	 * 批量删除专业类别
	 */
	@ResponseBody
	@RequiresPermissions("project:specialty:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			specialtyService.delete(specialtyService.get(id));
		}
		j.setMsg("删除专业类别成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("project:specialty:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Specialty specialty, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "专业类别"+DateUtils.getDate("yyyyMMdd")+".xlsx";
            Page<Specialty> page = specialtyService.findPage(new Page<Specialty>(request, response, -1), specialty);
    		new ExportExcel("专业类别", Specialty.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出专业类别记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("project:specialty:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Specialty> list = ei.getDataList(Specialty.class);
			for (Specialty specialty : list){
				try{
					specialtyService.save(specialty);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条专业类别记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条专业类别记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入专业类别失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/project/specialty/?repage";
    }
	
	/**
	 * 下载导入专业类别数据模板
	 */
	@RequiresPermissions("project:specialty:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "专业类别数据导入模板.xlsx";
    		List<Specialty> list = Lists.newArrayList(); 
    		new ExportExcel("专业类别数据", Specialty.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/project/specialty/?repage";
    }

	/**
	 * 查找所有专业类别列表
	 */
	@ResponseBody
	@RequestMapping(value = "getSpecialtyAll")
	public List<Specialty> getSpecialtyAll() {
		return specialtyService.findAllSpecialtyList();
	}
	
	/**
	 * 判断是否重复的排序
	 */
	@ResponseBody
	@RequestMapping(value = "findSort")
	public Integer findSort(String sort) {
		return specialtyService.findSort(sort);
	}
	
	
	
	
	/**
	 * 专业类别列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "dataList")
	public Map<String, Object> dataList(Specialty specialty,String projectId, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user=UserUtils.getUser();
		specialty.setRemarks(projectId);
		specialty.setUserSpecialtyId(user.getId());
		Page<Specialty> page = specialtyService.findPagedataList(new Page<Specialty>(request, response), specialty); 
		return getBootstrapData(page);
	}

	
	
	
	
}