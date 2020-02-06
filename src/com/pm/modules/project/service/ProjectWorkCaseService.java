/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pm.modules.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.utils.IdGen;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.pm.modules.project.entity.ProjectWorkCase;
import com.pm.modules.project.mapper.ProjectWorkCaseMapper;

/**
 * 个人工作情况统计Service
 * @author yt
 * @version 2018-10-16
 */
@Service
@Transactional(readOnly = true)
public class ProjectWorkCaseService extends CrudService<ProjectWorkCaseMapper, ProjectWorkCase> {

	@Autowired
	private ProjectWorkCaseMapper projectWorkCaseMapper;
	
	public ProjectWorkCase get(ProjectWorkCase projectWorkCase) {
		return projectWorkCaseMapper.get(projectWorkCase);
	}
	
	public List<ProjectWorkCase> findList(ProjectWorkCase projectWorkCase) {
		return super.findList(projectWorkCase);
	}
	
	public Page<ProjectWorkCase> findPage(Page<ProjectWorkCase> page, ProjectWorkCase projectWorkCase) {
		Page<ProjectWorkCase> projectWorkCasePage = super.findPage(page, projectWorkCase);
		List<ProjectWorkCase> projectWorkCaseList = projectWorkCasePage.getList();
		for(ProjectWorkCase projectWorkCaseObj : projectWorkCaseList){
			//根据项目id和专业id,用户id查找工作情况
			ProjectWorkCase projectWork = projectWorkCaseMapper.getProjectWork(projectWorkCaseObj);
			if(projectWork != null){
				projectWorkCaseObj.setContent(projectWork.getContent());
				projectWorkCaseObj.setPlan(projectWork.getPlan());
				projectWorkCaseObj.setFinishDate(projectWork.getFinishDate());
				projectWorkCaseObj.setDifficulty(projectWork.getDifficulty());
				projectWorkCaseObj.setSpecState(projectWork.getSpecState());
			}
		}
		return projectWorkCasePage;
	}
	
	/**
	 * 工作情况汇总
	 * @param page
	 * @param projectWorkCase
	 * @return
	 */
	public Page<ProjectWorkCase> findPages(Page<ProjectWorkCase> page, ProjectWorkCase projectWorkCase) {
		dataRuleFilter(projectWorkCase);
		projectWorkCase.setPage(page);
		Page<ProjectWorkCase> projectWorkCasePage = page.setList(mapper.findLists(projectWorkCase));
		List<ProjectWorkCase> projectWorkCaseList = projectWorkCasePage.getList();
		for(ProjectWorkCase projectWorkCaseObj : projectWorkCaseList){
			//根据项目id和专业id,用户id查找工作情况
			ProjectWorkCase projectWork = projectWorkCaseMapper.getProjectWork(projectWorkCaseObj);
			if(projectWork != null){
				projectWorkCaseObj.setSpecState(projectWork.getSpecState());
				projectWorkCaseObj.setContent(projectWork.getContent());
				projectWorkCaseObj.setPlan(projectWork.getPlan());
				projectWorkCaseObj.setFinishDate(projectWork.getFinishDate());
				projectWorkCaseObj.setDifficulty(projectWork.getDifficulty());
			}
		}
		return projectWorkCasePage;
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectWorkCase projectWorkCase) {
		String[] idStr = projectWorkCase.getId().split(",");
		//项目状态为已完成
		if(projectWorkCase.getSpecState() != null && projectWorkCase.getSpecState().equals("5")){
			//项目进度改成已完成
			projectWorkCase.setPlan("10");
		}
		//得到id
		String workId = projectWorkCaseMapper.getId(projectWorkCase);
		if(workId != null && !workId.equals("")){
			projectWorkCase.setProId(idStr[0]);
			projectWorkCase.setSpecId(idStr[1]);
			projectWorkCase.setId(workId);
			projectWorkCaseMapper.update(projectWorkCase);
		}else{
			projectWorkCase.setId(IdGen.uuid());
			projectWorkCase.preInsert();
			projectWorkCaseMapper.insert(projectWorkCase);
		}
		super.save(projectWorkCase);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProjectWorkCase projectWorkCase) {
		super.delete(projectWorkCase);
	}

	/**
	 * 得到id
	 * @param proId
	 * @param specid
	 * @return
	 */
	public String getId(ProjectWorkCase projectWorkCase) {
		return projectWorkCaseMapper.getId(projectWorkCase);
	}

	/**
	 * 根据项目id和专业id查找工作情况
	 * @param projectWorkCase
	 * @return
	 */
	public ProjectWorkCase getProjectWork(ProjectWorkCase projectWorkCase) {
		return projectWorkCaseMapper.getProjectWork(projectWorkCase);
	}
	
}