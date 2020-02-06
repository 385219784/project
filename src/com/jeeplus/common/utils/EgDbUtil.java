package com.jeeplus.common.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Query;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class EgDbUtil {
	
	public static String getAllLike(String condition) {
		
		return "%" + condition + "%";
	}
	
	public static Timestamp getDefaultTimestamp() {
		
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		calendar.set(Calendar.YEAR, 2017);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
				
		return new Timestamp(calendar.getTimeInMillis());
	}
	
	public static String encryption(String pwd, String salt) {
		
		String p = new SimpleHash(  
                "md5",
                pwd,
                ByteSource.Util.bytes(salt),
                2   //迭代次数  
                ).toHex(); 
		
		return p;
	}
	
	public static String getSqlIn(List<?> list) {
		
		String where = "";
		
		if(list!=null && !list.isEmpty()) {
			
			for(Object l: list) {
				
				if(l instanceof String) {
					
					where += "'" + l.toString() + "',";
				} else {
					
					where += l.toString() + ",";
				}
			}
			
			return where.substring(0, where.length()-1);
		}
		
		return where;
	}
	
	public static void setParams(Query typedQuery, Map<String, Object> params) {
		
		Iterator<String> it = params.keySet().iterator();
		
		while(it.hasNext()) {
			
			String key = it.next();
			typedQuery.setParameter(key, params.get(key));
		}
	}
		
	public static String createCountSql(String sql) {
		
		return "select count(*) from (" + sql + ") as counttable_";
	}
}
