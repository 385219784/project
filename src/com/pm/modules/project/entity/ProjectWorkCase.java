/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.project.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 个人项目Entity
 * @author yt
 * @version 2018-10-16
 */
public class ProjectWorkCase extends DataEntity<ProjectWorkCase> {
	
	private static final long serialVersionUID = 1L;
	private String proId;       // 项目id
	private String specId;      // 专业id
	private String userId;      // 用户id
	private String proNum;		// 项目编号
	private String proName;		// 项目名称
	private String proShortened;		// 项目简称
	private String proType;		// 项目类别
	private String designType;		// 设计阶段
	private String principal;		// 项目负责人
	private String supervisor;		// 主管领导
	private String specialty;		// 专业
	private String specialtyUser;   // 专业人员
	private String content;		// 设计内容
	private String plan;		// 进度
	private String state;		// 状态
	private Date finishDate;		// 计划完成时间
	private String difficulty;		// 制约因素
	private String specState; //专业状态
	private Integer serial; //序号
	private Date startDate;//起始时间
	private Date endDate;//终止时间

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	//@ExcelField(title="序号", align=1, sort=0)
	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}
	
	public ProjectWorkCase() {
		super();
	}

	public ProjectWorkCase(String id){
		super(id);
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@ExcelField(title="专业人员", align=1, sort=1)
	public String getSpecialtyUser() {
		return specialtyUser;
	}

	public void setSpecialtyUser(String specialtyUser) {
		this.specialtyUser = specialtyUser;
	}

	@ExcelField(title="项目编号", align=1, sort=5)
	public String getProNum() {
		return proNum;
	}

	public void setProNum(String proNum) {
		this.proNum = proNum;
	}
	
	public String getProName() {
		return proName;
	}

	@ExcelField(title="项目简称", align=1, sort=15)
	public String getProShortened() {
		return proShortened;
	}

	public void setProShortened(String proShortened) {
		this.proShortened = proShortened;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}
	
	@ExcelField(title="项目类别", dictType="pro_type", align=1, sort=20)
	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}
	
	//@ExcelField(title="设计阶段", dictType="design_type", align=1, sort=25)
	public String getDesignType() {
		return designType;
	}

	public void setDesignType(String designType) {
		this.designType = designType;
	}
	
	@ExcelField(title="主管领导", align=1, sort=30)
	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	
	@ExcelField(title="项目负责人", align=1, sort=35)
	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	
	@ExcelField(title="专业", align=1, sort=40)
	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	@ExcelField(title="项目状态", dictType="pro_state", align=1, sort=45)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@ExcelField(title="专业状态", dictType="pro_state", align=1, sort=48)
	public String getSpecState() {
		return specState;
	}

	public void setSpecState(String specState) {
		this.specState = specState;
	}
	
	@ExcelField(title="进度", dictType="plan_state", align=3, sort=50)
	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	@ExcelField(title="设计内容", align=1, sort=55)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="计划完成时间", align=1, sort=60)
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	
	@ExcelField(title="制约因素", align=1, sort=65)
	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

}