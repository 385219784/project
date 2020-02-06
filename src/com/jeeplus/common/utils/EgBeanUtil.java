package com.jeeplus.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;

public class EgBeanUtil {
	
	/**
	 * 拷贝属性
	 * @param dest
	 * @param orig
	 * @param property
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public static void copyProperties(Object dest, Object orig, String... properties) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		if(dest==null || orig==null || properties==null || properties.length==0) {
			
			return;
		}
		
		for(String property: properties) {
			
			Object v = PropertyUtils.getProperty(orig, property);
			ConvertUtils.register(new org.apache.commons.beanutils.converters.BigDecimalConverter(null), BigDecimal.class);
			BeanUtils.setProperty(dest, property, v);
		}
	}
}
