package com.jeeplus.modules.sys.entity;

import com.jeeplus.core.persistence.DataEntity;

/**
 * 用户管辖区域类
 * @author yt
 * 
 */
public class UserArea extends DataEntity<UserArea>{

	private static final long serialVersionUID = 1L;

	private String name; //管辖区域名称
	private String userId;	// 用户id
	private String areaId;	// 地区id
	
	public UserArea() {
		super();
	}
	
	public UserArea(String name, String userId, String areaId) {
		super();
		this.name = name;
		this.userId = userId;
		this.areaId = areaId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	
}
