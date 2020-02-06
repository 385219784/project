/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.project.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.pm.modules.project.entity.Principal;
import com.pm.modules.project.entity.Project;
import com.pm.modules.project.entity.Specialty;
import com.pm.modules.project.entity.SpecialtyUser;
import com.pm.modules.project.entity.Supervisor;
import com.pm.modules.project.mapper.ProjectMapper;
import com.pm.modules.project.mapper.SpecialtyMapper;
import com.pm.modules.project.mapper.SpecialtyUserMapper;
import com.pm.modules.project.mapper.SupervisorMapper;

/**
 * 项目管理Service
 * @author yt
 * @version 2018-10-15
 */
@Service
@Transactional(readOnly = true)
public class ProjectService extends CrudService<ProjectMapper, Project> {

	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	private SupervisorMapper supervisorMapper;

	@Autowired
	private SpecialtyUserMapper specialtyUserMapper;
	
	@Autowired
	private SpecialtyMapper specialtyMapper;
	
	public Project get(String id) {
		Project project = super.get(id);
		return project;
	}
	
	public List<Project> findList(Project project) {
		return super.findList(project);
	}
	
	public Page<Project> findPage(Page<Project> page, Project project) {
		Page<Project> projectPage = super.findPage(page, project);
		List<Project> projectList = projectPage.getList();
		//查找所有专业
		List<Specialty> specialtyList = specialtyMapper.findAllList(new Specialty());
		for(Project projectObj : projectList){
			/*//查找项目主管 
			String supervisor = supervisorMapper.getUsers(projectObj.getId());
			projectObj.setSupervisor(supervisor);
			//查找项目负责人
			String principal = supervisorMapper.getPrincipal(projectObj.getId());
			projectObj.setPrincipal(principal);*/
			for(Specialty specialty : specialtyList){
				//根据项目id和专业id查找专业人员
				String userName = projectMapper.getSpecialtyUserListByProIdAndSpecId(projectObj.getId(),specialty.getId());
				//使用反射赋值
				Field wField;
				try {
					wField = projectObj.getClass().getDeclaredField("spec" +specialty.getColNum());
					wField.setAccessible(true);
					wField.set(projectObj, userName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return projectPage;
	}
	
	public Page<Project> findPages(Page<Project> page, Project project) throws Exception {
		Page<Project> projectPage = super.findPage(page, project);
		List<Project> projectList = projectPage.getList();
		for(int i=0; i<projectList.size(); i++){
			projectList.get(i).setSerial(i+1);
		}
		return projectPage;
	}
	
	@Transactional(readOnly = false)
	public synchronized void save(Project project) {
		/*if(project.getId() == null || project.getId().equals("")){
			//查找最大序号
			Integer serial = projectMapper.getMaxSerial();
			project.setSerial(serial+1);
		}*/
		//项目状态为已完成
		if(project.getState() != null && project.getState().equals("5")){
			//项目进度改成已完成
			project.setPlanState("10");
		}
		if(project.getId() == null || project.getId().equals("")){
			Pattern pattern = Pattern.compile("[^0-9]");
			String isAuto = pattern.matcher(project.getNum()).replaceAll("").trim();
			if(isAuto == null || isAuto.equals("")){
				//项目编号
				StringBuffer num = new StringBuffer(project.getNum().toUpperCase());
				int i = 0;
				String str = "";
				for(int j=9;j>=0;j--){
					num.append(j);
					//查找序列数字
					str = projectMapper.getNum(num.toString());
					num.deleteCharAt(num.length()-1);
					if(str != null && !str.equals("")){
						break;
					}
				}
				if(str != null){
					i = Integer.valueOf(pattern.matcher(str).replaceAll("").trim());
				}
				if(str == null){
					str = "001";
				}else if(i < 9){
					str = "00" + String.valueOf(i+1);
				}else if(i < 99){
					str = "0" + String.valueOf(i+1);
				}else if(i < 999){
					str = String.valueOf(i+1);
				}
				num.append(str);
				project.setNum(num.toString());
			}
		}
		
		super.save(project);
		// 项目主管
		List<Supervisor> supervisorList = project.getSupervisorList();
		supervisorMapper.delete(new Supervisor(null,project.getId()));
		for (Supervisor supervisor : supervisorList){
			supervisor.setProId(project.getId());
			supervisorMapper.insert(supervisor);
		}
		// 项目负责人
		List<Principal> principalList = project.getPrincipalList();
		supervisorMapper.deletePrincipal(project.getId());
		for (Principal principal : principalList){
			principal.setProId(project.getId());
			supervisorMapper.insertPrincipal(principal);
		}
		// 专业人员
		List<SpecialtyUser> specialtyUserList = project.getSpecialtyUserList();
		specialtyUserMapper.delete(new SpecialtyUser(null,project.getId()));
		for(SpecialtyUser specialtyUser : specialtyUserList){
			specialtyUser.setProId(project.getId());
			specialtyUserMapper.insert(specialtyUser);
		}
		
	}
	
	@Transactional(readOnly = false)
	public void delete(Project project) {
		super.delete(project);
		//删除专业人员
		specialtyUserMapper.deleteByLogic(new SpecialtyUser(null,project.getId()));
		//删除主管领导
		supervisorMapper.deleteByLogic(new Supervisor(null,project.getId()));
		//删除项目负责人
		supervisorMapper.deletePrincipalUser(project.getId());
	}

	/**
	 * 查找所有的用户
	 * @return
	 */
	public List<Supervisor> getAllUser(String specId) {
		return projectMapper.getAllUser(specId);
	}

	/**
	 * 根据项目id查找项目主管
	 * @param id
	 * @return
	 */
	public List<Supervisor> getSupervisorListByProId(String proId) {
		return projectMapper.getSupervisorListByProId(proId);
	}

	/**
	 * 根据项目id查找专业人员
	 * @param id
	 * @return
	 */
	public List<SpecialtyUser> getSpecialtyUserListByProId(String proId) {
		return projectMapper.getSpecialtyUserListByProId(proId);
	}
	
	/**
	 * 查找项目主管 
	 * @param id
	 * @return
	 */
	public String getSupervisors(String proId) {
		return supervisorMapper.getUsers(proId);
	}

	/**
	 * 根据项目id和专业id查找专业人员
	 * @param id
	 * @param id2
	 * @return
	 */
	public String getSpecialtyUserListByProIdAndSpecId(String proId, String specId) {
		return projectMapper.getSpecialtyUserListByProIdAndSpecId(proId,specId);
	}

	/**
	 * 改变项目状态
	 * @param id
	 * @param state
	 */
	@Transactional(readOnly = false)
	public void updateState(Project project) {
		projectMapper.updateState(project);
	}

	/**
	 * 得到项目状态
	 * @param id
	 * @param state
	 * @return
	 */
	public int getProState(String id) {
		return projectMapper.getProState(id);
	}

	/**
	 * 查找所有项目主管
	 * @return
	 */
	public List<Supervisor> getAllSuperUser() {
		return projectMapper.getAllSuperUser();
	}
	
	/**
	 * 查找所有项目负责人
	 * @return
	 */
	public List<Principal> getAllPrincipalUser() {
		return projectMapper.getAllPrincipalUser();
	}

	/**
	 * 查找项目主管 
	 * @param id
	 * @return
	 */
	public String getUsers(String proId) {
		return supervisorMapper.getUsers(proId);
	}
	
	public List<String> getLeaderByproId(String proId) {
		// TODO Auto-generated method stub
		return mapper.getLeaderByproId(proId);
	}

	public List<String> getUserListByProId(String proId) {
		// TODO Auto-generated method stub
		return mapper.getUserListByProId(proId);
	}

	/**
	 * 根据项目id查找项目负责人
	 * @param id
	 * @return
	 */
	public List<Principal> getPrincipalListsByProId(String proId) {
		return projectMapper.getPrincipalListsByProId(proId);
	}

	/**
	 * 查找项目负责人
	 * @param id
	 * @return
	 */
	public String getPrincipal(String proId) {
		return supervisorMapper.getPrincipal(proId);
	}

	public Page<Project> findPagedataUser(Page<Project> page, Project project) {
		dataRuleFilter(project);
		project.setPage(page);
		page.setList(mapper.findPagedataUser(project));
		return page;
		
	}

	public Page<Project> findPagedataUserDgt(Page<Project> page, Project project) {
		dataRuleFilter(project);
		project.setPage(page);
		page.setList(mapper.findPagedataUserDgt(project));
		return page;
	}

	/**
	 * 根据项目id修改所有专业状态和进度为已完成
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void updateSpecialtyState(String proId) {
		mapper.updateSpecialtyState(proId);
	}

	public  List<String> getNotifyProject() {
		// TODO Auto-generated method stub
		return mapper.getNotifyProject();
	}

	public List<String> getFzrList(String proId) {
		// TODO Auto-generated method stub
		return mapper.getFzrList(proId);
	}

	public List<String> getZgList(String proId) {
		// TODO Auto-generated method stub
		return mapper.getZgList(proId);
	}

}