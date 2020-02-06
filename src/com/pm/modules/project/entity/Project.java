/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.project.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.User;

/**
 * 项目管理Entity
 * @author yt
 * @version 2018-10-15
 */
public class Project extends DataEntity<Project> {
	
	private static final long serialVersionUID = 1L;
	private String num;		// 项目编号
	private String name;		// 项目名称
	private String type;		// 项目类别
	private String designType;		// 设计类别
	private String principal;		// 项目负责人
	private String supervisor;		// 项目主管
	private String content;		// 上年度计奖比例
	private Date demand;		// 业主要求
	private String state;		// 状态
	private String planState; //进度
	private String question;		// 归档状态
	private String feature;		// 重点、难点、亮点、创新点、敏感点、关键技术问题
	private String proShortened; //项目简称
	private Integer serial; //序号
	private Date startDate;//项目开始时间
	private Date endDate;//项目终止时间
	private Date startDate1;//开始起始时间（搜索条件）
	private Date startDate2;//开始结束时间（搜索条件）
	private Date endDate1;//终止起始时间（搜索条件）
	private Date endDate2;//终止结束时间（搜索条件）
	private User user;//人员名称
	private String proType;    //复选框搜索条件
	private String dataRule; //时间搜索规则
	
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

	public Date getStartDate1() {
		return startDate1;
	}

	public void setStartDate1(Date startDate1) {
		this.startDate1 = startDate1;
	}

	public Date getStartDate2() {
		return startDate2;
	}

	public void setStartDate2(Date startDate2) {
		this.startDate2 = startDate2;
	}

	public Date getEndDate1() {
		return endDate1;
	}

	public void setEndDate1(Date endDate1) {
		this.endDate1 = endDate1;
	}

	public Date getEndDate2() {
		return endDate2;
	}

	public void setEndDate2(Date endDate2) {
		this.endDate2 = endDate2;
	}

	public String getDataRule() {
		return dataRule;
	}

