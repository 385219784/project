/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.TreeMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.Office;

/**
 * 机构MAPPER接口
 * @author jeeplus
 * @version 2017-05-16
 */
@MyBatisMapper
public interface OfficeMapper extends TreeMapper<Office> {
	
	public Office getByCode(String code);

	public void updateOfficeName(@Param(value="id")String id, @Param(value="branchName")String branchName);

	public String getChildList(@Param(value="id")String id);

	public List<Office> getChildrenList(@Param(value="id")String parentId);

	public List<Office> findOfficeByRemarks(@Param(value="remarks")String remarks);
}
