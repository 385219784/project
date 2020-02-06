package com.jeeplus.common.utils.xls.usermodel;

public class RichTextModel {
	
	private int begin;
	
	private short font;
	
	private String moban;
	
	private String text;

	public RichTextModel(int begin, short font, String moban) {
		super();
		this.begin = begin;
		this.font = font;
		this.moban = moban;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}
	
	public short getFont() {
		return font;
	}

	public void setFont(short font) {
		this.font = font;
	}

	public String getMoban() {
		return moban;
	}

	public void setMoban(String moban) {
		this.moban = moban;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
