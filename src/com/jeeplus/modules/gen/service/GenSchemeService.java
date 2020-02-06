package com.jeeplus.modules.gen.service;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.BaseService;
import com.jeeplus.modules.gen.entity.GenConfig;
import com.jeeplus.modules.gen.entity.GenScheme;
import com.jeeplus.modules.gen.entity.GenTable;
import com.jeeplus.modules.gen.entity.GenTableColumn;
import com.jeeplus.modules.gen.entity.GenTemplate;
import com.jeeplus.modules.gen.mapper.GenSchemeMapper;
import com.jeeplus.modules.gen.mapper.GenTableColumnMapper;
import com.jeeplus.modules.gen.mapper.GenTableMapper;
import com.jeeplus.modules.gen.util.a;
import com.jeeplus.modules.sys.entity.Menu;
import com.jeeplus.modules.sys.service.SystemService;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GenSchemeService extends BaseService {

	@Autowired
	private GenSchemeMapper genSchemeMapper;

	@Autowired
	private GenTableMapper genTableMapper;

	@Autowired
	private GenTableColumnMapper genTableColumnMapper;

	@Autowired
	private SystemService systemService;

	public GenScheme a(String id) {
		return (GenScheme) this.genSchemeMapper.get(id);
	}

	public Page<GenScheme> a1(Page<GenScheme> a2, GenScheme a3) {
		a.a5();
		a3.setPage(a2);
		a2.setList(this.genSchemeMapper.findList(a3));
		return a2;
	}

	@Transactional(readOnly = false)
	public String a2(GenScheme a3) throws Exception {
		String a4 = a3(a3);
		if (StringUtils.isBlank(a3.getId())) {
			a3.preInsert();
			this.genSchemeMapper.insert(a3);
		} else {
			a3.preUpdate();
			this.genSchemeMapper.update(a3);
		}
		return a4;
	}

	@Transactional(readOnly = false)
	public void a4(GenScheme a5, Menu a6) {
		String a7 = new StringBuilder().append(StringUtils.lowerCase(a5.getModuleName())).append(StringUtils.isNotBlank(a5.getSubModuleName()) ? new StringBuilder().append(":").append(StringUtils.lowerCase(a5.getSubModuleName())).toString() : "").append(":").append(StringUtils.uncapitalize(a5.getGenTable().getClassName())).toString();
		String a8 = new StringBuilder().append("/").append(StringUtils.lowerCase(a5.getModuleName())).append(StringUtils.isNotBlank(a5.getSubModuleName()) ? new StringBuilder().append("/").append(StringUtils.lowerCase(a5.getSubModuleName())).toString() : "").append("/").append(StringUtils.uncapitalize(a5.getGenTable().getClassName())).toString();

		a6.setHref(a8);
		a6.setIsShow("1");
		a6.setType("1");
		a6.setPermission(new StringBuilder().append(a7).append(":list").toString());
		this.systemService.saveMenu(a6);

		Menu a2 = new Menu();
		a2.setName("增加");
		a2.setIsShow("0");
		a6.setType("2");
		a2.setSort(Integer.valueOf(30));
		a2.setPermission(new StringBuilder().append(a7).append(":add").toString());
		a2.setParent(a6);
		this.systemService.saveMenu(a2);

		Menu a3 = new Menu();
		a3.setName("删除");
		a3.setIsShow("0");
		a6.setType("2");
		a3.setSort(Integer.valueOf(60));
		a3.setPermission(new StringBuilder().append(a7).append(":del").toString());
		a3.setParent(a6);
		this.systemService.saveMenu(a3);

		Menu a4 = new Menu();
		a4.setName("编辑");
		a4.setIsShow("0");
		a6.setType("2");
		a4.setSort(Integer.valueOf(90));
		a4.setPermission(new StringBuilder().append(a7).append(":edit").toString());
		a4.setParent(a6);
		this.systemService.saveMenu(a4);

		Menu a9 = new Menu();
		a9.setName("查看");
		a9.setIsShow("0");
		a6.setType("2");
		a9.setSort(Integer.valueOf(120));
		a9.setPermission(new StringBuilder().append(a7).append(":view").toString());
		a9.setParent(a6);
		this.systemService.saveMenu(a9);

		Menu b1 = new Menu();
		b1.setName("导入");
		b1.setIsShow("0");
		a6.setType("2");
		b1.setSort(Integer.valueOf(150));
		b1.setPermission(new StringBuilder().append(a7).append(":import").toString());
		b1.setParent(a6);
		this.systemService.saveMenu(b1);

		Menu b2 = new Menu();
		b2.setName("导出");
		b2.setIsShow("0");
		a6.setType("2");
		b2.setSort(Integer.valueOf(180));
		b2.setPermission(new StringBuilder().append(a7).append(":export").toString());
		b2.setParent(a6);
		this.systemService.saveMenu(b2);
	}

	@Transactional(readOnly = false)
	public void a5(GenScheme a1) {
		this.genSchemeMapper.delete(a1);
	}

	private String a3(GenScheme a1) throws Exception {
		StringBuilder a2 = new StringBuilder();

		GenTable a3 = (GenTable) this.genTableMapper.get(a1.getGenTable().getId());
		a3.setColumnList(this.genTableColumnMapper.findList(new GenTableColumn(new GenTable(a3.getId()))));

		GenConfig a4 = a.a7();

		List<GenTemplate> a5 = a.a8(a4, a1.getCategory(), false, a1.getFormStyle());
		List<GenTemplate> a6 = a.a8(a4, a1.getCategory(), true, a1.getFormStyle());

		if (a6.size() > 0) {
			GenTable a7 = new GenTable();
			a7.setParentTable(a3.getName());
			a3.setChildList(this.genTableMapper.findList(a7));
		}
		String a8 = "";
		GenTable a9;
		GenTableColumn c;
		Map<String, Object> a11;

		for (Iterator localIterator1 = a3.getChildList().iterator(); localIterator1.hasNext(); ) {
			a9 = (GenTable) localIterator1.next();
			a9.setParent(a3);

			a9.setColumnList(this.genTableColumnMapper.findList(new GenTableColumn(new GenTable(a9.getId()))));
			a1.setGenTable(a9);
			for (Iterator localIterator2 = a9.getColumnList().iterator(); localIterator2.hasNext(); ) {
				c = (GenTableColumn) localIterator2.next();
				if (c.getName().equals(a9.getParentTableFk())) {
					a8 = c.getSimpleJavaField();
				}
			}
			a11 = a.a9(a1);
			a11.put("childUrlPrefix", new StringBuilder().append(a11.get("moduleName"))
					.append(StringUtils.isNotBlank(a1
							.getSubModuleName()) ? new StringBuilder().append("/")
							.append(StringUtils.lowerCase(a1
									.getSubModuleName())).toString() : "").toString());

			a11.put("functionName", a9.getComments());
			a11.put("functionNameSimple", a9.getComments());
			for (GenTemplate tpl : a6)
				a2.append(a.a10(tpl, a11, true, a1.getProjectPath()));
		}
		a1.setGenTable(a3);
		Map<String, Object> a12 = a.a9(a1);
		a12.put("childSimpleJavaField", a8);
		for (GenTemplate tpl : a5) {
			a2.append(a.a10(tpl, a12, true, a1.getProjectPath()));
		}
		return a2.toString();
	}

	public GenScheme b1(String a1, String a2) {
		return (GenScheme) this.genSchemeMapper.findUniqueByProperty(a1, a2);
	}
}