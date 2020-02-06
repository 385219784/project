package com.jeeplus.modules.gen.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.gen.entity.GenTable;
import com.jeeplus.modules.gen.entity.GenTableColumn;

import java.util.List;

@MyBatisMapper
public abstract interface GenDataBaseDictMapper extends BaseMapper<GenTableColumn> {
	public abstract List<GenTable> findTableList(GenTable paramGenTable);

	public abstract List<GenTable> findTableListByName(GenTable paramGenTable);

	public abstract List<GenTableColumn> findTableColumnList(GenTable paramGenTable);

	public abstract List<String> findTablePK(GenTable paramGenTable);
}