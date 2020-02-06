package com.jeeplus.modules.gen.web;

import com.google.common.collect.Maps;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.PropertiesLoader;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.gen.entity.GenConfig;
import com.jeeplus.modules.gen.entity.GenCustomObj;
import com.jeeplus.modules.gen.entity.GenDict;
import com.jeeplus.modules.gen.entity.GenScheme;
import com.jeeplus.modules.gen.entity.GenTable;
import com.jeeplus.modules.gen.entity.GenTableColumn;
import com.jeeplus.modules.gen.entity.GenTemplate;
import com.jeeplus.modules.gen.mapper.GenDataBaseDictMapper;
import com.jeeplus.modules.gen.mapper.GenTableColumnMapper;
import com.jeeplus.modules.gen.mapper.GenTableMapper;
import com.jeeplus.modules.gen.mapper.GenTemplateMapper;
import com.jeeplus.modules.gen.service.GenCustomObjService;
import com.jeeplus.modules.gen.service.GenSchemeService;
import com.jeeplus.modules.gen.service.GenTableService;
import com.jeeplus.modules.gen.service.GenTemplateService;
import com.jeeplus.modules.gen.template.FreemarkerHelper;
import com.jeeplus.modules.gen.util.a;
import com.jeeplus.modules.gen.util.d;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"${adminPath}/gen/genTable"})
public class GenTableController extends BaseController {

	@Autowired
	public GenTemplateService genTemplateService;

	@Autowired
	public GenTableService genTableService;

	@Autowired
	private GenTableMapper genTableMapper;

	@Autowired
	public GenSchemeService genSchemeService;

	@Autowired
	public GenTemplateMapper genTemplateMapper;

	@Autowired
	private GenTableColumnMapper genTableColumnMapper;

	@Autowired
	private GenDataBaseDictMapper genDataBaseDictMapper;

	@Autowired
	private GenCustomObjService genCustomObjService;
	private static String b = "200";

	private static String b1 = "h";

	private static String b2 = "t";

	private static String b3 = "p";

	private static String b4 = "80";

	private static String b5 = "/";

	private static String c = "197";

	private static PropertiesLoader a6 = new PropertiesLoader(new String[]{"/properties/license.properties"});

	private Map<String, String> a8 = Maps.newHashMap();
	private static String d = "196";

	private static String d1 = "http://v.jeeplus.org/a";

	private static final String a7 = d1 + "/auth/jeeplusUser/getGenTemplate?";

