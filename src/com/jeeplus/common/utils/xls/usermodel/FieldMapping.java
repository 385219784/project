package com.jeeplus.common.utils.xls.usermodel;

public class FieldMapping {
	
	private Integer cellAt;
	
	private String head;
	
	private String property;
	
	private String type;
	
	private String formart;

	public FieldMapping(Integer cellAt, String property) {
		super();
		this.cellAt = cellAt;
		this.property = property;
		this.type = "string";
	}

	public FieldMapping(String head, String property) {
		super();
		this.head = head;
		this.property = property;
		this.type = "string";
	}

	public FieldMapping(Integer cellAt, String property, String type) {
		super();
		this.cellAt = cellAt;
		this.property = property;
		this.type = type;
	}

	public FieldMapping(Integer cellAt, String property, String type, String formart) {
		super();
		this.cellAt = cellAt;
		this.property = property;
		this.type = type;
		this.formart = formart;
	}

	public FieldMapping(String head, String property, String type, String formart) {
		super();
		this.head = head;
		this.property = property;
		this.type = type;
		this.formart = formart;
	}

	public Integer getCellAt() {
		return cellAt;
	}

	public void setCellAt(Integer cellAt) {
		this.cellAt = cellAt;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormart() {
		return formart;
	}

	public void setFormart(String formart) {
		this.formart = formart;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}
}
