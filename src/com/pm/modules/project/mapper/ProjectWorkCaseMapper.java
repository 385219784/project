/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.project.mapper;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.pm.modules.project.entity.ProjectWorkCase;

/**
 * 个人工作情况统计MAPPER接口
 * @author yt
 * @version 2018-10-16
 */
@MyBatisMapper
public interface ProjectWorkCaseMapper extends BaseMapper<ProjectWorkCase> {

	ProjectWorkCase get(ProjectWorkCase projectWorkCase);

	/**
	 * 得到id
	 * @param proId
	 * @param specId
	 * @return
	 */
	String getId(ProjectWorkCase projectWorkCase);

	/**
	 * 根据项目id和专业id查找工作情况
	 * @param proId
	 * @param specId
	 * @return
	 */
	ProjectWorkCase getProjectWork(ProjectWorkCase projectWorkCase);

	/**
	 * 工作情况汇总列表数据
	 * @param projectWorkCase
	 * @return
	 */
	List<ProjectWorkCase> findLists(ProjectWorkCase projectWorkCase);
	
}