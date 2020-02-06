/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.project.entity;

import java.util.List;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 专业人员管理Entity
 * @author yt
 * @version 2018-10-15
 */
public class SpecialtyUser extends DataEntity<SpecialtyUser> {
	
	private static final long serialVersionUID = 1L;
	private String proId;		// 项目id
	private String specId;		// 专业id
	private String specName;    // 专业名称
	private List<Supervisor> supervisorList = Lists.newArrayList();		// 专业人员lists
	private String userId;
	private String userName;
	
	public SpecialtyUser() {
		super();
	}

	public SpecialtyUser(String id){
		super(id);
	}
	
	public SpecialtyUser(String id, String proId){
		super();
		this.proId = proId;
	}

	@ExcelField(title="项目id", align=2, sort=1)
	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}
	
	@ExcelField(title="专业id", align=2, sort=2)
	public String getSpecId() {
		return specId;
	}

	public void setSpecId(String specId) {
		this.specId = specId;
	}
	
	@ExcelField(title="专业名称", align=2, sort=3)
	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public List<Supervisor> getSupervisorList() {
		return supervisorList;
	}

	public void setSupervisorList(List<Supervisor> supervisorList) {
		this.supervisorList = supervisorList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}