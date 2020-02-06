package com.jeeplus.modules.gen.web;

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.gen.entity.GenCustomObj;
import com.jeeplus.modules.gen.entity.GenScheme;
import com.jeeplus.modules.gen.entity.GenTable;
import com.jeeplus.modules.gen.service.GenCustomObjService;
import com.jeeplus.modules.gen.service.GenSchemeService;
import com.jeeplus.modules.gen.service.GenTableService;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"${adminPath}/gen/genCustomObj"})
public class GenCustomObjController extends BaseController {

	@Autowired
	private GenCustomObjService genCustomObjService;

	@Autowired
	public GenSchemeService genSchemeService;

	@Autowired
	public GenTableService genTableService;

	@ModelAttribute
	public GenCustomObj a(@RequestParam(required = false) String id) {
		GenCustomObj a1 = null;
		if (StringUtils.isNotBlank(id)) {
			a1 = this.genCustomObjService.get(id);
		}
		if (a1 == null) {
			a1 = new GenCustomObj();
		}
		return a1;
	}

	@RequiresPermissions({"gen:genCustomObj:list"})
	@RequestMapping({"list", ""})
	public String a() {
		return "modules/gen/genCustomObjList";
	}

	@ResponseBody
	@RequiresPermissions({"gen:genCustomObj:list"})
	@RequestMapping({"data"})
	public Map<String, Object> a(GenCustomObj b, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page a = this.genCustomObjService.findPage(new Page(request, response), b);
		return getBootstrapData(a);
	}

	@RequiresPermissions(value = {"gen:genCustomObj:view", "gen:genCustomObj:add", "gen:genCustomObj:edit"}, logical = Logical.OR)
	@RequestMapping({"form"})
	public String d(GenCustomObj a, Model model) {
		model.addAttribute("genCustomObj", a);
		return "modules/gen/genCustomObjForm";
	}

	@RequiresPermissions(value = {"gen:genCustomObj:add", "gen:genCustomObj:edit"}, logical = Logical.OR)
	@RequestMapping({"save"})
	public String e(GenCustomObj c, Model model, RedirectAttributes redirectAttributes) throws Exception {
		if (!beanValidator(model, c, new Class[0])) {
			return d(c, model);
		}
		this.genCustomObjService.save(c);
		addMessage(redirectAttributes, new String[]{"保存自定义对象成功"});
		return new StringBuilder().append("redirect:").append(Global.getAdminPath()).append("/gen/genCustomObj/?repage").toString();
	}

	@ResponseBody
	@RequestMapping({"add"})
	public AjaxJson h(String gen_table_id, String genTableType, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		GenScheme a1 = this.genSchemeService.b1("gen_table_id", gen_table_id);

		if (a1 == null) {
			j.setSuccess(false);
			j.setMsg("添加JAVA自定义对象失败,请先生成代码!或者你可以到自定义对象管理中手动添加。");
			return j;
		}
		GenTable b1 = this.genTableService.a1(a1.getGenTable().getId());
		GenCustomObj c1 = new GenCustomObj();
		c1.setLabel(b1.getClassName());
		String a2 = b1.getClassName();
		if (StringUtils.isNotBlank(a2)) {
			a2 = new StringBuilder().append(a2.substring(0, 1).toLowerCase()).append(a2.substring(1)).toString();
		}
		String d = new StringBuilder().append(a1.getPackageName()).append(".").append(a1.getModuleName()).append(".entity").append(StringUtils.isBlank(a1.getSubModuleName()) ? "" : new StringBuilder().append(".").append(StringUtils.lowerCase(a1.getSubModuleName())).toString()).append(".").append(b1.getClassName()).toString();
		c1.setValue(d);
		c1.setTableName(b1.getName());

		String a3 = new StringBuilder().append("/").append(a1.getModuleName())
				.append(StringUtils.isNotBlank(a1
						.getSubModuleName()) ? new StringBuilder().append("/")
						.append(StringUtils.lowerCase(a1
								.getSubModuleName())).toString() : "").append("/").append(a2).toString();

		if (("3".equals(genTableType)) || ("4".equals(genTableType)))
			c1.setDataUrl(new StringBuilder().append(a3).append("/treeData").toString());
		else {
			c1.setDataUrl(new StringBuilder().append(a3).append("/data").toString());
		}
		this.genCustomObjService.save(c1);
		j.setMsg("添加自定义对象成功！请到自定义对象管理中查看。");
		return j;
	}

	@ResponseBody
	@RequiresPermissions({"gen:genCustomObj:del"})
	@RequestMapping({"delete"})
	public AjaxJson h(GenCustomObj genCustomObj, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		this.genCustomObjService.delete(genCustomObj);
		j.setMsg("删除自定义对象成功");
		return j;
	}

	@ResponseBody
	@RequiresPermissions({"gen:genCustomObj:del"})
	@RequestMapping({"deleteAll"})
	public AjaxJson x(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String[] a1 = ids.split(",");
		for (String id : a1) {
			this.genCustomObjService.delete(this.genCustomObjService.get(id));
		}
		j.setMsg("删除自定义对象成功");
		return j;
	}

	@ResponseBody
	@RequiresPermissions({"gen:genCustomObj:export"})
	@RequestMapping(value = {"export"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
	public AjaxJson h(GenCustomObj a, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			String a1 = new StringBuilder().append("自定义对象").append(DateUtils.getDate("yyyyMMddHHmmss")).append(".xlsx").toString();
			Page b = this.genCustomObjService.findPage(new Page(request, response, -1), a);
			new ExportExcel("自定义对象", GenCustomObj.class).setDataList(b.getList()).write(response, a1).dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg(new StringBuilder().append("导出自定义对象记录失败！失败信息：").append(e.getMessage()).toString());
		}
		return j;
	}

	@RequiresPermissions({"gen:genCustomObj:import"})
	@RequestMapping(value = {"import"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
	public String e(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int a1 = 0;
			int a2 = 0;
			StringBuilder a3 = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<GenCustomObj> a4 = ei.getDataList(GenCustomObj.class, new int[0]);
			for (GenCustomObj a7 : a4) {
				try {
					this.genCustomObjService.save(a7);
					a1++;
				} catch (ConstraintViolationException ex) {
					a2++;
				} catch (Exception ex) {
					a2++;
				}
			}
			if (a2 > 0) {
				a3.insert(0, new StringBuilder().append("，失败 ").append(a2).append(" 条自定义对象记录。").toString());
			}
			addMessage(redirectAttributes, new String[]{new StringBuilder().append("已成功导入 ").append(a1).append(" 条自定义对象记录").append(a3).toString()});
		} catch (Exception e) {
			addMessage(redirectAttributes, new String[]{new StringBuilder().append("导入自定义对象失败！失败信息：").append(e.getMessage()).toString()});
		}
		return new StringBuilder().append("redirect:").append(Global.getAdminPath()).append("/gen/genCustomObj/?repage").toString();
	}

	@RequiresPermissions({"gen:genCustomObj:import"})
	@RequestMapping({"import/template"})
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "自定义对象数据导入模板.xlsx";
			List list = Lists.newArrayList();
			new ExportExcel("自定义对象数据", GenCustomObj.class, 1, new int[0]).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, new String[]{new StringBuilder().append("导入模板下载失败！失败信息：").append(e.getMessage()).toString()});
		}
		return new StringBuilder().append("redirect:").append(Global.getAdminPath()).append("/gen/genCustomObj/?repage").toString();
	}
}