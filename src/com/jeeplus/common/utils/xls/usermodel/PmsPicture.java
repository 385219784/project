package com.jeeplus.common.utils.xls.usermodel;

import java.io.Serializable;

import org.apache.poi.ss.usermodel.Workbook;

public class PmsPicture implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2084790421233287259L;

	private String imgPath;
	
	private int beginRow;
	
	private int beginCell;
	
	private int beginDx;
	
	private int beginDy;
	
	private int endRow;
	
	private int endCell;
	
	private int endDx;
	
	private int endDy;
	
	private int pictureType;
	
	//是否保持源图片尺寸
	private boolean isResize = false;
	
	public PmsPicture() {
		super();
	}

	public PmsPicture(String imgPath, int beginRow, int beginCell, int beginDx,
			int beginDy, int endRow, int endCell, int endDx, int endDy,
			int pictureType, boolean isResize) {
		super();
		this.imgPath = imgPath;
		this.beginRow = beginRow;
		this.beginCell = beginCell;
		this.beginDx = beginDx;
		this.beginDy = beginDy;
		this.endRow = endRow;
		this.endCell = endCell;
		this.endDx = endDx;
		this.endDy = endDy;
		this.pictureType = pictureType;
		this.isResize = isResize;
	}
	
	public PmsPicture(String imgPath, int beginRow, int beginCell, int endRow,
			int endCell, boolean isResize) {
		
		this(imgPath, beginRow, beginCell, 0, 0, endRow, endCell, 0, 0, 0, isResize);
		
		String imgSuffix = imgPath.substring(imgPath.lastIndexOf(".") + 1);
		if("png".equalsIgnoreCase(imgSuffix)) {
			
			setPictureType(Workbook.PICTURE_TYPE_PNG);
		} else if("jpeg".equalsIgnoreCase(imgSuffix)) {
			
			setPictureType(Workbook.PICTURE_TYPE_JPEG);
		}
	}
	
	public PmsPicture(String imgPath, int beginRow, int beginCell, int endRow,
			int endCell) {
		
		this(imgPath, beginRow, beginCell, endRow, endCell, false);		
	}
	
	public PmsPicture(String imgPath, int beginRow, int beginCell, int endRow,
			int endCell, int pictureType) {
		
		this(imgPath, beginRow, beginCell, 0, 0, endRow, endCell, 0, 0, pictureType, false);
	}
	
	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public int getBeginRow() {
		return beginRow;
	}

	public void setBeginRow(int beginRow) {
		this.beginRow = beginRow;
	}

	public int getBeginCell() {
		return beginCell;
	}

	public void setBeginCell(int beginCell) {
		this.beginCell = beginCell;
	}

	public int getBeginDx() {
		return beginDx;
	}

	public void setBeginDx(int beginDx) {
		this.beginDx = beginDx;
	}

	public int getBeginDy() {
		return beginDy;
	}

	public void setBeginDy(int beginDy) {
		this.beginDy = beginDy;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getEndCell() {
		return endCell;
	}

	public void setEndCell(int endCell) {
		this.endCell = endCell;
	}

	public int getEndDx() {
		return endDx;
	}

	public void setEndDx(int endDx) {
		this.endDx = endDx;
	}

	public int getEndDy() {
		return endDy;
	}

	public void setEndDy(int endDy) {
		this.endDy = endDy;
	}

	public int getPictureType() {
		return pictureType;
	}

	public void setPictureType(int pictureType) {
		this.pictureType = pictureType;
	}

	public boolean isResize() {
		return isResize;
	}

	public void setResize(boolean isResize) {
		this.isResize = isResize;
	}
}
