/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.attence.entity;

import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 考勤事项Entity
 * @author yt
 * @version 2018-10-24
 */
public class Item extends DataEntity<Item> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 事项名称
	private Integer sort;		// 排序
	private User user; //用户
	
	public Item() {
		super();
	}

	public Item(String id){
		super(id);
	}

	@ExcelField(title="事项名称", align=1, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="排序不能为空")
	@ExcelField(title="排序", align=1, sort=2)
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
	
}