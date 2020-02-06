package com.jeeplus.common.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

public class EgUtil {

	public static boolean equals(Object obj1, Object obj2) {

		if (obj1 == null || obj1 == null) {

			return false;
		}
		
		if(obj1 instanceof BigDecimal) {
			
			BigDecimal b1 = (BigDecimal)obj1;
			BigDecimal b2 = (BigDecimal)obj2;
			
			return b1.compareTo(b2)==0;
		}
		
		return obj1.equals(obj2);
	}

	public static boolean notEquals(Object obj1, Object obj2) {

		return !equals(obj1, obj2);
	}

	public static boolean isNotNullOrEmpty(Object obj) {

		return !isNullOrEmpty(obj);
	}

	public static boolean isNullOrEmpty(Object obj) {

		if (obj == null) {

			return true;
		}

		if (obj instanceof String) {

			String s = (String) obj;
			return s.length() == 0;
		} else if (obj instanceof Iterable) {

			Iterable<?> it = (Iterable<?>) obj;

			return !it.iterator().hasNext();
		} else if (obj instanceof Map) {

			Map<?, ?> map = (Map<?, ?>) obj;

			return map.isEmpty();
		}

		return false;
	}

	public static Integer getYear(Calendar calendar) {

		return calendar.get(Calendar.YEAR);
	}

	public static Integer getMonth(Calendar calendar) {

		return calendar.get(Calendar.MONTH) + 1;
	}

	public static Integer getCurrentYear() {

		return getYear(Calendar.getInstance());
	}

	public static Integer getCurrentMonth() {

		return getMonth(Calendar.getInstance());
	}
	
	public static Object[] merge(Object[] objs, List<Object> list) {

		List<Object> t = new ArrayList<Object>();

		if (objs != null && objs.length > 0) {

			for (int i = 0; i < objs.length; i++) {

				t.add(objs[i]);
			}
		}

		if (list != null && list.size() > 0) {

			for (Object o : list) {

				t.add(o);
			}
		}

		return t.toArray();
	}
	
	public static BigDecimal getBigDecimal(BigDecimal b) {
		
		if(b==null) {
			
			return BigDecimal.ZERO;
		}
		
		return b;
	}
	
	public static BigDecimal addBigDecimal(BigDecimal s, BigDecimal t) {
		
		if(s==null && t == null) {
			
			return null;
		}
		
		return getBigDecimal(s).add(getBigDecimal(t));
	}
	
	public static String getUUID() {
		
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static String replaceTemplate(String templateString, Object... params) {
		
		if(templateString==null || templateString.length()==0 || params==null || params.length==0) {
			
			return templateString;
		}
		
		for(int i=0; i< params.length; i++) {
			
			templateString = templateString.replaceAll("\\{"+ i +"\\}", params[i].toString());
		}
		
		return templateString;
	}
	
	public static String getBatchNo() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * 获取文件名称后缀，按照"."进行匹配
	 * @param fileName
	 * @return
	 */
	public static String getFileNameSuffix(String fileName) {
		
		if(isNullOrEmpty(fileName) || fileName.indexOf(".")<0) {
			
			return "";
		}
		
		return fileName.substring(fileName.lastIndexOf("."));
	}
	
	public static String getPy(String n) throws Exception {
		
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		String rs = "";

		char[] chars = n.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			
			String[] s = PinyinHelper.toHanyuPinyinStringArray(chars[i], format);
			
			if(s!=null && s.length>0 && s[0]!=null && s[0].length()>0) {
				
				rs += s[0].substring(0, 1);
			} else {
				
				rs+= chars[i];
			}
		}

		return rs.toUpperCase();
	}
	
	public static void main(String[] args) {
		
		System.out.println(replaceTemplate("{0}年{1}月的数据有问题{1}.", 2017, 10));
		
	}
}
