package com.jeeplus.common.utils.xls.usermodel;

import org.apache.poi.ss.usermodel.PictureData;

public class ExcelPicture {
	
	private int sheetAt;
	
	private int rowAt;
	
	private int cellAt;
	
	private PictureData  picture;
	
	private byte[]  picData;

	private String mimeType;
	
	private String extension;
	
	
	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public ExcelPicture(int sheetAt, int rowAt, int cellAt, PictureData picture) {
		super();
		this.sheetAt = sheetAt;
		this.rowAt = rowAt;
		this.cellAt = cellAt;
		this.picture = picture;
	}
	
	public ExcelPicture(int sheetAt, int rowAt, int cellAt, byte[]  picData) {
		super();
		this.sheetAt = sheetAt;
		this.rowAt = rowAt;
		this.cellAt = cellAt;
		this.picData = picData;
	}

	
	
	public byte[] getPicData() {
		return picData;
	}

	public void setPicData(byte[] picData) {
		this.picData = picData;
	}

	public int getSheetAt() {
		return sheetAt;
	}

	public void setSheetAt(int sheetAt) {
		this.sheetAt = sheetAt;
	}

	public int getRowAt() {
		return rowAt;
	}

	public void setRowAt(int rowAt) {
		this.rowAt = rowAt;
	}

	public int getCellAt() {
		return cellAt;
	}

	public void setCellAt(int cellAt) {
		this.cellAt = cellAt;
	}

	public PictureData getPicture() {
		return picture;
	}

	public void setPicture(PictureData picture) {
		this.picture = picture;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		 if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ExcelPicture other = (ExcelPicture) obj;
        if (other.getSheetAt() == getSheetAt() && other.getRowAt() == getRowAt() && other.getCellAt() == getCellAt()) {
            
        	return true;
        } else
            return false;
	}
}
