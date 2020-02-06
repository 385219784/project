package com.jeeplus.modules.gen.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.gen.entity.GenScheme;
import com.jeeplus.modules.gen.entity.GenTable;
import com.jeeplus.modules.gen.mapper.GenTableMapper;
import com.jeeplus.modules.gen.service.GenSchemeService;
import com.jeeplus.modules.gen.service.GenTableService;
import com.jeeplus.modules.gen.util.a;
import com.jeeplus.modules.sys.entity.Menu;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;

import java.io.File;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"${adminPath}/gen/genScheme"})
public class GenSchemeController extends BaseController {

	@Autowired
	public GenSchemeService genSchemeService;

	@Autowired
	public GenTableService genTableService;

	@Autowired
	private GenTableMapper genTableMapper;

	@Autowired
	public SystemService systemService;

	@ModelAttribute
	public GenScheme a(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return this.genSchemeService.a(id);
		}
		return new GenScheme();
	}

	@RequestMapping({"getFileTree"})
	@ResponseBody
	public Object b(String parentNode) {
		JSONArray a = new JSONArray();

		try {
			File[] a4;
			int var6;
			if(StringUtils.isEmpty(parentNode)) {
				File[] a1 = File.listRoots();
				a4 = a1;
				int i = a1.length;

				for(var6 = 0; var6 < i; ++var6) {
					File r = a4[var6];
					JSONObject a2 = new JSONObject();
					a2.put("id", r.getAbsolutePath());
					a2.put("text", r.getPath());
					if(this.a4(r)) {
						a2.put("children", Boolean.valueOf(true));
					} else {
						a2.put("children", Boolean.valueOf(false));
					}

					a.add(a2);
				}
			} else {
				File a3 = new File(parentNode);
				a4 = a3.listFiles();
				File[] var12 = a4;
				var6 = a4.length;

				for(int var13 = 0; var13 < var6; ++var13) {
					File r = var12[var13];
					JSONObject a5 = new JSONObject();
					if(r.isDirectory()) {
						a5.put("id", r.getAbsolutePath());
						a5.put("text", r.getPath());
						if(this.a4(r)) {
							a5.put("children", Boolean.valueOf(true));
						} else {
							a5.put("children", Boolean.valueOf(false));
						}
						a.add(a5);
					}
				}
			}
			return a;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("该文件夹不可选择");
		}
	}
	private boolean a4(File a1) {
		try {
			return a1.listFiles().length != 0;
		} catch (Exception e) {
			this.logger.info(e.getMessage());
		}
		return false;
	}

	@RequestMapping({"fileTree"})
	public String b() {
		return "modules/gen/fileTree";
	}

	@RequiresPermissions({"gen:genScheme:view"})
	@RequestMapping({"list", ""})
	public String c(GenScheme a1, HttpServletRequest request, HttpServletResponse response, Model c) {
		User a2 = UserUtils.getUser();
		if (!a2.isAdmin()) {
			a1.setCreateBy(a2);
		}
		Page<GenScheme> a3 = this.genSchemeService.a1(new Page(request, response), a1);
		c.addAttribute("page", a3);

		return "modules/gen/genSchemeList";
	}

	@RequiresPermissions({"gen:genScheme:view"})
	@RequestMapping({"form"})
	public String c(GenScheme a1, Model b) {
		if (StringUtils.isBlank(a1.getPackageName())) {
			a1.setPackageName("com.jeeplus.modules");
		}
		b.addAttribute("genScheme", a1);
		b.addAttribute("config", a.a7());
		b.addAttribute("tableList", this.genTableService.a3());
		return "modules/gen/genSchemeForm";
	}

	@RequiresPermissions({"gen:genScheme:edit"})
	@RequestMapping({"save"})
	public String d(GenScheme a1, Model a2, RedirectAttributes redirectAttributes) throws Exception {
		if (!beanValidator(a2, a1, new Class[0])) {
			return c(a1, a2);
		}

		String a3 = this.genSchemeService.a2(a1);
		addMessage(redirectAttributes, new String[]{"操作生成方案'" + a1.getName() + "'成功<br/>" + a3});
		return "redirect:" + this.adminPath + "/gen/genScheme/?repage";
	}

	@RequiresPermissions({"gen:genScheme:edit"})
	@RequestMapping({"delete"})
	public String e(GenScheme a1, RedirectAttributes redirectAttributes) {
		this.genSchemeService.a5(a1);
		addMessage(redirectAttributes, new String[]{"删除生成方案成功"});
		return "redirect:" + this.adminPath + "/gen/genScheme/?repage";
	}

	@RequestMapping({"menuForm"})
	public String f(String gen_table_id, String genTableType, Menu a1, Model a2, RedirectAttributes redirectAttributes) {
		if ((a1.getParent() == null) || (a1.getParent().getId() == null)) {
			a1.setParent(new Menu(Menu.getRootId()));
		}
		a1.setParent(this.systemService.getMenu(a1.getParent().getId()));

		GenScheme a3 = this.genSchemeService.b1("gen_table_id", gen_table_id);

		if (a3 != null) {
			a1.setName(a3.getFunctionName());
		}

		a2.addAttribute("menu", a1);
		a2.addAttribute("gen_table_id", gen_table_id);
		a2.addAttribute("genTableType", genTableType);
		return "modules/gen/genMenuForm";
	}

	@ResponseBody
	@RequestMapping({"createMenu"})
	public AjaxJson h(String gen_table_id, String genTableType, Menu a1, Model a2, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		if (Global.isDemoMode().booleanValue()) {
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作！");
			return j;
		}
		GenScheme a3 = this.genSchemeService.b1("gen_table_id", gen_table_id);

		if (a3 == null) {
			j.setSuccess(false);
			j.setMsg("创建菜单失败,请先生成代码!");
			return j;
		}

		if (StringUtils.isBlank(a1.getId())) {
			List<Menu> a7 = Lists.newArrayList();
			List<Menu> a8 = this.systemService.findAllMenu();
			Menu.sortList(a7, a8, a1.getParentId(), false);
			if (a7.size() > 0) {
				a1.setSort(Integer.valueOf(((Menu) a7.get(a7.size() - 1)).getSort().intValue() + 30));
			}
		}
		if ("4".equals(genTableType)) {
			GenTable a4 = new GenTable();
			a4.setParentTable(this.genTableService.a1(gen_table_id).getName());
			GenTable a5 = (GenTable) this.genTableMapper.findList(a4).get(0);
			a3.setGenTable(a5);
		} else {
			a3.setGenTable(this.genTableService.a1(gen_table_id));
		}

		this.genSchemeService.a4(a3, a1);
		j.setSuccess(true);
		j.setMsg("创建菜单'" + a3.getFunctionName() + "'成功<br/>");
		return j;
	}
}