package com.jeeplus.modules.sys.entity;

import com.jeeplus.core.persistence.DataEntity;

/**
 * 业务类别类
 * @author yt
 *
 */
public class UserYwType extends DataEntity<UserArea> {

	private static final long serialVersionUID = 1L;

	private String name; //业务类别名称
	private String userId;	// 用户id
	private String ywTypeId;	// 类别id
	
	
	public UserYwType() {
		super();
	}


	public UserYwType(String name, String userId, String ywTypeId) {
		super();
		this.name = name;
		this.userId = userId;
		this.ywTypeId = ywTypeId;
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


	public String getYwTypeId() {
		return ywTypeId;
	}


	public void setYwTypeId(String ywTypeId) {
		this.ywTypeId = ywTypeId;
	}
	
}
