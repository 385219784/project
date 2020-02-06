/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.attence.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.pm.modules.attence.entity.ProWorkSum;
import com.pm.modules.attence.entity.WorkAttence;

/**
 * 工作日报MAPPER接口
 * @author scc
 * @version 2018-10-15
 */
@MyBatisMapper
public interface WorkAttenceMapper extends BaseMapper<WorkAttence> {

	List<WorkAttence> getUserRecordByDate(@Param("userId")String userId,@Param("date") Date date);

	List<WorkAttence> getUserSpec(@Param("userId")String userId,@Param("date") Date date);

	List<WorkAttence> getList(@Param("start")Date start,@Param("end")Date end, @Param("userId")String userId);

	List<WorkAttence> getUserMonthList(@Param("userId")String userId,@Param("date") Date date,@Param("workDateStart") Date workDateStart,@Param("workDateEnd") Date workDateEnd);

	/**
	 * 工日统计汇总
	 * @param workAttence
	 * @return
	 */
	List<WorkAttence> findLists(WorkAttence workAttence);

	List<WorkAttence> findAllProAttence();
	//项目汇总
	List<ProWorkSum> findProWorkNumList(ProWorkSum proWorkSum);
	
	/**
	 * 个人项目工日汇总
	 * @param proWorkSum
	 * @return
	 */
	List<ProWorkSum> findProPersonWorkNumList(ProWorkSum proWorkSum);

	Map<String, Double> getProSpecSum(@Param("start")Date start,@Param("end") Date end, @Param("proId")String proId,@Param("specId") String specId);
	
	Map<String, Double> getProPersonSpecSum(@Param("start")Date start,@Param("end") Date end, 
			@Param("proId")String proId,@Param("specId") String specId,@Param("specUserId") String specUserId);

	//查找项目的状态和修改时间
	Map<String, Object> getProStatusAndEndTime(@Param("proId")String proId);

	List<Date> getAttenceRecent(@Param("userId")String userId, @Param("start")Date start,@Param("end") Date end);

	List<WorkAttence> getUserRecordByDateNo(@Param("userId")String userId,@Param("dateStr") String dateStr);

	Date getLateDay(@Param("userId")String userId,@Param("date") Date date);

	/**
	 *  查看项目工日汇总详情
	 * @param proId
	 * @param endDate 
	 * @param startDate 
	 * @return
	 */
	List<WorkAttence> findWorkAttence(@Param("proId")String proId, @Param("start")String start, 
			@Param("end")String end);
	//查看369天是否有工日
	int getRecord369(@Param("userId")String userId, @Param("date369")Date date369);



	/**
	 * 查找总工日和总工时
	 * @param prosum
	 * @return
	 */
	Map<String, Double> getSumtime(ProWorkSum prosum);
	
}