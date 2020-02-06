/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.attence.mapper;

import java.util.Date;
import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.pm.modules.attence.entity.Item;
import com.pm.modules.attence.entity.WorkItem;

/**
 * 考勤事项MAPPER接口
 * @author yt
 * @version 2018-10-24
 */
@MyBatisMapper
public interface ItemMapper extends BaseMapper<Item> {

	/**
	 * 工日统计汇总列表数据
	 * @param workItem
	 * @return
	 */
	List<WorkItem> findWorkItem(WorkItem workItem);

	/**
	 * 得到相应考勤事项的天数
	 * @param workItemObj
	 * @return
	 */
	WorkItem getWorkItem(WorkItem workItem);

	/**
	 * 得到出差天数
	 * @param workItemObj
	 * @return
	 */
	List<Date> getEvectionNum(WorkItem workItem);
	
}