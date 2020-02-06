package com.jeeplus.modules.sys.entity;

import com.jeeplus.core.persistence.DataEntity;

public class UserSupply extends DataEntity<UserArea>{

	private static final long serialVersionUID = 1L;
	
	//配电所名称
	private String supplyName;
	//用户id
	private String userId;
	//配电所id
	private String supplyId;
	//区域id
	private String areaId;
	//标志位,用来判断是否选中配电所
	private String flag;
	
	public UserSupply() {
		super();
	}
	
	public UserSupply(String supplyName, String userId, String supplyId, String areaId) {
		super();
		this.supplyName = supplyName;
		this.userId = userId;
		this.supplyId = supplyId;
		this.areaId = areaId;
	}
	
	public String getSupplyName() {
		return supplyName;
	}
	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSupplyId() {
		return supplyId;
	}
	public void setSupplyId(String supplyId) {
		this.supplyId = supplyId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((areaId == null) ? 0 : areaId.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((supplyId == null) ? 0 : supplyId.hashCode());
		result = prime * result + ((supplyName == null) ? 0 : supplyName.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserSupply other = (UserSupply) obj;
		if (areaId == null) {
			if (other.areaId != null)
				return false;
		} else if (!areaId.equals(other.areaId))
			return false;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
			return false;
		if (supplyId == null) {
			if (other.supplyId != null)
				return false;
		} else if (!supplyId.equals(other.supplyId))
			return false;
		if (supplyName == null) {
			if (other.supplyName != null)
				return false;
		} else if (!supplyName.equals(other.supplyName))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
	
}
