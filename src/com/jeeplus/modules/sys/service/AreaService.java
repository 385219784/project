/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.entity.UserSupply;
import com.jeeplus.modules.sys.entity.UserYwType;
import com.jeeplus.modules.sys.mapper.AreaMapper;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 区域Service
 * @author jeeplus
 * @version 2017-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaMapper, Area> {
	@Autowired
	private AreaMapper areaMapper;
	
	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}

	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	public List<Area> findListTree(Area area){
		return areaMapper.findListTree(area);
	}
	
	public List<Area> findListTreeSd(Area area){
		return areaMapper.findListTreeSd(area);
	}
	/*
	 * 根据父ID查找子节点
	 */
	
	public List<Area> findListByParentId(String  parentId){
		return areaMapper.findListByParentId(parentId);
	}

	/**
	 * 查找所有区域
	 * @param area
	 * @return
	 */
	public List<Area> findLists(Area area) {
		return areaMapper.findLists(area);
	}

	/**
	 * 查找所有业务类别
	 * @param userYwType
	 * @return
	 */
	public List<UserYwType> findAllUserYwType(UserYwType userYwType) {
		return areaMapper.findAllUserYwType(userYwType);
	}
	
	/**
	 * 查找所有配电所
	 * @param userSupply
	 * @return
	 */
	public List<UserSupply> findAllUserSupply(UserSupply userSupply) {
		return areaMapper.findAllUserSupply(userSupply);
	}

	/**
	 * 根据用户id查找业务类别
	 * @param id
	 * @return
	 */
	public List<UserYwType> getUserYwTypeIds(String userId) {
		return areaMapper.getUserYwTypeIds(userId);
	}

	/**
	 * 根据用户id和区域id查找配电所
	 * @param id
	 * @return
	 */
	public List<UserSupply> getSupplyIds(String userId) {
		return areaMapper.getSupplyIds(userId);
	}

}


