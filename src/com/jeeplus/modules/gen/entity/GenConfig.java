package com.jeeplus.modules.gen.entity;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "config")
public class GenConfig implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<GenCategory> categoryList;
	private List<GenDict> javaTypeList;
	private List<GenDict> queryTypeList;
	private List<GenDict> showTypeList;
	private List<GenDict> validateTypeList;
	private List<GenDict> OracleFieldType;
	private List<GenDict> MysqlFieldType;

	@XmlElementWrapper(name = "validateType")
	@XmlElement(name = "genDict")
	public List<GenDict> getValidateTypeList() {
		return this.validateTypeList;
	}

	public void setValidateTypeList(List<GenDict> validateTypeList) {
		this.validateTypeList = validateTypeList;
	}

	@XmlElementWrapper(name = "category")
	@XmlElement(name = "category")
	public List<GenCategory> getCategoryList() {
		return this.categoryList;
	}

	public void setCategoryList(List<GenCategory> categoryList) {
		this.categoryList = categoryList;
	}

	@XmlElementWrapper(name = "javaType")
	@XmlElement(name = "genDict")
	public List<GenDict> getJavaTypeList() {
		return this.javaTypeList;
	}

	public void setJavaTypeList(List<GenDict> javaTypeList) {
		this.javaTypeList = javaTypeList;
	}

	@XmlElementWrapper(name = "queryType")
	@XmlElement(name = "genDict")
	public List<GenDict> getQueryTypeList() {
		return this.queryTypeList;
	}

	public void setQueryTypeList(List<GenDict> queryTypeList) {
		this.queryTypeList = queryTypeList;
	}

	@XmlElementWrapper(name = "showType")
	@XmlElement(name = "genDict")
	public List<GenDict> getShowTypeList() {
		return this.showTypeList;
	}

	public void setShowTypeList(List<GenDict> showTypeList) {
		this.showTypeList = showTypeList;
	}

	@XmlElementWrapper(name = "MysqlFieldType")
	@XmlElement(name = "genDict")
	public List<GenDict> getMysqlFieldType() {
		return this.MysqlFieldType;
	}

	public void setMysqlFieldType(List<GenDict> mysqlFieldType) {
		this.MysqlFieldType = mysqlFieldType;
	}

	@XmlElementWrapper(name = "OracleFieldType")
	@XmlElement(name = "genDict")
	public List<GenDict> getOracleFieldType() {
		return this.OracleFieldType;
	}

	public void setOracleFieldType(List<GenDict> oracleFieldType) {
		this.OracleFieldType = oracleFieldType;
	}
}