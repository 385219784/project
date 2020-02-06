/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.pm.modules.project.entity.Principal;
import com.pm.modules.project.entity.Project;
import com.pm.modules.project.entity.SpecialtyUser;
import com.pm.modules.project.entity.Supervisor;

/**
 * 项目管理MAPPER接口
 * @author yt
 * @version 2018-10-15
 */
@MyBatisMapper
public interface ProjectMapper extends BaseMapper<Project> {

	/**
	 * 查找所有的用户
	 * @return
	 */
	List<Supervisor> getAllUser(@Param("specId")String specId);

	/**
	 * 根据项目id查找项目主管
	 * @param proId
	 * @return
	 */
	List<Supervisor> getSupervisorListByProId(@Param("proId")String proId);

	/**
	 * 根据项目id查找专业人员
	 * @param proId
	 * @return
	 */
	List<SpecialtyUser> getSpecialtyUserListByProId(@Param("proId")String proId);

	/**
	 * 根据项目id和专业id查找专业人员
	 * @param proId
	 * @param specId
	 * @return
	 */
	String getSpecialtyUserListByProIdAndSpecId(@Param("proId")String proId, @Param("specId")String specId);

	/**
	 * 改变项目状态
	 * @param id
	 * @param state
	 */
	void updateState(Project project);

	/**
	 * 得到项目状态
	 * @param id
	 * @param state
	 * @return
	 */
	int getProState(@Param("id")String id);

	/**
	 * 查找所有项目主管
	 * @return
	 */
	List<Supervisor> getAllSuperUser();

	/**
	 * 查找所有项目负责人
	 * @return
	 */
	List<Principal> getAllPrincipalUser();

	List<String> getLeaderByproId(@Param("proId")String proId);

	List<String> getUserListByProId(@Param("proId")String proId);

	/**
	 * 根据项目id查找项目负责人
	 * @param proId
	 * @return
	 */
	List<Principal> getPrincipalListsByProId(@Param("proId")String proId);

	List<Project> findPagedataUser(Project project);

	List<Project> findPagedataUserDgt(Project project);

	/**
	 * 查找最大序号
	 * @return
	 */
	Integer getMaxSerial();

	/**
	 * 根据项目id修改所有专业状态和进度为已完成
	 * @param proId
	 */
	void updateSpecialtyState(@Param("proId")String proId);
	/**
	 * 得到需要提醒的项目
	 * @return
	 */
	List<String> getNotifyProject();

	List<String> getFzrList(String proId);

	List<String> getZgList(String proId);

	/**
	 * 查找序列数字
	 * @param string
	 * @return
	 */
	String getNum(@Param("str")String str);
	
}