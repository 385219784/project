package com.jeeplus.common.utils.xls.usermodel;

import org.apache.poi.ss.usermodel.Cell;

/**
 * �ֶ�ת���������ڶ�������ֶ���Ҫ����ת��
 * @author think
 *
 */
public interface FileldConverter {
	
	/**
	 * ת������Objectֵ���ð汾ֱ��ʹ�÷���ֵ��toString����cellֵ
	 * @param templateCell
	 * @param dataCell
	 * @param field
	 * @param m
	 */
	public String convert(Cell templateCell, Cell dataCell, String field, Object m);
}
