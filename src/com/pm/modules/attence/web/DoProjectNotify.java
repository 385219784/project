package com.pm.modules.attence.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import com.drew.lang.StringUtil;
import com.google.common.collect.Lists;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.monitor.entity.Task;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.service.OaNotifyService;
import com.jeeplus.modules.sys.entity.User;
import com.pm.modules.attence.service.WorkAttenceService;
import com.pm.modules.project.entity.Project;
import com.pm.modules.project.service.ProjectService;
import com.pm.modules.project.service.SpecialtyService;

public class DoProjectNotify   extends Task{

	  private   static  SpecialtyService  specialtyService=  SpringContextHolder.getBean("specialtyService");
	  private   static  WorkAttenceService  workAttenceService=  SpringContextHolder.getBean("workAttenceService");
	  private   static  OaNotifyService   oaNotifyService=SpringContextHolder.getBean("oaNotifyService");
	  private   static  ProjectService projectService=SpringContextHolder.getBean("projectService");
	@Override
	public void run() {
		List<String> proList = projectService.getNotifyProject();
		if (proList != null && proList.size() > 0) {
			for (String proId : proList) {
				List<String> resultList = Lists.newArrayList();
				// 查找出项目的专业人员
				List<String> proUserList = specialtyService.getProUser(proId);
				// 项目负责人
				List<String> fzrList = projectService.getFzrList(proId);
				// 项目主管
				List<String> zgList = projectService.getZgList(proId);
				if (proUserList != null && proUserList.size() > 0) {
					resultList.addAll(proUserList);
				}
				if (fzrList != null && fzrList.size() > 0) {
					resultList.addAll(fzrList);
				}
				if (zgList != null && zgList.size() > 0) {
					resultList.addAll(zgList);
				}
				if (resultList != null && resultList.size() > 0) {
					LinkedHashSet<String> set = new LinkedHashSet<String>(resultList.size());
					set.addAll(resultList);
					resultList.clear();
					resultList.addAll(set);
				}
				StringBuilder userIdList = new StringBuilder();
				for (String userId : resultList) {
					userIdList.append(userId).append(",");
				}
				if (StringUtils.isNoneBlank(userIdList)) {
					Project pro = projectService.get(proId);
					Date date = pro.getDemand();
					SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日");
					OaNotify oa = new OaNotify();
					oa.setContent("您的" + pro.getName() + "项目计划在" + f.format(date) + "完成,请尽快完成相关工作,如已完成,请忽略!");
					oa.setType("4");
					oa.setTitle("日程提醒");
					oa.setStatus("1");
					oa.setCreateBy(new User("1"));
					oa.setUpdateBy(new User("1"));
					oa.setOaNotifyRecordIds(userIdList.toString());
					oaNotifyService.saveOa(oa);
				}
			}
		} else {

		}
	}
}
