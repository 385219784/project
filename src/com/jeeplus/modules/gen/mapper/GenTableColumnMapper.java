package com.jeeplus.modules.gen.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.gen.entity.GenTable;
import com.jeeplus.modules.gen.entity.GenTableColumn;

import java.util.List;

@MyBatisMapper
public abstract interface GenTableColumnMapper extends BaseMapper<GenTableColumn> {
	public abstract void deleteByGenTable(GenTable paramGenTable);

	public abstract void deleteByGenTableByLogic(GenTable paramGenTable);

	public abstract List<GenTableColumn> findListAllStatus(GenTable paramGenTable);
}