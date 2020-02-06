/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.project.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.pm.modules.project.entity.Specialty;
import com.pm.modules.project.entity.SpecialtyUser;

/**
 * 专业管理MAPPER接口
 * @author yt
 * @version 2018-10-15
 */
@MyBatisMapper
public interface SpecialtyMapper extends BaseMapper<Specialty> {
	//  获取所有的专业
	List<Map<String, Object>> getAllSpec();

	/**
	 * 判断是否重复的排序
	 * @param sort
	 * @return
	 */
	Integer findSort(@Param("sort")String sort);
	
	List<Integer> getAlreadyColNum();

	List<SpecialtyUser> getRunningSpecUser(@Param("today")Date today);

	List<Specialty> findPagedataList(Specialty specialty);

	List<String> getProUser(String proId);
	
}