	private static final String a9 = d1 + "/auth/jeeplusUser/initGenTemplate?";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAutoGrowCollectionLimit(1024);
	}

	/**
	 * 验证注册码
	 * @param a1
	 * @return 12：无效
	 * @throws Exception
	 */
	private static String a1(String a1) throws Exception {
		System.out.println("url is:" + a1);
		URL a2 = new URL(a1);
		HttpURLConnection a3 = (HttpURLConnection) a2.openConnection();
		a3.setRequestMethod("POST");
		a3.setRequestProperty("Content-type", "text/html");
		a3.setRequestProperty("Accept-Charset", "utf-8");
		a3.setRequestProperty("contentType", "utf-8");
		a3.setConnectTimeout(3000);
		a3.setReadTimeout(3000);
		a3.connect();
		InputStream a4 = a3.getInputStream();

		Reader a5 = new InputStreamReader(a4, "UTF-8");
		BufferedReader a6 = new BufferedReader(a5);
		String a7 = null;
		StringBuffer a8 = new StringBuffer();
		while ((a7 = a6.readLine()) != null) {
			a8.append(a7);
		}
		a5.close();
		a3.disconnect();
		return a8.toString();
	}

	private String a2(String a1, String a2, String a3, String a4, String a5, String a6) {
		if ((a3 == null) || ("".equals(a3)) || (a3.startsWith("输入错误"))) {
			a3 = a4;
		}
		String a7 = "1";
		try {
			a7 = a1(a9 + "mac=" + a7(a3, "UTF-8") + "&productID=" + a1 + "&license=" + a2 + "&ip=" +
					a7(a4, "UTF-8") +
					"&type=" +
					a7(a5, "UTF-8") +
					"&version=" +
					a7(a6, "UTF-8") +
					"&inittime=" +
					a7(a.a12(), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a7;
	}

	private GenTemplate a3(String a4, String a1, String a2) {
		if ((a4 == null) || ("".equals(a4)) || (a4.startsWith("输入错误"))) {
			a4 = a2;
		}
		GenTemplate a3 = new GenTemplate();
		try {
			String a11 = a1(a7 + "mac=" +
					a7(a4, "UTF-8") +
					"&productID=" +
					a7(a1, "UTF-8"));

			a3.setName(a11);
		} catch (Exception e) {
			a3.setName("-2");
		}
		return a3;
	}

	private static String a7(String a1, String a2) throws UnsupportedEncodingException {
		if (a1 == null)
			a1 = "";
		return URLEncoder.encode(a1, "UTF-8");
	}

	private String a7(String a1) {
		String a2 = (String) this.a8.get(a1);
		if (a2 == null) {
			a2 = a6.getProperty(a1);
			this.a8.put(a1, a2 != null ? a2 : "");
		}
		return a2;
	}

	public GenTable a0(GenTable a1) {
		if (StringUtils.isNotBlank(a1.getId())) {
			return this.genTableService.a1(a1.getId());
		}
		return a1;
	}

	@RequiresPermissions({"gen:genTable:list"})
	@RequestMapping({"list", ""})
	public String a11(GenTable genTable, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException, d {
		return "modules/gen/genTableList";
	}

	@ResponseBody
	@RequiresPermissions({"gen:genTable:list"})
	@RequestMapping({"data"})
	public Map<String, Object> a(GenTable b2, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		b2 = a0(b2);
		User a1 = UserUtils.getUser();
		if (!a1.isAdmin()) {
			b2.setCreateBy(a1);
		}
		Page<GenTable> a6 = this.genTableService.a2(new Page(request, response), b2);

		return getBootstrapData(a6);
	}

	@RequiresPermissions(value = {"gen:genTable:view", "gen:genTable:add", "gen:genTable:edit"}, logical = Logical.OR)
	@RequestMapping({"form"})
	public String a(GenTable c1, HttpServletResponse response, Model model) throws IOException {
		c1 = a0(c1);
		model.addAttribute("genTable", c1);

		List<GenCustomObj> f = this.genCustomObjService.findList(new GenCustomObj());
		GenConfig f2 = a.a7();
		for (GenCustomObj genObj : f) {
			GenDict f1 = new GenDict();
			f1.setLabel(genObj.getLabel());
			f1.setValue(genObj.getValue());
			f1.setDescription(genObj.getRemarks());
			f2.getJavaTypeList().add(f1);
		}

		model.addAttribute("config", f2);
		model.addAttribute("tableList", this.genTableService.a3());
		return "modules/gen/genTableForm";
	}

	@ResponseBody
	@RequiresPermissions(value = {"gen:genTable:add", "gen:genTable:edit"}, logical = Logical.OR)
	@RequestMapping({"save"})
	public AjaxJson a4(GenTable a2, Model model, RedirectAttributes redirectAttributes, HttpServletResponse response) throws IOException {
		GenTable a1 = a0(a2);
		a2.setOldComments(a1.getOldComments());
		a2.setOldName(a1.getOldName());
		a2.setOldGenIdType(a1.getOldGenIdType());
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, a2, new Class[0])) {
			j.setSuccess(false);
			j.setMsg("参数错误！");
			return j;
		}

		if ((StringUtils.isBlank(a2.getId())) && (!this.genTableService.a5(a2.getName()))) {
			j.setSuccess(false);
			j.setMsg("添加失败！" + a2.getName() + " 记录已存在！");

			return j;
		}

		if ((StringUtils.isBlank(a2.getId())) &&(!this.genTableService.a7(a2.getName()))) {
			j.setSuccess(false);
			j.setMsg("添加失败！" + a2.getName() + "表已经在数据库中存在,请从数据库导入表单！");

			return j;
		}

		if ((StringUtils.isNotBlank(a2.getId())) &&(!a2.getName().equals(a1.getName())) && (!this.genTableService.a5(a2.getName()))) {
			j.setSuccess(false);
			j.setMsg("重命名失败！" + a2.getName() + " 记录已存在！");

			return j;
		}

		if ((StringUtils.isNotBlank(a2.getId())) &&(!a2.getName().equals(a1.getName())) && (!this.genTableService.a7(a2.getName()))) {
			j.setSuccess(false);
			j.setMsg("重命名失败！" + a2.getName() + "表已经在数据库中存在,请从数据库导入表单！");

			return j;
		}

		List<GenTableColumn> a4 = a2.getColumnList();
		for (GenTableColumn a5 : a4) {
			GenCustomObj genCuObj;
			if ("gridselect".equals(a5.getShowType())) {
				if (!"This".equals(a5.getJavaType())) {
					genCuObj = (GenCustomObj) this.genCustomObjService.findUniqueByProperty("value", a5.getJavaType());
					if (genCuObj == null) {
						j.setSuccess(false);
						j.setMsg(a5.getJavaField() + "字段对应的显示表单类型你选择了【gridselect】或者【树选择控件】，<br/>但是对应的java类型你没有关联自定义java对象！<br/>");
						return j;
					}
					a5.setDataUrl(genCuObj.getDataUrl());
					a5.setTableName(genCuObj.getTableName());
				}
			} else if (("treeselect".equals(a5.getShowType())) &&
					(!"This".equals(a5.getJavaType())) && (!a5.getName().equals(a2.getParentTableFk()))) {
				genCuObj = (GenCustomObj) this.genCustomObjService.findUniqueByProperty("value", a5.getJavaType());
				if (genCuObj == null) {
					j.setSuccess(false);
					j.setMsg(a5.getJavaField() + "字段对应的显示表单类型你选择了【gridselect】或者【树选择控件】，<br/>但是对应的java类型你没有关联自定义java对象！<br/>");
					return j;
				}
				a5.setDataUrl(genCuObj.getDataUrl());
				a5.setTableName(genCuObj.getTableName());
			}

		}

		if ((StringUtils.isNotBlank(a2.getOldName())) && (!this.genTableService.a7(a2.getOldName())))
			this.genTableService.b2(a2);
		else {
			this.genTableService.b1(a2);
		}

		j.setSuccess(true);
		j.setMsg("保存业务表'" + a2.getName() + "'成功");
		return j;
	}

	@ResponseBody
	@RequiresPermissions({"gen:genTable:importDb"})
	@RequestMapping({"saveTableFromDB"})
	public AjaxJson a3(String name, Model model, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		StringBuffer a1 = new StringBuffer();
		String[] a2 = name.split(",");
		for (String a : a2) {
			if (StringUtils.isNotBlank(a)) {
				if (!this.genTableService.a5(a)) {
					a1.append("<font color='red'>数据库导入表单" + a + " 失败，表已经添加!</font><br/>");
				} else {
					GenTable a3 = new GenTable();
					a3.setName(a);
					a3 = this.genTableService.a5(a3);
					a3.setTableType("0");
					this.genTableService.d(a3);
					a1.append("<font color='green'>数据库导入表单'" + a3.getName() + "'成功!</font><br/>");
				}
			}
		}

		j.setSuccess(true);
		j.setMsg(a1.toString());
		return j;
	}

	@RequiresPermissions({"gen:genTable:importDb"})
	@RequestMapping({"importTableFromDB"})
	public String a1(GenTable genTable, Model model, RedirectAttributes redirectAttributes) {
		return "modules/gen/importTableFromDB";
	}

	@ResponseBody
	@RequiresPermissions({"gen:genTable:importDb"})
	@RequestMapping({"importTableData"})
	public Map<String, Object> a(GenTable genTable, Model model, RedirectAttributes redirectAttributes) {
		List<GenTable> a = this.genTableService.a4(genTable);

		Map<String, Object> x = new HashMap();
		x.put("rows", a);
		x.put("total", Integer.valueOf(a.size()));
		return x;
	}

	@ResponseBody
	@RequiresPermissions({"gen:genTable:del"})
	@RequestMapping({"delete"})
	public AjaxJson a2(GenTable genTable, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		genTable = a0(genTable);
		this.genTableService.e(genTable);
		this.genSchemeService.a5(this.genSchemeService.b1("gen_table_id", genTable.getId()));
		j.setSuccess(true);
		j.setMsg("移除业务表记录成功");
		return j;
	}

	@ResponseBody
	@RequiresPermissions({"gen:genTable:del"})
	@RequestMapping({"deleteDb"})
	public AjaxJson a1(GenTable x, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		if (Global.isDemoMode().booleanValue()) {
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作！");
			return j;
		}
		x = a0(x);
		this.genTableService.e(x);
		this.genSchemeService.a5(this.genSchemeService.b1("gen_table_id", x.getId()));
		StringBuffer y = new StringBuffer();
		String c = Global.getConfig("jdbc.type");
		if ("mysql".equals(c)) {
			y.append("drop table if exists " + x.getName() + " ;");
		} else if ("oracle".equals(c))
			try {
				y.append("DROP TABLE " + x.getName() + "");
			} catch (Exception localException) {
			}
		else if (("mssql".equals(c)) || ("sqlserver".equals(c))) {
			y.append("if exists (select * from sysobjects where id = object_id(N'[" +
					x.getName() + "]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)  drop table [" +
					x.getName() + "]");
		}

		this.genTableService.f(y.toString());
		j.setSuccess(true);
		j.setMsg("删除业务表记录和数据库表成功!");
		return j;
	}

	@ResponseBody
	@RequiresPermissions({"gen:genTable:del"})
	@RequestMapping({"deleteAll"})
	public AjaxJson b3(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			this.genTableService.e(this.genTableService.a1(id));
		}
		j.setSuccess(true);
		j.setMsg("移除业务表成功!");
		return j;
	}

	@ResponseBody
	@RequiresPermissions({"gen:genTable:synchDb"})
	@RequestMapping({"synchDb"})
	public AjaxJson a4(GenTable a4, String isForce, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		if (Global.isDemoMode().booleanValue()) {
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作！");
			return j;
		}
		String a1 = Global.getConfig("jdbc.type");
		a4 = a0(a4);
		List<GenTableColumn> a2 = a4.getColumnList();
		List<GenTableColumn> a3 = a4.getAllColumnList();
		String b4;
		GenTable a6;
		StringBuffer a5;
		if ("mysql".equals(a1)) {
			if (("1".equals(isForce)) && (a4.getOldName() != null)) {
				a5 = new StringBuffer();
				if (!a4.getName().equalsIgnoreCase(a4.getOldName())) {
					a5.append("ALTER  TABLE " + a4.getOldName() + " RENAME TO " + a4.getName() + ";");
					this.genTableService.f("ALTER  TABLE " + a4.getOldName() + " RENAME TO " + a4.getName() + ";");
					a6 = (GenTable) this.genTableMapper.get(a4);
					a6.setOldName(a4.getName());
					this.genTableMapper.update(a6);
				}

				if (!a4.getComments().equals(a4.getOldComments())) {
					a5.append("alter table " + a4.getName() + " comment '" + a4.getComments() + "';");
					this.genTableService.f("alter table " + a4.getName() + " comment '" + a4.getComments() + "';");
					a6 = (GenTable) this.genTableMapper.get(a4);
					a6.setOldComments(a4.getComments());
					a6.setOldGenIdType(a4.getGenIdType());
					this.genTableMapper.update(a6);
				}

				String a7;
				for (GenTableColumn a8 : a3) {
					a7 = a8.getOldName();
					if (a8.getDelFlag().equals("1") && a3(a4, a7)) {
						a5.append("alter table " + a4.getName() + " drop " + a7 + ";");
						this.genTableService.f("alter table " + a4.getName() + " drop " + a7 + ";");
						this.genTableColumnMapper.delete(a8);
					}
				}

				String a10, a11, a12;
				GenTableColumn a13;
				for (GenTableColumn a9 : a3) {
					a10 = a9.getOldName();
					a11 = a9.getName();
					if ((!a9.getDelFlag().equals("1")) && (a10 != null)) {
						if ((!a11.equals(a10)) || (!a9.getJdbcType().equals(a9.getOldJdbcType())) ||
								(!a9.getComments().equals(a9.getOldComments()))) {
							a12 = StringUtils.isBlank(a9.getOldName()) ? a9.getName() : a9.getOldName();
							a5.append("alter table " + a4.getName() + " change  " + a10 + " " + a11 + " " + a9.getJdbcType() + " comment '" + a9.getComments() + "';");
							this.genTableService.f("alter table " + a4.getName() + " change  " + a12 + " " + a9.getName() + " " + a9.getJdbcType() + " comment '" + a9.getComments() + "';");

							a13 = (GenTableColumn) this.genTableColumnMapper.get(a9.getId());
							a13.setOldComments(a9.getComments());
							a13.setOldIsPk(a9.getIsPk());
							a13.setOldJdbcType(a9.getJdbcType());
							a13.setOldName(a9.getName());
							this.genTableColumnMapper.update(a13);
						}

					}

				}

				String b1, b2;
				GenTableColumn b3;
				for (GenTableColumn a9 : a3) {
					b1 = a9.getOldName();
					b2 = a9.getName();
					if ((!a9.getDelFlag().equals("1")) && (b1 == null)) {
						a5.append("alter table " + a4.getName() + " add " + b2 + " " + a9.getJdbcType() + " comment '" + a9.getComments() + "';");
						this.genTableService.f("alter table " + a4.getName() + " add " + a9.getName() + " " + a9.getJdbcType() + " comment '" + a9.getComments() + "';");
						b3 = (GenTableColumn) this.genTableColumnMapper.get(a9.getId());
						b3.setOldComments(a9.getComments());
						b3.setOldIsPk(a9.getIsPk());
						b3.setOldJdbcType(a9.getJdbcType());
						b3.setOldName(a9.getName());
						this.genTableColumnMapper.update(b3);
					}
				}

				if ((a4.getGenIdType() != null) && (!a4.getGenIdType().equals(a4.getOldGenIdType()))) {
					for (GenTableColumn a9 : a3) {
						if ((!a9.getDelFlag().equals("1")) && (a9.getName() != null) &&
								(a9.getIsPk().equals("1"))) {
							if (a4.getGenIdType().equals("2")) {
								b2 = a9.getJdbcType();
								if ((!b2.toLowerCase().contains("int")) && (!b2.toLowerCase().contains("integer"))) {
									b2 = "integer";
									a9.setJdbcType(b2);
									b3 = (GenTableColumn) this.genTableColumnMapper.get(a9.getId());
									b3.setJdbcType(b2);
									b3.setOldJdbcType(b2);
									this.genTableColumnMapper.update(b3);
								}
								this.genTableService.f("alter table " + a4.getName() + " change   " + a9.getName() + " " + a9.getName() + " " + b2 + " auto_increment ;");
							} else {
								b2 = a9.getJdbcType();
								if (!b2.toLowerCase().contains("varchar")) {
									b2 = "varchar(64)";
									a9.setJdbcType(b2);
									b3 = (GenTableColumn) this.genTableColumnMapper.get(a9.getId());
									b3.setJdbcType(b2);
									b3.setOldJdbcType(b2);
									this.genTableColumnMapper.update(b3);
								}
								this.genTableService.f("alter table " + a4.getName() + " change   " + a9.getName() + " " + a9.getName() + " " + b2 + " ;");
							}
						}
					}
				}

				a11 = "";
				String b11 = "";
				for (GenTableColumn a9 : a2) {
					if (a9.getIsPk().equals("1")) {
						a11 = a11 + a9.getName() + ",";
					}

					if ("1".equals(a9.getOldIsPk())) {
						b11 = b11 + a9.getName() + ",";
					}

				}

				if (!b11.equals(a11)) {
					a5.append("alter table " + a4.getName() + " drop primary key;");
					this.genTableService.f("alter table " + a4.getName() + " drop primary key;");
					GenTableColumn b5;
					for (GenTableColumn a9 : a2) {
						if ("1".equals(a9.getOldIsPk())) {
							b5 = (GenTableColumn) this.genTableColumnMapper.get(a9.getId());
							b5.setOldIsPk("0");
							this.genTableColumnMapper.update(b5);
						}
					}

					if (a11.length() > 0) {
						a5.append("alter table " + a4.getName() + " add  CONSTRAINT PK_SJ_RESOURCE_CHARGES PRIMARY KEY(" + a11.substring(0, a11.length() - 1) + ");");
						this.genTableService.f("alter table " + a4.getName() + " add  CONSTRAINT PK_SJ_RESOURCE_CHARGES PRIMARY KEY(" + a11.substring(0, a11.length() - 1) + ");");
						for (GenTableColumn a9 : a2) {
							if ("1".equals(a9.getIsPk())) {
								GenTableColumn x = (GenTableColumn) this.genTableColumnMapper.get(a9.getId());
								x.setOldIsPk("1");
								this.genTableColumnMapper.update(x);
							}
						}

					}

				}

				j.setMsg("同步数据库表成功!");
			} else {
				StringBuffer b3 = new StringBuffer();
				if (StringUtils.isNotBlank(a4.getOldName()))
					b3.append("drop table if exists " + a4.getOldName() + " ;");
				else {
					b3.append("drop table if exists " + a4.getName() + " ;");
				}
				this.genTableService.f(b3.toString());
				GenTable g = (GenTable) this.genTableMapper.get(a4);
				g.setOldName(a4.getName());
				this.genTableMapper.update(g);

				b3 = new StringBuffer();
				b3.append("create table " + a4.getName() + " (");
				b4 = "";
				for (GenTableColumn b2 : a3) {
					if ((!b2.getDelFlag().equals("1")) && (b2.getName() != null)) {
						if (b2.getIsPk().equals("1")) {
							if (a4.getGenIdType().equals("2")) {
								String c1 = b2.getJdbcType();
								if ((!c1.toLowerCase().contains("int")) && (!c1.toLowerCase().contains("integer"))) {
									c1 = "integer";
									b2.setJdbcType(c1);
								}
								b3.append("  " + b2.getName() + " " + c1 + " auto_increment  comment '" + b2
										.getComments() + "',");
							} else {
								String c2 = b2.getJdbcType();
								if (!c2.toLowerCase().contains("varchar")) {
									c2 = "varchar(64)";
									b2.setJdbcType(c2);
								}
								b3.append("  " + b2.getName() + " " + c2 + " comment '" + b2.getComments() + "',");
							}
							b4 = b4 + b2.getName() + ",";
						} else {
							b3.append("  " + b2.getName() + " " + b2.getJdbcType() + " comment '" + b2.getComments() + "',");
						}
					}
				}

				b3.append("primary key (" + b4.substring(0, b4.length() - 1) + ") ");

				b3.append(") comment '" + a4.getComments() + "' DEFAULT CHARSET=utf8");
				this.genTableService.f(b3.toString());
				j.setMsg("重新建表成功!");
			}

		} else if ("oracle".equals(a1)) {
			StringBuffer b1 = new StringBuffer();
			try {
				b1.append("DROP TABLE " + a4.getOldName());
				this.genTableService.f(b1.toString());
			} catch (Exception localException) {
			}
			b1 = new StringBuffer();
			b1.append("create table " + a4.getName() + " (");
			String pk = "";
			for (GenTableColumn b2 : a2) {
				String c1 = b2.getJdbcType();
				if (c1.equalsIgnoreCase("integer"))
					c1 = "number(10,0)";
				else if (c1.equalsIgnoreCase("datetime"))
					c1 = "date";
				else if (c1.contains("nvarchar("))
					c1 = c1.replace("nvarchar", "nvarchar2");
				else if (c1.contains("varchar("))
					c1 = c1.replace("varchar", "varchar2");
				else if (c1.equalsIgnoreCase("double"))
					c1 = "float(24)";
				else if (c1.equalsIgnoreCase("longblob"))
					c1 = "blob";
				else if (c1.equalsIgnoreCase("longtext")) {
					c1 = "clob";
				}
				if (b2.getIsPk().equals("1")) {
					b1.append("  " + b2.getName() + " " + c1 + ",");
					pk = pk + b2.getName();
				} else {
					b1.append("  " + b2.getName() + " " + c1 + ",");
				}
			}

			b1 = new StringBuffer(b1.substring(0, b1.length() - 1) + ")");
			this.genTableService.f(b1.toString());
			this.genTableService.f("comment on table " + a4.getName() + " is  '" + a4.getComments() + "'");
			for (GenTableColumn column : a2) {
				this.genTableService.f("comment on column " + a4.getName() + "." + column.getName() + " is  '" +
						column.getComments() + "'");
			}

			this.genTableService.f("alter table " + a4.getName() + " add constraint PK_" +
					a4.getName() + "_" + pk + " primary key (" + pk + ") ");
		} else if (("mssql".equals(a1)) || ("sqlserver".equals(a1))) {
			StringBuffer a11 = new StringBuffer();
			a11.append("if exists (select * from sysobjects where id = object_id(N'[" +
					a4.getName() + "]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)  drop table [" + a4.getName() + "]");
			this.genTableService.f(a11.toString());
			a11 = new StringBuffer();
			a11.append("create table " + a4.getName() + " (");
			String pk = "";
			for (GenTableColumn column : a2) {
				if (column.getIsPk().equals("1")) {
					a11.append("  " + column.getName() + " " + column.getJdbcType() + ",");
					pk = pk + column.getName() + ",";
				} else {
					a11.append("  " + column.getName() + " " + column.getJdbcType() + ",");
				}
			}
			a11.append("primary key (" + pk.substring(0, pk.length() - 1) + ") ");

			a11.append(")");
			this.genTableService.f(a11.toString());
		}

		this.genTableService.c(a4);
		j.setSuccess(true);

		return j;
	}

	private boolean a3(GenTable a1, String columnName) {
		List<GenTableColumn> a2 = this.genDataBaseDictMapper.findTableColumnList(a1);
		for (GenTableColumn a3 : a2) {
			if ((columnName != null) && (columnName.equals(a3.getName()))) {
				return true;
			}
		}

		return false;
	}

	@RequiresPermissions({"gen:genTable:genCode"})
	@RequestMapping({"genCodeForm"})
	public String a2(String tableType, GenScheme a1, Model model, RedirectAttributes redirectAttributes) {
		if (StringUtils.isBlank(a1.getPackageName())) {
			a1.setPackageName("com.jeeplus.modules");
		}

		if (StringUtils.isBlank(a1.getCategory())) {
			if ("1".equals(tableType))
				a1.setCategory("curd_many");
			else if ("3".equals(tableType))
				a1.setCategory("treeTable");
			else if ("4".equals(tableType))
				a1.setCategory("leftTreeRightTable");
			else {
				a1.setCategory("curd");
			}

		}

		GenScheme a3 = this.genSchemeService.b1("gen_table_id", a1.getGenTable().getId());
		if (a3 != null) {
			a1 = a3;
		}
		if (StringUtils.isBlank(a1.getProjectPath())) {
			a1.setProjectPath(Global.getProjectPath());
		}
		model.addAttribute("genScheme", a1);
		model.addAttribute("config", a.a7());
		model.addAttribute("tableList", this.genTableService.a3());
		return "modules/gen/genCodeForm";
	}

	@ResponseBody
	@RequestMapping({"genCode"})
	public AjaxJson a1(GenScheme a3, Model model, RedirectAttributes redirectAttributes) throws Exception {
		AjaxJson j = new AjaxJson();
		String a1 = "";
		a1 = this.genSchemeService.a2(a3);
		j.setSuccess(true);
		j.setMsg(a3.getGenTable().getName() + "代码生成成功<br/>" + a1);

		return j;
	}
}