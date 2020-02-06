package com.jeeplus.common.utils.xls.usermodel.converter;

import org.apache.poi.ss.usermodel.Cell;

import com.jeeplus.common.utils.xls.usermodel.FileldConverter;

public class SeqFileldConverter implements FileldConverter {
	
	//开始序号
	private int seq = 1;
	
	//重复编号次数
	private int repeat = 0;
	
	private int repeatC = 0;
		
	public SeqFileldConverter(int beginno, int repeat) {
		super();
		this.seq = beginno;
		this.repeat = repeat;
	}

	public SeqFileldConverter(int beginno) {
		
		this.seq = beginno;
	}

	public SeqFileldConverter() {
		super();
	}
	
	public String convert(Cell templateCell, Cell dataCell, String field,
			Object m) {
		
		if(repeat <= 0) {
			
			return String.valueOf(seq++);
		} else {
			
			if(repeatC>= repeat) {
				repeatC = 0;
				return String.valueOf(seq++);
			} else {
				
				repeatC++;
				return String.valueOf(seq);
			}
		}
	}

	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}
	
	public static void main(String[] args) {
		
		SeqFileldConverter s = new SeqFileldConverter(1,1);
		for(int i=0; i< 10; i++) {
			
			System.out.println(s.convert(null, null, null, null));
		}
	}
}
