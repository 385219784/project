package com.pm.modules.attence.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.beetl.core.InferContext;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.monitor.entity.Task;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.service.OaNotifyService;
import com.jeeplus.modules.oa.web.OaNotifyController;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.pm.modules.attence.service.WorkAttenceService;
import com.pm.modules.project.entity.SpecialtyUser;
import com.pm.modules.project.service.ProjectService;
import com.pm.modules.project.service.SpecialtyService;

public class DoEveryDayNotify2  extends Task{
	 
	  private   static  SpecialtyService  specialtyService=  SpringContextHolder.getBean("specialtyService");
	  private   static  WorkAttenceService  workAttenceService=  SpringContextHolder.getBean("workAttenceService");
	  private   static  OaNotifyService   oaNotifyService=SpringContextHolder.getBean("oaNotifyService");
	  private   static  ProjectService projectService=SpringContextHolder.getBean("projectService");
	@Override
	public void run() {
		
		
		/**
		 * 1,找出尚未结束项目的人员专业表
		 * 
		 * 2,专业人员在3天内有填写记录,是否填写,需判断是否休息天
		 * 
		 * 3,没有填写的人员发送通知
		 * 
		 * 4,给项目负责人发送通知(当出现多人未发送时,只能发送一分给负责人)
		 */
		  Calendar now=Calendar.getInstance();
		  now.set(Calendar.HOUR_OF_DAY, 0);
		//设置当前时刻的分钟为0
		  now.set(Calendar.MINUTE, 0);
		//设置当前时刻的秒钟为0
		  now.set(Calendar.SECOND, 0);
		//设置当前的毫秒钟为0
		  now.set(Calendar.MILLISECOND, 0);
		  //昨天
		  Calendar yesterday1=Calendar.getInstance();
		  //前天
		  Calendar beforeYesterday=Calendar.getInstance();
		  
		  Calendar beforeYesterday9=Calendar.getInstance();
		  yesterday1.add(Calendar.DATE, -3);
		  yesterday1.set(Calendar.HOUR_OF_DAY, 0);
			//设置当前时刻的分钟为0
		  yesterday1.set(Calendar.MINUTE, 0);
			//设置当前时刻的秒钟为0
		  yesterday1.set(Calendar.SECOND, 0);
			//设置当前的毫秒钟为0
		  yesterday1.set(Calendar.MILLISECOND, 0);
		  
		  
		  beforeYesterday.add(Calendar.DATE, -6);
		  beforeYesterday.set(Calendar.HOUR_OF_DAY, 0);
			//设置当前时刻的分钟为0
		  beforeYesterday.set(Calendar.MINUTE, 0);
			//设置当前时刻的秒钟为0
		  beforeYesterday.set(Calendar.SECOND, 0);
			//设置当前的毫秒钟为0
		  beforeYesterday.set(Calendar.MILLISECOND, 0);
		  
		  
		  
		  beforeYesterday9.add(Calendar.DATE, -9);
		  beforeYesterday9.set(Calendar.HOUR_OF_DAY, 0);
			//设置当前时刻的分钟为0
		  beforeYesterday9.set(Calendar.MINUTE, 0);
			//设置当前时刻的秒钟为0
		  beforeYesterday9.set(Calendar.SECOND, 0);
			//设置当前的毫秒钟为0
		  beforeYesterday9.set(Calendar.MILLISECOND, 0);
		  
		  //今天
		  Date today=now.getTime();
		  // 3天
		  Date yesterday=yesterday1.getTime();
		  // 6天
		  Date beforDay=beforeYesterday.getTime();
		  //9天
		  Date beforDay9=beforeYesterday.getTime();
		  
		 
		  
		  
		  //非工作日
		  List<String> workDay = new ArrayList<>(Arrays.asList("6","7"));
		  //需要查询的日期
		  List<Date> searchDay=Lists.newArrayList();  
		  if(!workDay.contains(dayForWeek(beforDay))) {
			  searchDay.add(beforDay);
		  }
		  if(!workDay.contains(dayForWeek(yesterday))) {
			  searchDay.add(yesterday);
		  }
		  if(!workDay.contains(dayForWeek(beforDay9))) {
			  searchDay.add(today);
		  }

		  
		  SimpleDateFormat formatDay = new SimpleDateFormat("yyyyMMdd");
		  SimpleDateFormat formatMonth = new SimpleDateFormat("yyyyMM");
		  // 获取现有的专业人员记录
		  
		  int  searchDayNum=searchDay.size();
		  //开始
		  Date start=searchDay.get(0);
		  //结束
		  Date end=searchDay.get(searchDayNum-1);
		  List<SpecialtyUser>    specUserList= specialtyService.getRunningSpecUser(today);
		  //没有填写人员的名单
		  Set<String> userSet=new HashSet<String>();
		  //项目名单
		  List<String> proList=Lists.newArrayList();
		  int  count;
		  for(SpecialtyUser    specUser:specUserList) {
			  if (proList==null||proList.size()==0) {
				  proList.add(specUser.getProId());
			  }else {
				  
				  if (proList.contains(specUser.getProId())) {
					
				}else {
					 proList.contains(specUser.getProId());
				}
				 
			  }
			  
			  for(Date    date369:searchDay) {
				  count=workAttenceService.getRecord369(specUser.getUserId(),date369);
				  
				  if(count==0) {
					  userSet.add(specUser.getUserId()); 
				  }
			  }
			  
		/*	  
			  
			  List<Date> dates=workAttenceService.getAttenceRecent(specUser.getUserId(),start,end);
			  if(dates==null||dates.size()==0) {
				  userSet.add(specUser.getUserId());
			  }else if (dates.size()<searchDayNum) {
				  userSet.add(specUser.getUserId());
			  }else {
				
			}*/
	  
		  }
		  List<String > userlist = new ArrayList(userSet); 
		if (userlist==null||userlist.size()==0) {
			  return ;
		}else {
			for(String  str:userlist) {
				OaNotify  oa=new  OaNotify();
				oa.setContent("您近期内(3-9天前)有日程没有填写,即将超出填写日期,请及时填写,如已填写,请忽略!");
				oa.setType("4");
				oa.setTitle("日程提醒");
				oa.setStatus("1");
				oa.setCreateBy(new User("1"));
				oa.setUpdateBy(new User("1"));
				oa.setOaNotifyRecordIds(str);
				oaNotifyService.saveOa(oa);
			}
		}
		
		//给项目负责人发送推送
		if(proList==null||proList.size()==0) {
			
		}else {
			for(String  proId:proList) {
				//未填写人员名单
				StringBuilder  userName=new StringBuilder();
				// 项目负责人
				List<String>  leader =projectService.getLeaderByproId(proId);
				//项目的组员projectService
				List<String>  proUserList=projectService.getUserListByProId(proId);
				if(proUserList==null||proUserList.size()==0) {
					continue;
				}
				
				for(String  userId:userlist) {
					if (proUserList.contains(userId)) {
						userName.append(UserUtils.get(userId).getName()).append(",");
					}
				}
				
				if(leader!=null&&leader.size()>0) {
					for(String  str:leader) {
						if (StringUtils.isNotBlank(userName)) {
							OaNotify  oa=new  OaNotify();
							oa.setContent("以下人员在近期内(3-9天前)有日程没有填写:"+userName+"即将超出填写日期,如已填写,请忽略!");
							oa.setType("4");
							oa.setTitle("日程提醒");
							oa.setStatus("1");
							oa.setCreateBy(new User("1"));
							oa.setUpdateBy(new User("1"));
							oa.setOaNotifyRecordIds(str);
							oaNotifyService.saveOa(oa);
						}
					}
					
					
					
				}
				
				
				
			
			
			}
			
			
		}
		
		
		  
		  
		
	}
	
	
	
	
	
	
	
	
	
	
	// 判断是星期几
	 public static String dayForWeek(Date date)  {  
	        Calendar cal = Calendar.getInstance(); 
	        String[] weekDays = { "7", "1", "2", "3", "4", "5", "6" };
	        try {
	            cal.setTime(date);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
	        if (w < 0)
	            w = 0;
	        return weekDays[w];
	    }  

	 
	
	
	

}
