/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.TreeMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.entity.UserSupply;
import com.jeeplus.modules.sys.entity.UserYwType;

/**
 * 区域MAPPER接口
 * @author jeeplus
 * @version 2017-05-16
 */
@MyBatisMapper
public interface AreaMapper extends TreeMapper<Area> {
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public List<Area> findListTree(Area area);
	
	public List<Area> findListTreeSd(Area area);
	public List<Area> findListByParentId(String parentId);

	/**
	 * 查找所有区域
	 * @param area
	 * @return
	 */
	public List<Area> findLists(Area area);
	/**
	 * 获取4大区域
	 * @return
	 */
	public List<String> getAreaListByType();

	/**
	 * 查找所有业务类别
	 * @param userYwType
	 * @return
	 */
	public List<UserYwType> findAllUserYwType(UserYwType userYwType);

	/**
	 * 根据用户id查找业务类别
	 * @param userId
	 * @return
	 */
	public List<UserYwType> getUserYwTypeIds(@Param("userId")String userId);

	/**
	 * 查找所有配电所
	 * @param userSupply
	 * @return
	 */
	public List<UserSupply> findAllUserSupply(UserSupply userSupply);

	/**
	 * 根据用户id和区域id查找配电所
	 * @param userId
	 * @return
	 */
	public List<UserSupply> getSupplyIds(@Param("userId")String userId);

	public List<Map<String, String>> getWorkAreaMap();
}
