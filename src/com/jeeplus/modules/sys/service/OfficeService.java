/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.mapper.AreaMapper;
import com.jeeplus.modules.sys.mapper.OfficeMapper;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author jeeplus
 * @version 2017-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeMapper, Office> {

	@Autowired
	private OfficeMapper officeMapper;
	
	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		office.setParentIds(office.getParentIds()+"%");
		return officeMapper.findByParentIdsLike(office);
	}
	
	@Transactional(readOnly = true)
	public Office getByCode(String code){
		return officeMapper.getByCode(code);
	}
	
	public List<Office> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		User user= UserUtils.getUser();
		if (user.isAdmin()) {
			return mapper.getChildren(parentId);
		}else {
			List<Office>  offList=mapper.getChildren(parentId);
			//用户所在部门和公司信息
			String  offId=user.getOffice().getId();
			String   compId=user.getCompany().getId();
			Office  off= mapper.get(offId);
			Office  com= mapper.get(compId);
			
			
			Office  currentOff;
			//所有子节点
			//String childIds;
			//如果上级office为部门,1为公司,2为部门
			
			if ("2".equals(off.getType())&&"1".equals(com.getType())) {
				
				currentOff=com;
			//同为公司
			}else if ("1".equals(off.getType())&&"1".equals(com.getType())) {
				currentOff=off;
			}
			//同为部门
			else if ("2".equals(off.getType())&&"2".equals(com.getType())) {
				currentOff=com;
			}
			
			else if ("1".equals(off.getType())&&"2".equals(com.getType())) {
				currentOff=off;
			}else {
				currentOff=off;
			}
			//所有父节点
			String   parentsIds=currentOff.getParentIds()+currentOff.getId()+",";
			//父节点数组ss
			String[]  parentsArr=parentsIds.split(",");
			//父节点集合
		    List<String> praList = new ArrayList<String>(Arrays.asList(parentsArr));

			//过滤后返回的office
			List<Office> offListNew =new  ArrayList<Office>();
			//过滤数据,判断是否包含
			for(Office  offTmp:offList) {
				if(praList.contains(offTmp.getId().toString())) {
					offListNew.add(offTmp);
				}
			}  
			if (offListNew==null||offListNew.size()==0) {
				return  offList;
			}
			
			return offListNew;
		}
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	@Transactional(readOnly = false)
	public void updateOfficeName(String id, String branchName) {
		mapper.updateOfficeName(id,branchName);
		
	}



	public String getChildList(String parentId) {
	   return	mapper.getChildList(parentId);
		
	}

	public List<Office> getChildrenList(String parentId) {
		// TODO Auto-generated method stub
		return mapper.getChildrenList(parentId);
	}
	
	public  List<Office> findOfficeByRemarks(String  remarks){
		return mapper.findOfficeByRemarks( remarks);
			
		
	}
	
}
