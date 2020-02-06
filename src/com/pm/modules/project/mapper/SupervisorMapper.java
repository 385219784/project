/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.project.mapper;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.pm.modules.project.entity.Principal;
import com.pm.modules.project.entity.Supervisor;

/**
 * 项目主管表MAPPER接口
 * @author yt
 * @version 2018-10-14
 */
@MyBatisMapper
public interface SupervisorMapper extends BaseMapper<Supervisor> {

	/**
	 * 查找项目主管
	 * @param id
	 * @return
	 */
	String getUsers(@Param("projectId")String projectId);

	/**
	 * 添加项目负责人
	 * @param user
	 */
	void insertPrincipal(Principal principal);

	/**
	 * 删除项目负责人
	 * @param id
	 */
	void deletePrincipal(@Param("proId")String proId);

	/**
	 * 查找项目负责人
	 * @param id
	 * @return
	 */
	String getPrincipal(@Param("proId")String proId);

	/**
	 * 删除项目负责人
	 * @param id
	 */
	void deletePrincipalUser(@Param("proId")String proId);
	
}