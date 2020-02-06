package com.jeeplus.common.utils.xls.usermodel;

import java.util.HashMap;
import java.util.Map;

/**
 * 循环处理区域
 * @author think
 *
 */
public class LoopArea {
	
	private int rowStart;
	
	private int rowEnd;
	
	private LoopDataModel loopDataModel;
	
	private Map<String, FileldConverter> fileldConverters = new HashMap<String, FileldConverter> ();;
	
	private DataMargin[] dataMargins;
	
	public int getRowStart() {
		return rowStart;
	}

	public void setRowStart(int rowStart) {
		this.rowStart = rowStart;
	}

	public int getRowEnd() {
		return rowEnd;
	}

	public void setRowEnd(int rowEnd) {
		this.rowEnd = rowEnd;
	}

	public LoopDataModel getLoopDataModel() {
		return loopDataModel;
	}

	public void setLoopDataModel(LoopDataModel loopDataModel) {
		this.loopDataModel = loopDataModel;
	}

	public Map<String, FileldConverter> getFileldConverters() {
		return fileldConverters;
	}

	public void setFileldConverters(Map<String, FileldConverter> fileldConverters) {
		this.fileldConverters = fileldConverters;
	}

	public DataMargin[] getDataMargins() {
		return dataMargins;
	}

	public void setDataMargins(DataMargin[] dataMargins) {
		this.dataMargins = dataMargins;
	}
}
