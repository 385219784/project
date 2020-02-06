/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.attence.service;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.iim.entity.MyCalendar;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.pm.modules.attence.entity.AttenceData;
import com.pm.modules.attence.entity.ProWorkSum;
import com.pm.modules.attence.entity.WorkAttence;
import com.pm.modules.attence.entity.WorkItem;
import com.pm.modules.attence.mapper.WorkAttenceMapper;
import com.pm.modules.project.entity.ProjectWorkCase;
import com.pm.modules.project.entity.Specialty;
import com.pm.modules.project.mapper.SpecialtyMapper;
import com.pm.modules.project.mapper.SupervisorMapper;

import sun.security.krb5.internal.ccache.CCacheInputStream;

/**
 * 工作日报Service
 * @author scc
 * @version 2018-10-15
 */
@Service
@Transactional(readOnly = true)
public class WorkAttenceService extends CrudService<WorkAttenceMapper, WorkAttence> {
	
	private static String kc="1";
	private static String px ="2";
	private static String ny ="3";
	private static String wy ="4";
	private static String dgt ="5";
	private static String lxsw ="6";
	private static String sj ="7";
	private static String nj ="8";
	private static String bj ="9";
	private static String cw ="10";
	private static String sw ="11";
	private static String jy ="12";
	private static String rcgl ="13";
	private static String cc ="14";
	private static String xx ="15";
	
	//beijing   
	private static String bg="rgb(53,149,204)";
	@Autowired
	private   SupervisorMapper   supervisorMapper;
	@Autowired
	private SpecialtyMapper specialtyMapper;

	public WorkAttence get(String id) {
		return super.get(id);
	}
	
	public List<WorkAttence> findList(WorkAttence workAttence) {
		return super.findList(workAttence);
	}
	
	public Page<WorkAttence> findPage(Page<WorkAttence> page, WorkAttence workAttence) {
		return super.findPage(page, workAttence);
	}
	
