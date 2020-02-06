/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.project.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 专业管理Entity
 * @author yt
 * @version 2018-10-15
 */
public class Specialty extends DataEntity<Specialty> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 专业名称
	private Integer sort;		// 排序
	private Integer colNum;
	


	public Integer getColNum() {
		return colNum;
	}

	public void setColNum(Integer colNum) {
		this.colNum = colNum;
	}

	private User user; //用户

	private String userSpecialtyId; //用户专业id
	
	public Specialty() {
		super();
	}

	public Specialty(String id){
		super(id);
	}

	@ExcelField(title="专业名称", align=1, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="排序", align=1, sort=5)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserSpecialtyId() {
		return userSpecialtyId;
	}

	public void setUserSpecialtyId(String userSpecialtyId) {
		this.userSpecialtyId = userSpecialtyId;
	}
	
}