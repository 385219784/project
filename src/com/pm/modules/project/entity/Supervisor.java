/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.project.entity;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 项目主管表Entity
 * @author yt
 * @version 2018-10-14
 */
public class Supervisor extends DataEntity<Supervisor> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 主管id
	private String userName;		// 主管名称
	private String proId;		// 项目id
	
	public Supervisor() {
		super();
	}

	public Supervisor(String id){
		super(id);
	}
	
	public Supervisor(String id,String proId){
		super();
		this.proId = proId;
	}

	@ExcelField(title="主管id", value="userId", align=2, sort=1)
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

	@ExcelField(title="项目id", align=2, sort=2)
	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}
	
}