	public Page<WorkAttence> findPages(Page<WorkAttence> page, WorkAttence workAttence) {
		dataRuleFilter(workAttence);
		workAttence.setPage(page);
		return page.setList(mapper.findLists(workAttence));
	}
	
/*	@Transactional(readOnly = false)
	public void save(List<AttenceData> attenceData ) throws ParseException {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (AttenceData atten:attenceData) {
			WorkAttence   workAttence=new  WorkAttence();
			if (StringUtils.isBlank(atten.getId())) {
				workAttence.setId("");
				workAttence.setProId(atten.getProId());
				workAttence.setSpecId(atten.getSpecId());
				workAttence.setWorkTime(atten.getWorkTime());
				workAttence.setOverTime(atten.getOverTime());
				workAttence.setWorkDate(sdf.parse(atten.getDateStr()));
			}else {
				workAttence.setId(atten.getId());
				workAttence.setProId(atten.getProId());
				workAttence.setSpecId(atten.getSpecId());
				workAttence.setWorkTime(atten.getWorkTime());
				workAttence.setOverTime(atten.getOverTime());
				workAttence.setWorkDate(sdf.parse(atten.getDateStr()));
				
			}
			super.save(workAttence);
			
		}
		
		
	}*/
	
	
	@Transactional(readOnly = false)
	public void saveItem(WorkItem item, String dateStr) throws ParseException {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 
		 Date  date=sdf.parse(dateStr);
		 List<WorkAttence>  list=Lists.newArrayList();
		 //考察
		 if (item.getInspect()!=null) {
			 item.getInspect().setWorkDate(date);
			 item.getInspect().setItemType(kc);
			 list.add(item.getInspect());
		}
		 //培训
		 if (item.getTrain()!=null) {
			 item.getTrain().setWorkDate(date);
			 item.getTrain().setItemType(px);
			 list.add(item.getTrain());
		}
		 
		 
		 //年假
		 if (item.getYearLeave()!=null) {
			 item.getYearLeave().setWorkDate(date);
			 item.getYearLeave().setItemType(nj);
			 list.add(item.getYearLeave());
		}
		 
		 
		 //事假
		 if (item.getCasualLeave()!=null) {
			 item.getCasualLeave().setWorkDate(date);
			 item.getCasualLeave().setItemType(sj);
			 list.add(item.getCasualLeave());
		}
		 
		 //病假
		 if (item.getSickLeave()!=null) {
			 item.getSickLeave().setWorkDate(date);
			 item.getSickLeave().setItemType(bj);
			 list.add(item.getSickLeave());
		}
		 
		 
		 //零星事物
		 if (item.getSiteWork()!=null) {
			 item.getSiteWork().setWorkDate(date);
			 item.getSiteWork().setItemType(lxsw);
			 list.add(item.getSiteWork());
		}
		 
		 
		 
		 
		 
		 //财务
		 if (item.getCw()!=null) {
			 item.getCw().setWorkDate(date);
			 item.getCw().setItemType(cw);
			 list.add(item.getCw());
		}
		 //商务
		 if (item.getSw()!=null) {
			 item.getSw().setWorkDate(date);
			 item.getSw().setItemType(sw);
			 list.add(item.getSw());
		}
		 //经营
		 if (item.getJy()!=null) {
			 item.getJy().setWorkDate(date);
			 item.getJy().setItemType(jy);
			 list.add(item.getJy());
		}
		 //日常管理
		 if (item.getRcgl()!=null) {
			 item.getRcgl().setWorkDate(date);
			 item.getRcgl().setItemType(rcgl);
			 list.add(item.getRcgl());
		}
		 
		 //学习
		 if (item.getXx()!=null) {
			 item.getXx().setWorkDate(date);
			 item.getXx().setItemType(xx);
			 list.add(item.getXx());
		}
		 
		 //内业
		 if (item.getOfficeProList()!=null&&item.getOfficeProList().size()>0) {
			 for(WorkAttence  attence:item.getOfficeProList()) {
				 
				 if(attence.getProject()==null) {
					 continue;
				 }
				 attence.setWorkDate(date);
				 attence.setItemType(ny);
				 list.add(attence);
			 }
		}
		 
		 //外业
		 if (item.getOutProList()!=null&&item.getOutProList().size()>0) {
			 for(WorkAttence  attence:item.getOutProList()) {
				 if(attence.getProject()==null) {
					 continue;
				 }
				 attence.setWorkDate(date);
				 attence.setItemType(wy);
				 list.add(attence);
			 }
		}
		 
		//dgt
		 if (item.getOrgProList()!=null&&item.getOrgProList().size()>0) {
			 for(WorkAttence  attence:item.getOrgProList()) {
				 if(attence.getProject()==null) {
					 continue;
				 }
				 attence.setWorkDate(date);
				 attence.setItemType(dgt);
				 list.add(attence);
			 }
		}
		 
		//cc
		 if (item.getCcList()!=null&&item.getCcList().size()>0) {
			 for(WorkAttence  attence:item.getCcList()) {
				 if(attence.getProject()==null) {
					 continue;
				 }
				 attence.setWorkDate(date);
				 attence.setItemType(cc);
				 list.add(attence);
			 }
		} 
		 
		 
		 if (list!=null&&list.size()>0) {
			 for(WorkAttence  work:list){
				 
				 if (work.getOverTime()==null) {
					 work.setOverTime(new Double(0));
				}
				 if (!"1".equals(work.getIsTrip())) {
					work.setIsTrip("0");
				}
				 if("1".equals(work.getDelFlag())) {
					 //id为空切删除标记为1
					 if (StringUtils.isBlank(work.getId())) {
						
					}else {
						mapper.delete(work.getId());
					}
				 }else {
					 if(work.getWorkTime()==0&&work.getOverTime()==0) {
						 
					 }else {
						 super.save(work);   
					 }
					 
					
				 }
			 };
			
		}
	/*	for (AttenceData atten:attenceData) {
			WorkAttence   workAttence=new  WorkAttence();
			if (StringUtils.isBlank(atten.getId())) {
				workAttence.setId("");
				workAttence.setProId(atten.getProId());
				workAttence.setSpecId(atten.getSpecId());
				workAttence.setWorkTime(atten.getWorkTime());
				workAttence.setOverTime(atten.getOverTime());
				workAttence.setWorkDate(sdf.parse(atten.getDateStr()));
			}else {
				workAttence.setId(atten.getId());
				workAttence.setProId(atten.getProId());
				workAttence.setSpecId(atten.getSpecId());
				workAttence.setWorkTime(atten.getWorkTime());
				workAttence.setOverTime(atten.getOverTime());
				workAttence.setWorkDate(sdf.parse(atten.getDateStr()));
				
			}
			super.save(workAttence);
			
		}*/
		
		
	}
	
	
	@Transactional(readOnly = false)
	public void delete(WorkAttence workAttence) {
		super.delete(workAttence);
	}

