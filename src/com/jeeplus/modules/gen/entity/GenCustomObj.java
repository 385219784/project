package com.jeeplus.modules.gen.entity;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

public class GenCustomObj extends DataEntity<GenCustomObj> {
	private static final long serialVersionUID = 1L;
	private String label;
	private String value;
	private String dataUrl;
	private String tableName;
	private String description;

	public GenCustomObj() {
	}

	public GenCustomObj(String id) {
		super(id);
	}

	@ExcelField(title = "标签", align = 2, sort = 1)
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@ExcelField(title = "完整类名", align = 2, sort = 2)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@ExcelField(title = "数据接口", align = 2, sort = 3)
	public String getDataUrl() {
		return this.dataUrl;
	}

	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	@ExcelField(title = "说明", align = 2, sort = 8)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}