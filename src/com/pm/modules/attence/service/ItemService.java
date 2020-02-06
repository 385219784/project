/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.attence.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.pm.modules.attence.entity.Item;
import com.pm.modules.attence.entity.WorkAttence;
import com.pm.modules.attence.entity.WorkItem;
import com.pm.modules.attence.mapper.ItemMapper;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

/**
 * 考勤事项Service
 * @author yt
 * @version 2018-10-24
 */
@Service
@Transactional(readOnly = true)
public class ItemService extends CrudService<ItemMapper, Item> {

	private static final String KC ="1";//考察id
	private static final String PX ="2";//培训id
	private static final String NY ="3";//内业id
	private static final String WY ="4";//外业id
	private static final String DGT ="5";//党工团id
	private static final String LXSW ="6";//零星事务id
	private static final String SJ ="7";//事假id
	private static final String NJ ="8";//年假id
	private static final String BJ ="9";//病假id
	private static final String CW ="10";//财务id
	private static final String SW ="11";//商务id
	private static final String JY ="12";//经营id
	private static final String RCGL ="13";//日常管理id
	private static final String CC ="14";//出差id
	private static final String XX ="15";//学习id
	
	public Item get(String id) {
		return super.get(id);
	}
	
	public List<Item> findList(Item item) {
		return super.findList(item);
	}
	
	public Page<Item> findPage(Page<Item> page, Item item) {
		return super.findPage(page, item);
	}
	/**
	 * 工日统计汇总列表数据
	 * @param page
	 * @param workItem
	 * @return
	 */
	public Page<WorkItem> findWorkItem(Page<WorkItem> page, WorkItem workItem) {
		dataRuleFilter(workItem);
		workItem.setPage(page);
		Page<WorkItem> workItemPage = page.setList(mapper.findWorkItem(workItem));
		List<WorkItem> workItemList = workItemPage.getList();
		for(WorkItem workItemObj : workItemList){

		 /*    Double   overTime=	workItemObj.getSumOverTime();


			BigDecimal   boverTime=new BigDecimal(overTime);

			Double  overTimeDay= (boverTime.divide(new BigDecimal("7"),2,ROUND_HALF_DOWN)).doubleValue();
			workItemObj.setOverTimeDay(overTimeDay);*/



			workItemObj.setWorkDateStart(workItem.getWorkDateStart());
			workItemObj.setWorkDateEnd(workItem.getWorkDateEnd());
			//内业
			workItemObj.setItemType(NY);
			workItemObj.setOfficePro(getWorkAttence(workItemObj));
			//外业
			workItemObj.setItemType(WY);
			workItemObj.setOutPro(getWorkAttence(workItemObj));
			//党工团
			workItemObj.setItemType(DGT);
			workItemObj.setOrgPro(getWorkAttence(workItemObj));
			//零星事务
			workItemObj.setItemType(LXSW);
			workItemObj.setSiteWork(getWorkAttence(workItemObj));
			//财务
			workItemObj.setItemType(CW);
			workItemObj.setCw(getWorkAttence(workItemObj));
			//商务
			workItemObj.setItemType(SW);
			workItemObj.setSw(getWorkAttence(workItemObj));
			//经营
			workItemObj.setItemType(JY);
			workItemObj.setJy(getWorkAttence(workItemObj));
			//日常管理
			workItemObj.setItemType(RCGL);
			workItemObj.setRcgl(getWorkAttence(workItemObj));
			//培训
			workItemObj.setItemType(PX);
			workItemObj.setTrain(getWorkAttence(workItemObj));
			//考察
			workItemObj.setItemType(KC);
			workItemObj.setInspect(getWorkAttence(workItemObj));
			//出差
			workItemObj.setItemType(CC);
			workItemObj.setCc(getWorkAttence(workItemObj));
			//学习
			workItemObj.setItemType(XX);
			workItemObj.setXx(getWorkAttence(workItemObj));
			//年假
			workItemObj.setItemType(NJ);
			workItemObj.setYearLeave(getWorkAttence(workItemObj));
			//事假
			workItemObj.setItemType(SJ);
			workItemObj.setCasualLeave(getWorkAttence(workItemObj));
			//病假
			workItemObj.setItemType(BJ);
			workItemObj.setSickLeave(getWorkAttence(workItemObj));
		}
		return workItemPage;
	}
	
	/**
	 * 得到相应考勤事项的天数
	 * @param workItemObj
	 * @return
	 */
	private WorkAttence getWorkAttence(WorkItem workItemObj) {
		WorkAttence workAttence = new WorkAttence();
		WorkItem workItem = mapper.getWorkItem(workItemObj);//得到相应考勤事项的天数
		if(workItem == null){
			workAttence.setWorkTime(0D);
			workAttence.setOverTime(0D);
		}else{
			workAttence.setWorkTime(workItem.getSumWorkTime());
			workAttence.setOverTime(workItem.getSumOverTime());
		}
		return workAttence;
		
	}

	/**
	 * 查找工日统计汇总数据
	 * @param workItem
	 * @return
	 */
	public List<WorkItem> findWorkItem(WorkItem workItem) {
		dataRuleFilter(workItem);
		return mapper.findWorkItem(workItem);
	}
	
	/**
	 * 得到相应考勤事项的天数
	 * @param workItemObj
	 * @return
	 */
	public WorkItem getWorkItem(WorkItem workItem) {
		return mapper.getWorkItem(workItem);
	}

	/**
	 * 得到出差天数
	 * @param workItemObj
	 * @return
	 */
	public List<Date> getEvectionNum(WorkItem workItem) {
		return mapper.getEvectionNum(workItem);
	}

	@Transactional(readOnly = false)
	public void save(Item item) {
		super.save(item);
	}
	
	@Transactional(readOnly = false)
	public void delete(Item item) {
		super.delete(item);
	}

}