	//根据用户查找日程记录
	public List<WorkAttence> getUserRecordByDate(String userId, Date date) {
		return mapper.getUserRecordByDate(userId,date);
	}
	//根据用户查找专业记录
	public List<WorkAttence> getUserSpec(String userId, Date date) {
		SimpleDateFormat formatStr=new SimpleDateFormat("yyyyMMdd");
		//填写日期
		int  dateInt=Integer.valueOf(formatStr.format(date));
		
		List<WorkAttence> resutl=mapper.getUserSpec(userId,date);
		//填报的时间需小于项目结束的时间
		for(WorkAttence   attence:resutl) {
			Map<String, Object> map=mapper.getProStatusAndEndTime(attence.getProId());
			if (map!=null) {
				String state=(String)map.get("state");
				String end =(String)map.get("end");
				//如果项目已经结束
				if ("5".equals(state)&&dateInt>Integer.valueOf(end)) {
					resutl.remove(attence);
				}
			}
		}
		return resutl;
	}

	public List<MyCalendar> getList(Date start, Date end, String userId) {
		
		User user=UserUtils.getUser();
		List<MyCalendar>   calList=Lists.newArrayList();
		List<WorkAttence>  list=mapper.getList(start,end,userId);
		if (list==null||list.size()==0) {
			return null;
		}else {
			for(WorkAttence work:list) {
				MyCalendar  callen=new MyCalendar();
				callen.setId(work.getId());
				
				if (StringUtils.isBlank(work.getProId())) {
					
					if (work.getOverTime()==null) {
						callen.setTitle(work.getItemName()+"-"+work.getWorkTime().toString()+"-"+"0");	
					}else {
						callen.setTitle(work.getItemName()+"-"+work.getWorkTime().toString()+"-"+work.getOverTime().toString());
						
					}
				}else {
					if (work.getOverTime()==null) {
						callen.setTitle(work.getProject().getProShortened()+"-"+work.getWorkTime().toString()+"-"+"0");	
					}else {
						callen.setTitle(work.getProject().getProShortened()+"-"+work.getWorkTime().toString()+"-"+work.getOverTime().toString());	
					}
				}
				callen.setStart(work.getWorkDate());
				//callen.setEnd(work.getWorkDate());
				callen.setAdllDay("1");
				callen.setColor(bg);
				callen.setUser(user);
				calList.add(callen);
			}
			return 	calList;
			
		}
		
	}

	public List<WorkAttence> getUserMonthList(String userId, Date date, Date workDateStart, Date workDateEnd) {
		// TODO Auto-generated method stub
		  return  mapper.getUserMonthList(userId,date,workDateStart,workDateEnd);
	}

	
	// ruhe 
	public List<WorkAttence> findAllProAttence() {
		List<WorkAttence>  proSumList=      mapper.findAllProAttence();
		if (proSumList==null||proSumList.size()==0) {
			return null;
		} else {
			for(WorkAttence prosum:proSumList) {
				prosum.setSupervisor(supervisorMapper.getUsers(prosum.getId()));
			}
		}
		
		
		// TODO Auto-generated method stub
		return mapper.findAllProAttence();
	}

