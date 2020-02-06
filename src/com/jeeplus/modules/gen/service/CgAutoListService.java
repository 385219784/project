package com.jeeplus.modules.gen.service;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.BaseService;
import com.jeeplus.modules.gen.entity.GenConfig;
import com.jeeplus.modules.gen.entity.GenScheme;
import com.jeeplus.modules.gen.entity.GenTable;
import com.jeeplus.modules.gen.entity.GenTableColumn;
import com.jeeplus.modules.gen.mapper.GenDataBaseDictMapper;
import com.jeeplus.modules.gen.mapper.GenTableColumnMapper;
import com.jeeplus.modules.gen.mapper.GenTableMapper;
import com.jeeplus.modules.gen.template.FreemarkerHelper;
import com.jeeplus.modules.gen.util.a;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CgAutoListService extends BaseService {

	@Autowired
	private GenTableMapper genTableMapper;

	@Autowired
	private GenTableColumnMapper genTableColumnMapper;

	@Autowired
	private GenDataBaseDictMapper genDataBaseDictMapper;

	public GenTable get(String id) {
		GenTable genTable = (GenTable) this.genTableMapper.get(id);
		GenTableColumn genTableColumn = new GenTableColumn();
		genTableColumn.setGenTable(new GenTable(genTable.getId()));
		genTable.setColumnList(this.genTableColumnMapper.findList(genTableColumn));
		return genTable;
	}

	public Page<GenTable> find(Page<GenTable> page, GenTable genTable) {
		genTable.setPage(page);
		page.setList(this.genTableMapper.findList(genTable));
		return page;
	}

	public List<GenTable> findAll() {
		return this.genTableMapper.findAllList(new GenTable());
	}

	public List<GenTable> findTableListFormDb(GenTable genTable) {
		return this.genDataBaseDictMapper.findTableList(genTable);
	}

	public boolean checkTableName(String tableName) {
		if (StringUtils.isBlank(tableName)) {
			return true;
		}
		GenTable genTable = new GenTable();
		genTable.setName(tableName);
		List list = this.genTableMapper.findList(genTable);
		return list.size() == 0;
	}

	public boolean checkTableNameFromDB(String tableName) {
		if (StringUtils.isBlank(tableName)) {
			return true;
		}
		GenTable genTable = new GenTable();
		genTable.setName(tableName);
		List list = this.genDataBaseDictMapper.findTableListByName(genTable);
		return list.size() == 0;
	}

	public String generateCode(GenScheme genScheme) {
		StringBuilder result = new StringBuilder();

		GenTable genTable = (GenTable) this.genTableMapper.get(genScheme.getGenTable().getId());
		genTable.setColumnList(this.genTableColumnMapper.findList(new GenTableColumn(new GenTable(genTable.getId()))));

		GenConfig config = a.a7();

		genScheme.setGenTable(genTable);
		Map model = a.a9(genScheme);

		FreemarkerHelper viewEngine = new FreemarkerHelper();
		String html = viewEngine.parseTemplate("/com/jeeplus/modules/gen/template/viewList.ftl", model);
		return html;
	}

	public String generateListCode(GenScheme genScheme) {
		StringBuilder result = new StringBuilder();

		GenTable genTable = (GenTable) this.genTableMapper.get(genScheme.getGenTable().getId());
		genTable.setColumnList(this.genTableColumnMapper.findList(new GenTableColumn(new GenTable(genTable.getId()))));

		GenConfig config = a.a7();

		genScheme.setGenTable(genTable);
		Map model = a.a9(genScheme);

		FreemarkerHelper viewEngine = new FreemarkerHelper();
		String html = viewEngine.parseTemplate("/com/jeeplus/modules/gen/template/findList.ftl", model);
		return html;
	}
}