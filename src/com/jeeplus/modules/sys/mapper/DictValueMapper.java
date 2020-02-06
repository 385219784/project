/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.DictValue;

/**
 * 数据字典MAPPER接口
 * @author lgf
 * @version 2017-01-16
 */
@MyBatisMapper
public interface DictValueMapper extends BaseMapper<DictValue> {

	List<DictValue> findList(DictValue dictValue);
	
	/**
	 * 根据父键值查找下一级键值
	 * @param parentId
	 * @return
	 */
	List<DictValue> getDictValues(@Param("parentId")String parentId);

	/**
	 * 根据value的id找到label
	 * @param id
	 * @return
	 */
	String getLabel(@Param("id")String id);

	/**
	 * 根据父value和type查找父id
	 * @param paramMap
	 * @return
	 */
	String getParentId(Map<String, Object> paramMap);

	/**
	 * 根据父id和type查找下级字典
	 * @param paramMap
	 * @return
	 */
	List<DictValue> getSonTypes(Map<String, Object> paramMap);
}