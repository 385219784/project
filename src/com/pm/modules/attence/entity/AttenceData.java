package com.pm.modules.attence.entity;

import java.io.Serializable;
import java.util.Date;

public class AttenceData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String proId;
	private String specId;
	private double workTime;
	private double overTime;
	private String  dateStr;
	public AttenceData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public double getWorkTime() {
		return workTime;
	}
	public void setWorkTime(double workTime) {
		this.workTime = workTime;
	}
	public double getOverTime() {
		return overTime;
	}
	public void setOverTime(double overTime) {
		this.overTime = overTime;
	}


}
