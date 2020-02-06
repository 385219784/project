package com.pm.modules.attence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.User;

public class WorkItem  extends DataEntity<WorkItem> implements Serializable {
	/**
	 * 
	 */
	
	private String dateStr ; //日期
	private Date date ;
	private static final long serialVersionUID = 1L;
	private  WorkAttence          inspect;       //考察
	private  WorkAttence          train;         // 培训
	private  WorkAttence          yearLeave;     //年假
	private  WorkAttence          casualLeave;   //事假
	private  WorkAttence          sickLeave;     //病假
	private  WorkAttence          siteWork ;     // 零星事物
	private  WorkAttence    	  officePro;     //内业项目
	private  WorkAttence    	  outPro;        //外业项目
	private  WorkAttence    	  orgPro;        //党工团项目
	private  WorkAttence          cw;     //财务（更名为党支部）
	private  WorkAttence          sw ;     // 商务（更名为团支部）
	private  WorkAttence    	  jy;     //经营（更名为工会）
	private  WorkAttence    	  rcgl;        //日常管理（更名为办公室）
	private  WorkAttence     	  cc;        // 出差
	private  WorkAttence     	  xx;        // 学习
	private  List<WorkAttence>    officeProList;     //内业项目
	private  List<WorkAttence>    outProList;        //外业项目
	private  List<WorkAttence>    orgProList;        //党工团项目
	private  List<WorkAttence>     ccList;        // 出差





	public WorkAttence getXx() {
		return xx;
	}
	public void setXx(WorkAttence xx) {
		this.xx = xx;
	}
	public WorkAttence getCc() {
		return cc;
	}
	public void setCc(WorkAttence cc) {
		this.cc = cc;
	}
	public List<WorkAttence> getCcList() {
		return ccList;
	}
	public void setCcList(List<WorkAttence> ccList) {
		this.ccList = ccList;
	}
	private User user; //用户
	private Double evection; //出差天数
	private Double sumWorkTime; //总工日

	public Double getOverTimeDay() {
		return overTimeDay;
	}

	public void setOverTimeDay(Double overTimeDay) {
		this.overTimeDay = overTimeDay;
	}

	private Double sumOverTime; //总加班(小时)

	private Double overTimeDay;  // 加班折算成工日,7小时算一日

	private Date workDateStart;      // 工作开始时间
	private Date workDateEnd;      // 工作结束时间
	private String itemType; //考勤类别
	private String isTrip; //是否出差
	public WorkAttence getCw() {
		return cw;
	}
	public void setCw(WorkAttence cw) {
		this.cw = cw;
	}
	public WorkAttence getSw() {
		return sw;
	}
	public void setSw(WorkAttence sw) {
		this.sw = sw;
	}
	public WorkAttence getJy() {
		return jy;
	}
	public void setJy(WorkAttence jy) {
		this.jy = jy;
	}
	public WorkAttence getRcgl() {
		return rcgl;
	}
	public void setRcgl(WorkAttence rcgl) {
		this.rcgl = rcgl;
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
	public Double getSumWorkTime() {
		return sumWorkTime;
	}
	public void setSumWorkTime(Double sumWorkTime) {
		this.sumWorkTime = sumWorkTime;
	}
	public Double getSumOverTime() {
		return sumOverTime;
	}
	public void setSumOverTime(Double sumOverTime) {
		this.sumOverTime = sumOverTime;
	}
	public Double getEvection() {
		return evection;
	}
	public void setEvection(Double evection) {
		this.evection = evection;
	}
	public WorkAttence getOfficePro() {
		return officePro;
	}
	public void setOfficePro(WorkAttence officePro) {
		this.officePro = officePro;
	}
	public WorkAttence getOutPro() {
		return outPro;
	}
	public void setOutPro(WorkAttence outPro) {
		this.outPro = outPro;
	}
	public WorkAttence getOrgPro() {
		return orgPro;
	}
	public void setOrgPro(WorkAttence orgPro) {
		this.orgPro = orgPro;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public WorkItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WorkAttence getInspect() {
		return inspect;
	}
	public void setInspect(WorkAttence inspect) {
		this.inspect = inspect;
	}
	public WorkAttence getTrain() {
		return train;
	}
	public void setTrain(WorkAttence train) {
		this.train = train;
	}
	public WorkAttence getYearLeave() {
		return yearLeave;
	}
	public void setYearLeave(WorkAttence yearLeave) {
		this.yearLeave = yearLeave;
	}
	public WorkAttence getCasualLeave() {
		return casualLeave;
	}
	public void setCasualLeave(WorkAttence casualLeave) {
		this.casualLeave = casualLeave;
	}
	public WorkAttence getSickLeave() {
		return sickLeave;
	}
	public void setSickLeave(WorkAttence sickLeave) {
		this.sickLeave = sickLeave;
	}
	public WorkAttence getSiteWork() {
		return siteWork;
	}
	public void setSiteWork(WorkAttence siteWork) {
		this.siteWork = siteWork;
	}

	
	
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public List<WorkAttence> getOfficeProList() {
		return officeProList;
	}
	public void setOfficeProList(List<WorkAttence> officeProList) {
		this.officeProList = officeProList;
	}
	public List<WorkAttence> getOutProList() {
		return outProList;
	}
	public void setOutProList(List<WorkAttence> outProList) {
		this.outProList = outProList;
	}
	public List<WorkAttence> getOrgProList() {
		return orgProList;
	}
	public void setOrgProList(List<WorkAttence> orgProList) {
		this.orgProList = orgProList;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
