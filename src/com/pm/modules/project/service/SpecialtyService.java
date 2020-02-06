/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.project.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.pm.modules.project.entity.Specialty;
import com.pm.modules.project.entity.SpecialtyUser;
import com.pm.modules.project.mapper.SpecialtyMapper;

/**
 * 专业管理Service
 * @author yt
 * @version 2018-10-15
 */
@Service
@Transactional(readOnly = true)
public class SpecialtyService extends CrudService<SpecialtyMapper, Specialty> {
	
	private static List<Integer> coList=new ArrayList<>();
	static {
		for(int i=1;i<30 ;i++) {
			coList.add(i);
		}
	}
	
	@Autowired
	private SpecialtyMapper specialtyMapper;
	
	public Specialty get(String id) {
		return super.get(id);
	}
	
	public List<Specialty> findList(Specialty specialty) {
		return super.findList(specialty);
	}
	
	public Page<Specialty> findPage(Page<Specialty> page, Specialty specialty) {
		return super.findPage(page, specialty);
	}
	
	@Transactional(readOnly = false)
	public void save(Specialty specialty) {
		//避免重复的colnum并且重复利用
		if (specialty.getIsNewRecord()){
		List<Integer>   alreadycol=	mapper.getAlreadyColNum();
		if(alreadycol==null||alreadycol.size()==0) {
			specialty.setColNum(coList.get(0));
		}else {
			coList.removeAll(alreadycol);
			specialty.setColNum(coList.get(0));
		}
			
		}
		super.save(specialty);
	}
	
	@Transactional(readOnly = false)
	public void delete(Specialty specialty) {
		super.delete(specialty);
	}

	/**
	 * 查找所有专业信息
	 * @return
	 */
	public List<Specialty> findAllSpecialtyList() {
		return specialtyMapper.findAllList(new Specialty());
	}


	public List<Map<String, Object>> getAllSpec() {
		// TODO Auto-generated method stub
		return specialtyMapper.getAllSpec();
	}

	/**
	 * 判断是否重复的排序
	 * @param sort
	 * @return
	 */
	public Integer findSort(String sort) {
		return specialtyMapper.findSort(sort);
	}

	public List<SpecialtyUser> getRunningSpecUser(Date today) {
		// TODO Auto-generated method stub
		return mapper.getRunningSpecUser(today);
	}

	public Page<Specialty> findPagedataList(Page<Specialty> page, Specialty specialty) {
		dataRuleFilter(specialty);
		specialty.setPage(page);
		page.setList(mapper.findPagedataList(specialty));
		return page;
	}

	public List<String> getProUser(String proId) {
		// TODO Auto-generated method stub
		return mapper.getProUser(proId);
	}
	
}