	public Page<ProWorkSum> findProWorkNum(Page<ProWorkSum> page, ProWorkSum proWorkSum) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Date  start=proWorkSum.getStart();
		Date   end =proWorkSum.getEnd();
		
		proWorkSum.setPage(page);
		dataRuleFilter(proWorkSum);
		List<Specialty>   specList=specialtyMapper.findAllList(new Specialty());
		Page<ProWorkSum> proWorkSumPage = page.setList(mapper.findProWorkNumList(proWorkSum));
		List<ProWorkSum>   resultLit= proWorkSumPage.getList();
		if(resultLit==null||resultLit.size()==0) {
		}else {
			for(ProWorkSum   prosum:resultLit) {  
				prosum.setStart(start);
				prosum.setEnd(end);
				//查找总工日和总工时
				Map<String, Double> sumTime = mapper.getSumtime(prosum);
				prosum.setSumOverTime(sumTime.get("sumOverTime"));
				prosum.setSumWorkTime(sumTime.get("sumWorkTime"));
				prosum.setOverTimeDay(sumTime.get("overTimeDay"));
				/*// 项目主管
				prosum.getPro().setSupervisor(supervisorMapper.getUsers(prosum.getPro().getId()));
				// 项目负责人
				prosum.getPro().setPrincipal(supervisorMapper.getPrincipal(prosum.getPro().getId()));*/
				for(Specialty spec:specList) {
				Map<String, Double>  workTime=	mapper.getProSpecSum(start,end,prosum.getPro().getId(),spec.getId());
						//使用反射赋值
					   Field wField = prosum.getClass().getDeclaredField("work" +spec.getColNum());
					   wField.setAccessible(true);
					   wField.set(prosum, workTime.get("work"));
				       
				       Field oField = prosum.getClass().getDeclaredField("over" +spec.getColNum());
				       oField.setAccessible(true);
				       oField.set(prosum, workTime.get("over"));

					Field dField = prosum.getClass().getDeclaredField("ovday" +spec.getColNum());
					dField.setAccessible(true);
					dField.set(prosum, workTime.get("ovday"));
				}
			}
		}
		return proWorkSumPage;
	}
	
	/**
	 * 个人工日统计汇总
	 * @param page
	 * @param proWorkSum
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Page<ProWorkSum> findProPerSonWorkNum(Page<ProWorkSum> page, ProWorkSum proWorkSum) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Date  start=proWorkSum.getStart();
		Date   end =proWorkSum.getEnd();

		dataRuleFilter(proWorkSum);
		proWorkSum.setPage(page);
		
		List<Specialty>   specList=specialtyMapper.findAllList(new Specialty());
		List<ProWorkSum>   resultLit=mapper.findProPersonWorkNumList(proWorkSum);
		if(resultLit==null||resultLit.size()==0) {
		}else {
			for(ProWorkSum   prosum:resultLit) {  
				// 项目主管
				prosum.getPro().setSupervisor(supervisorMapper.getUsers(prosum.getPro().getId()));
				// 项目负责人
				prosum.getPro().setPrincipal(supervisorMapper.getPrincipal(prosum.getPro().getId()));
				for(Specialty spec:specList) {
				Map<String, Double>  workTime=	mapper.getProPersonSpecSum(start,end,prosum.getPro().getId(),spec.getId(),prosum.getUser().getId());
						//使用反射赋值
					   Field wField = prosum.getClass().getDeclaredField("work" +spec.getColNum());
					   wField.setAccessible(true);
					   wField.set(prosum, workTime.get("work"));
				       
				       Field oField = prosum.getClass().getDeclaredField("over" +spec.getColNum());
				       oField.setAccessible(true);
				       oField.set(prosum, workTime.get("over"));
				}
		}
	}
		page.setCount(resultLit.size());
		page.setList(resultLit);
		return page;
	}

	

	public List<Date> getAttenceRecent(String userId, Date start, Date end) {
		// TODO Auto-generated method stub
		return mapper.getAttenceRecent(userId,start,end);
	}
	
	
	public List<ProWorkSum> findProWorkNumList(ProWorkSum proWorkSum) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Date  start=proWorkSum.getStart();
		Date   end =proWorkSum.getEnd();

		dataRuleFilter(proWorkSum);
		List<Specialty>   specList=specialtyMapper.findAllList(new Specialty());
		List<ProWorkSum>   resultLit=mapper.findProWorkNumList(proWorkSum);
		if(resultLit==null||resultLit.size()==0) {
		}else {
			for(ProWorkSum   prosum:resultLit) {  
				prosum.setStart(start);
				prosum.setEnd(end);
				//查找总工日和总工时
				Map<String, Double> sumTime = mapper.getSumtime(prosum);
				prosum.setSumOverTime(sumTime.get("sumOverTime"));
				prosum.setSumWorkTime(sumTime.get("sumWorkTime"));

				prosum.setOverTimeDay(sumTime.get("overTimeDay"));
				/*// 项目负责人
				prosum.getPro().setSupervisor(supervisorMapper.getUsers(prosum.getPro().getId()));*/
				for(Specialty spec:specList) {
				Map<String, Double>  workTime=	mapper.getProSpecSum(start,end,prosum.getPro().getId(),spec.getId());
						//使用反射赋值
					   Field wField = prosum.getClass().getDeclaredField("work" +spec.getColNum());
					   wField.setAccessible(true);
					   wField.set(prosum, workTime.get("work"));
				       
				       Field oField = prosum.getClass().getDeclaredField("over" +spec.getColNum());
				       oField.setAccessible(true);
				       oField.set(prosum, workTime.get("over"));


					Field dField = prosum.getClass().getDeclaredField("ovday" +spec.getColNum());
					dField.setAccessible(true);
					dField.set(prosum, workTime.get("ovday"));
				}
		}
	}
		return  resultLit;
	}
	
	public List<ProWorkSum> findProPerSonWorkNumList(ProWorkSum proWorkSum) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Date  start=proWorkSum.getStart();
		Date   end =proWorkSum.getEnd();
		
		dataRuleFilter(proWorkSum);
		List<Specialty>   specList=specialtyMapper.findAllList(new Specialty());
		List<ProWorkSum>   resultLit=mapper.findProPersonWorkNumList(proWorkSum);
		if(resultLit==null||resultLit.size()==0) {
		}else {
			for(ProWorkSum   prosum:resultLit) {  
				// 项目负责人
				prosum.getPro().setSupervisor(supervisorMapper.getUsers(prosum.getPro().getId()));
				for(Specialty spec:specList) {
				Map<String, Double>  workTime=	mapper.getProPersonSpecSum(start,end,prosum.getPro().getId(),spec.getId(),prosum.getUser().getId());
						//使用反射赋值
					   Field wField = prosum.getClass().getDeclaredField("work" +spec.getColNum());
					   wField.setAccessible(true);
					   wField.set(prosum, workTime.get("work"));
				       
				       Field oField = prosum.getClass().getDeclaredField("over" +spec.getColNum());
				       oField.setAccessible(true);
				       oField.set(prosum, workTime.get("over"));
				}
		}
	}
		return  resultLit;
	}
	
	/**
	 * 查看项目工日汇总详情
	 * @param endDate 
	 * @param startDate 
	 * @param id
	 * @return
	 */
	public List<WorkAttence> findWorkAttence(String proId, String startDate, String endDate) {
		return mapper.findWorkAttence(proId,startDate,endDate);
	}

	public List<WorkAttence> getUserRecordByDateNo(String userId, String dateStr) {
		// TODO Auto-generated method stub
		return mapper.getUserRecordByDateNo(userId,dateStr);
	}

	

	public Date getLateDay(String userId, Date date) {
		// TODO Auto-generated method stub
		return mapper.getLateDay(userId,date);
	}

	public int getRecord369(String userId, Date date369) {
		// TODO Auto-generated method stub
		return mapper.getRecord369(userId,date369);
	}

}