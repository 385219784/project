/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.project.entity;

import com.jeeplus.core.persistence.DataEntity;

/**
 * 项目负责人Entity
 * @author yt
 * @version 2018-10-14
 */
public class Principal extends DataEntity<Principal> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 项目负责人id
	private String userName;		// 负责人名称
	private String proId;		// 项目id
	
	public Principal() {
		super();
	}

	public Principal(String id){
		super(id);
	}
	
	public Principal(String id,String proId){
		super();
		this.proId = proId;
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

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}
	
}