package com.jeeplus.common.utils.xls.usermodel.compare;

import java.util.Date;

import com.jeeplus.common.utils.xls.usermodel.DataMrginCompare;

public class DefaultDataMrginCompare implements DataMrginCompare {

	public boolean compare(Object obj1, Object obj12) {
		
		if(obj1==null || obj12 ==null) return false;
		
		if(obj1 instanceof Date) {
			
			Date d1 = (Date) obj1;
			Date d2 = (Date) obj1;
			
			return d1.getTime() == d2.getTime();
		}
		
		return obj1.equals(obj12);
	}

}
