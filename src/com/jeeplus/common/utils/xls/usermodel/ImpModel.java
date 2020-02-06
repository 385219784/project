package com.jeeplus.common.utils.xls.usermodel;

import java.util.ArrayList;
import java.util.List;

public class ImpModel {
	
	private Integer sheetAt = 0;
	
	private Integer headAt = 0;
	
	private Integer fieldAt = 1;
	
	private List<FieldMapping> fms = new ArrayList<FieldMapping>();

	public ImpModel(List<FieldMapping> fms) {
		super();
		this.fms = fms;
	}

	public ImpModel(Integer sheetAt, Integer headAt, Integer fieldAt) {
		super();
		this.sheetAt = sheetAt;
		this.headAt = headAt;
		this.fieldAt = fieldAt;
	}

	public Integer getSheetAt() {
		return sheetAt;
	}

	public void setSheetAt(Integer sheetAt) {
		this.sheetAt = sheetAt;
	}

	public Integer getHeadAt() {
		return headAt;
	}

	public void setHeadAt(Integer headAt) {
		this.headAt = headAt;
	}

	public Integer getFieldAt() {
		return fieldAt;
	}

	public void setFieldAt(Integer fieldAt) {
		this.fieldAt = fieldAt;
	}

	public List<FieldMapping> getFms() {
		return fms;
	}

	public void setFms(List<FieldMapping> fms) {
		this.fms = fms;
	}
}