	public void setDataRule(String dataRule) {
		this.dataRule = dataRule;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	//项目主管
	private List<Supervisor> supervisorList = Lists.newArrayList();
	
	//项目负责人
	private List<Principal> principalList = Lists.newArrayList();
	
	//专业人员
	private List<SpecialtyUser> specialtyUserList = Lists.newArrayList();
	
	public Project() {
		super();
	}

	public Project(String id){
		super(id);
	}

	@ExcelField(title="项目编号", align=1, sort=1)
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	@ExcelField(title="项目名称", align=1, sort=5)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="项目类别", dictType="pro_type", align=1, sort=10)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="设计类别", dictType="design_type", align=1, sort=15)
	public String getDesignType() {
		return designType;
	}

	public void setDesignType(String designType) {
		this.designType = designType;
	}
	
	@ExcelField(title="主管领导", align=1, sort=20)
	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	
	@ExcelField(title="项目负责人", align=1, sort=25)
	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="业主要求", align=1, sort=30)
	public Date getDemand() {
		return demand;
	}

	public void setDemand(Date demand) {
		this.demand = demand;
	}
	
	@ExcelField(title="状态", dictType="pro_state", align=1, sort=35)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@ExcelField(title="进度", dictType="plan_state", align=1, sort=32)
	public String getPlanState() {
		return planState;
	}

	public void setPlanState(String planState) {
		this.planState = planState;
	}

	@ExcelField(title="上年度计奖比例", align=1, sort=40)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="归档状态", align=1, sort=45)
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	@ExcelField(title="重点、难点、亮点、创新点、敏感点、关键技术问题", align=1, sort=50)
	public String getFeature() {
		return feature;
	}
	
	public void setFeature(String feature) {
		this.feature = feature;
	}

	public List<Supervisor> getSupervisorList() {
		return supervisorList;
	}

	public void setSupervisorList(List<Supervisor> supervisorList) {
		this.supervisorList = supervisorList;
	}
	
	@JsonIgnore
	public List<String> getSupervisorIdList() {
		List<String> supervisorIdList = Lists.newArrayList();
		for (Supervisor supervisor : supervisorList) {
			supervisorIdList.add(supervisor.getUserId());
		}
		return supervisorIdList;
	}

	public void setSupervisorIdList(List<Supervisor> supervisorIdList) {
		supervisorList = Lists.newArrayList();
		for (Supervisor supervisor : supervisorIdList) {
			User user = new User(supervisor.getId());
			supervisor.preInsert();
			supervisor.setId(IdGen.uuid());
			supervisor.setUserId(user.getId());
			supervisor.setDelFlag(DEL_FLAG_NORMAL);
			supervisorList.add(supervisor);
		}
	}

	public List<SpecialtyUser> getSpecialtyUserList() {
		return specialtyUserList;
	}

	public void setSpecialtyUserList(List<SpecialtyUser> specialtyUserList) {
		this.specialtyUserList = specialtyUserList;
	}

	@JsonIgnore
	public List<String> getSpecialtyUserIdList() {
		List<String> specialtyUserIdList = Lists.newArrayList();
		for (SpecialtyUser specialtyList : specialtyUserList) {
			specialtyUserIdList.add(specialtyList.getUserId());
		}
		return specialtyUserIdList;
	}

	public void setSpecialtyUserIdList(List<SpecialtyUser> specialtyUserIdList) {
		specialtyUserList = Lists.newArrayList();
		for (SpecialtyUser specialtyLists : specialtyUserIdList) {
			//用户id = 用户id + 专业id
			String userIdSpecId = specialtyLists.getId();
			String[] userIdSpecIds = userIdSpecId.split("#");
			specialtyLists.preInsert();
			specialtyLists.setId(IdGen.uuid());
			specialtyLists.setUserId(userIdSpecIds[0]);
			specialtyLists.setSpecId(userIdSpecIds[1]);
			specialtyLists.setDelFlag(DEL_FLAG_NORMAL);
			specialtyUserList.add(specialtyLists);
		}
	}

	public String getProShortened() {
		return proShortened;
	}

	public void setProShortened(String proShortened) {
		this.proShortened = proShortened;
	}

	public List<Principal> getPrincipalList() {
		return principalList;
	}

	public void setPrincipalList(List<Principal> principalList) {
		this.principalList = principalList;
	}
	
	@JsonIgnore
	public List<String> getPrincipalIdList() {
		List<String> principalIdList = Lists.newArrayList();
		for (Principal principal : principalList) {
			principalIdList.add(principal.getUserId());
		}
		return principalIdList;
	}

	public void setPrincipalIdList(List<Principal> principalIdList) {
		principalList = Lists.newArrayList();
		for (Principal principal : principalIdList) {
			String userId = principal.getId();
			principal.preInsert();
			principal.setId(IdGen.uuid());
			principal.setUserId(userId);
			principal.setDelFlag(DEL_FLAG_NORMAL);
			principalList.add(principal);
		}
	}
	
	private String spec1;
	private String spec2;
	private String spec3;
	private String spec4;
	private String spec5;
	private String spec6;
	private String spec7;
	private String spec8;
	private String spec9;
	private String spec10;
	private String spec11;
	private String spec12;
	private String spec13;
	private String spec14;
	private String spec15;
	private String spec16;
	private String spec17;
	private String spec18;
	private String spec19;
	private String spec20;
	private String spec21;
	private String spec22;
	private String spec23;
	private String spec24;
	private String spec25;
	private String spec26;
	private String spec27;
	private String spec28;
	private String spec29;
	private String spec30;

	public String getSpec1() {
		return spec1;
	}

	public void setSpec1(String spec1) {
		this.spec1 = spec1;
	}

	public String getSpec2() {
		return spec2;
	}

	public void setSpec2(String spec2) {
		this.spec2 = spec2;
	}

	public String getSpec3() {
		return spec3;
	}

	public void setSpec3(String spec3) {
		this.spec3 = spec3;
	}

	public String getSpec4() {
		return spec4;
	}

	public void setSpec4(String spec4) {
		this.spec4 = spec4;
	}

	public String getSpec5() {
		return spec5;
	}

	public void setSpec5(String spec5) {
		this.spec5 = spec5;
	}

	public String getSpec6() {
		return spec6;
	}

	public void setSpec6(String spec6) {
		this.spec6 = spec6;
	}

	public String getSpec7() {
		return spec7;
	}

	public void setSpec7(String spec7) {
		this.spec7 = spec7;
	}

	public String getSpec8() {
		return spec8;
	}

	public void setSpec8(String spec8) {
		this.spec8 = spec8;
	}

	public String getSpec9() {
		return spec9;
	}

	public void setSpec9(String spec9) {
		this.spec9 = spec9;
	}

	public String getSpec10() {
		return spec10;
	}

	public void setSpec10(String spec10) {
		this.spec10 = spec10;
	}

	public String getSpec11() {
		return spec11;
	}

	public void setSpec11(String spec11) {
		this.spec11 = spec11;
	}

	public String getSpec12() {
		return spec12;
	}

	public void setSpec12(String spec12) {
		this.spec12 = spec12;
	}

	public String getSpec13() {
		return spec13;
	}

	public void setSpec13(String spec13) {
		this.spec13 = spec13;
	}

	public String getSpec14() {
		return spec14;
	}

	public void setSpec14(String spec14) {
		this.spec14 = spec14;
	}

	public String getSpec15() {
		return spec15;
	}

	public void setSpec15(String spec15) {
		this.spec15 = spec15;
	}

	public String getSpec16() {
		return spec16;
	}

	public void setSpec16(String spec16) {
		this.spec16 = spec16;
	}

	public String getSpec17() {
		return spec17;
	}

	public void setSpec17(String spec17) {
		this.spec17 = spec17;
	}

	public String getSpec18() {
		return spec18;
	}

	public void setSpec18(String spec18) {
		this.spec18 = spec18;
	}

	public String getSpec19() {
		return spec19;
	}

	public void setSpec19(String spec19) {
		this.spec19 = spec19;
	}

	public String getSpec20() {
		return spec20;
	}

	public void setSpec20(String spec20) {
		this.spec20 = spec20;
	}

	public String getSpec21() {
		return spec21;
	}

	public void setSpec21(String spec21) {
		this.spec21 = spec21;
	}

	public String getSpec22() {
		return spec22;
	}

	public void setSpec22(String spec22) {
		this.spec22 = spec22;
	}

	public String getSpec23() {
		return spec23;
	}

	public void setSpec23(String spec23) {
		this.spec23 = spec23;
	}

	public String getSpec24() {
		return spec24;
	}

	public void setSpec24(String spec24) {
		this.spec24 = spec24;
	}

	public String getSpec25() {
		return spec25;
	}

	public void setSpec25(String spec25) {
		this.spec25 = spec25;
	}

	public String getSpec26() {
		return spec26;
	}

	public void setSpec26(String spec26) {
		this.spec26 = spec26;
	}

	public String getSpec27() {
		return spec27;
	}

	public void setSpec27(String spec27) {
		this.spec27 = spec27;
	}

	public String getSpec28() {
		return spec28;
	}

	public void setSpec28(String spec28) {
		this.spec28 = spec28;
	}

	public String getSpec29() {
		return spec29;
	}

	public void setSpec29(String spec29) {
		this.spec29 = spec29;
	}

	public String getSpec30() {
		return spec30;
	}

	public void setSpec30(String spec30) {
		this.spec30 = spec30;
	}
	
	
}