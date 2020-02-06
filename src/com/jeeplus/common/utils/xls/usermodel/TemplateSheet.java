package com.jeeplus.common.utils.xls.usermodel;

import java.util.HashMap;
import java.util.Map;

public class TemplateSheet {
	
	public boolean isFirstSheet() {
		
		return sheetNo==1;
	}
	
	private String subjectFlag = "#";
	
	private String loopFlag = "$";
	
	private int sheetAt;
	
	private String sheetName;
	
	private int maxRow;
	
	private LoopArea[] loopAreas;
	
	private Object subject;
	
	private Map<String, FileldConverter> fileldConverters = new HashMap<String, FileldConverter> ();
	
	private int sheetNo = 1;
	
	private PmsPicture[] picture;
	
	public TemplateSheet() {
		super();
	}

	public TemplateSheet(int sheetAt, String sheetName, int maxRow) {
		super();
		this.sheetAt = sheetAt;
		this.sheetName = sheetName;
		this.maxRow = maxRow;
	}

	public TemplateSheet(int sheetAt, String sheetName, int maxRow,
			LoopArea[] loopAreas, Object subject) {
		super();
		this.sheetAt = sheetAt;
		this.sheetName = sheetName;
		this.maxRow = maxRow;
		this.loopAreas = loopAreas;
		this.subject = subject;
	}

	public TemplateSheet(String subjectFlag, String loopFlag, int sheetAt,
			String sheetName, int maxRow, LoopArea[] loopAreas, Object subject,
			Map<String, FileldConverter> fileldConverters) {
		super();
		this.subjectFlag = subjectFlag;
		this.loopFlag = loopFlag;
		this.sheetAt = sheetAt;
		this.sheetName = sheetName;
		this.maxRow = maxRow;
		this.loopAreas = loopAreas;
		this.subject = subject;
		this.fileldConverters = fileldConverters;
	}

	public int getSheetAt() {
		return sheetAt;
	}

	public void setSheetAt(int sheetAt) {
		this.sheetAt = sheetAt;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public int getMaxRow() {
		return maxRow;
	}

	public void setMaxRow(int maxRow) {
		this.maxRow = maxRow;
	}

	public LoopArea[] getLoopAreas() {
		return loopAreas;
	}

	public void setLoopAreas(LoopArea[] loopAreas) {
		this.loopAreas = loopAreas;
	}

	public Object getSubject() {
		return subject;
	}

	public void setSubject(Object subject) {
		this.subject = subject;
	}

	public String getSubjectFlag() {
		return subjectFlag;
	}

	public void setSubjectFlag(String subjectFlag) {
		this.subjectFlag = subjectFlag;
	}

	public String getLoopFlag() {
		return loopFlag;
	}

	public void setLoopFlag(String loopFlag) {
		this.loopFlag = loopFlag;
	}

	public Map<String, FileldConverter> getFileldConverters() {
		return fileldConverters;
	}

	public void setFileldConverters(Map<String, FileldConverter> fileldConverters) {
		this.fileldConverters = fileldConverters;
	}

	public int getSheetNo() {
		return sheetNo;
	}

	public void setSheetNo(int sheetNo) {
		this.sheetNo = sheetNo;
	}

	public PmsPicture[] getPicture() {
		return picture;
	}

	public void setPicture(PmsPicture[] picture) {
		this.picture = picture;
	}	
}
