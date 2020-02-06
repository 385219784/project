package com.jeeplus.modules.gen.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.gen.entity.GenTable;
import org.apache.ibatis.annotations.Param;

@MyBatisMapper
public abstract interface GenTableMapper extends BaseMapper<GenTable> {
	public abstract int buildTable(@Param("sql") String paramString);
}