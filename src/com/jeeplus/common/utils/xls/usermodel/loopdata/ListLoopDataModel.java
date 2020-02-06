package com.jeeplus.common.utils.xls.usermodel.loopdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jeeplus.common.utils.xls.usermodel.LoopDataModel;

public class ListLoopDataModel implements LoopDataModel {
	
	private List<?> loopDatas = new ArrayList<Object>();
	
	private int f = 0;
	
	public ListLoopDataModel(List<?> loopDatas) {
		super();
		this.loopDatas = loopDatas;
	}
	
	public ListLoopDataModel(Object[] loopDatas) {
		super();
		this.loopDatas = Arrays.asList(loopDatas);
	}
	
	public Object next() {
		
		if(f<loopDatas.size()) {
			
			return loopDatas.get(f++);
		}
		
		return null;
	}

	public boolean hasNext() {
		
		return f<loopDatas.size();
	}

}
