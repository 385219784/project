package com.jeeplus.common.utils.xls.usermodel;

import com.jeeplus.common.utils.xls.usermodel.compare.DefaultDataMrginCompare;

public class DataMargin {
	
	private int cellNo;
	
	private DataMargin[] childDataMargins;
	
	private DataMrginCompare dataMrginCompare = new DefaultDataMrginCompare();

	public DataMargin(int cellNo, DataMargin[] childDataMargins) {
		super();
		this.cellNo = cellNo;
		this.childDataMargins = childDataMargins;
	}

	public DataMargin(int cellNo) {
		super();
		this.cellNo = cellNo;
	}
	
	public DataMargin() {
		super();
	}

	public DataMargin(int cellNo, DataMargin[] childDataMargins,
			DataMrginCompare dataMrginCompare) {
		super();
		this.cellNo = cellNo;
		this.childDataMargins = childDataMargins;
		this.dataMrginCompare = dataMrginCompare;
	}

	public int getCellNo() {
		return cellNo;
	}

	public void setCellNo(int cellNo) {
		this.cellNo = cellNo;
	}

	public DataMargin[] getChildDataMargins() {
		return childDataMargins;
	}

	public void setChildDataMargins(DataMargin[] childDataMargins) {
		this.childDataMargins = childDataMargins;
	}

	public DataMrginCompare getDataMrginCompare() {
		return dataMrginCompare;
	}

	public void setDataMrginCompare(DataMrginCompare dataMrginCompare) {
		this.dataMrginCompare = dataMrginCompare;
	}
}
