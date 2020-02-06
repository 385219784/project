package com.jeeplus.modules.gen.entity;

import com.jeeplus.core.persistence.DataEntity;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;

import org.hibernate.validator.constraints.Length;

public class GenDict extends DataEntity<GenDict> {
	private static final long serialVersionUID = 1L;
	private String value;
	private String label;
	private String type;
	private String description;
	private Integer sort;

	public GenDict() {
	}

	public GenDict(String id) {
		super(id);
	}

	public GenDict(String value, String label) {
		this.value = value;
		this.label = label;
	}

	@XmlAttribute
	@Length(min = 1, max = 100)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@XmlAttribute
	@Length(min = 1, max = 100)
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Length(min = 1, max = 100)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlAttribute
	@Length(min = 0, max = 100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String toString() {
		return this.label;
	}
}