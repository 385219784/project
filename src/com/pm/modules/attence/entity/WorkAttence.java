/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.attence.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.User;
import com.pm.modules.project.entity.Project;
import com.pm.modules.project.entity.Specialty;

/**
 * 工作日报Entity
 * @author scc
 * @version 2018-10-15
 */
public class WorkAttence extends DataEntity<WorkAttence> {
	
	private static final long serialVersionUID = 1L;
	private String proId;		// 项目ID
	private String specId;		// 专业ID
	private String proName;     // 项目名称
	private String proShortened; //项目简称
	private String specName;   //专业名称 
	private String  proNum;   // 项目编号
	private Double workTime;		// 工日(天)
	private Double overTime;		// 加班(时)
	private Date workDate;		// 工作时间
	private Date workDateStart;      // 工作开始时间
	private Date workDateEnd;      // 工作结束时间
	private User user; //专业人员
	private String remarks;
	private String principal;		// 项目负责人
	private String supervisor;		// 项目主管
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	private  Project project;
	private  Specialty   specialty;
	
	private String isTrip;
	private String itemType;
	private String itemName;
	
	public String getProShortened() {
		return proShortened;
	}

	public void setProShortened(String proShortened) {
		this.proShortened = proShortened;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}


	public String getIsTrip() {
		return isTrip;
	}

	public void setIsTrip(String isTrip) {
		this.isTrip = isTrip;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	private double sumWorkTime;
	private double sumOverTime;
	
	public WorkAttence() {
		super();
	}

	public WorkAttence(String id){
		super(id);
	}

	@ExcelField(title="专业人员", fieldType=User.class, value="user.name", align=1, sort=1)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ExcelField(title="项目编号", align=1, sort=5)
	public String getProNum() {
		return proNum;
	}

	public void setProNum(String proNum) {
		this.proNum = proNum;
	}
	

	@ExcelField(title="项目名称", align=1, sort=10)
	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	@ExcelField(title="工日(天)", align=3, sort=15)
	public Double getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Double workTime) {
		this.workTime = workTime;
	}
	
	@ExcelField(title="加班(时)", align=3, sort=20)
	public Double getOverTime() {
		return overTime;
	}

	public void setOverTime(Double overTime) {
		this.overTime = overTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getSpecId() {
		return specId;
	}

	public void setSpecId(String specId) {
		this.specId = specId;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public Date getWorkDateStart() {
		return workDateStart;
	}

	public void setWorkDateStart(Date workDateStart) {
		this.workDateStart = workDateStart;
	}

	public Date getWorkDateEnd() {
		return workDateEnd;
	}

	public void setWorkDateEnd(Date workDateEnd) {
		this.workDateEnd = workDateEnd;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public double getSumWorkTime() {
		return sumWorkTime;
	}

	public void setSumWorkTime(double sumWorkTime) {
		this.sumWorkTime = sumWorkTime;
	}

	public double getSumOverTime() {
		return sumOverTime;
	}

	public void setSumOverTime(double sumOverTime) {
		this.sumOverTime = sumOverTime;
	}
	
}