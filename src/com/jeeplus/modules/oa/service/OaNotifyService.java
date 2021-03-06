/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.oa.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.websocket.service.system.SystemInfoSocketHandler;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.entity.OaNotifyRecord;
import com.jeeplus.modules.oa.mapper.OaNotifyMapper;
import com.jeeplus.modules.oa.mapper.OaNotifyRecordMapper;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 通知通告Service
 * @author jeeplus
 * @version 2017-05-16
 */
@Service
@Transactional(readOnly = true)
public class OaNotifyService extends CrudService<OaNotifyMapper, OaNotify> {

	@Autowired
	private OaNotifyRecordMapper oaNotifyRecordMapper;

	public OaNotify get(String id) {
		OaNotify entity = mapper.get(id);
		return entity;
	}
	
	/**
	 * 获取通知发送记录
	 * @param oaNotify
	 * @return
	 */
	public OaNotify getRecordList(OaNotify oaNotify) {
		oaNotify.setOaNotifyRecordList(oaNotifyRecordMapper.findList(new OaNotifyRecord(oaNotify)));
		return oaNotify;
	}
	
	public Page<OaNotify> find(Page<OaNotify> page, OaNotify oaNotify) {
		oaNotify.setPage(page);
		page.setList(mapper.findList(oaNotify));
		return page;
	}
	
	/**
	 * 获取通知数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(OaNotify oaNotify) {
		return mapper.findCount(oaNotify);
	}
	
	@Transactional(readOnly = false)
	public void save(OaNotify oaNotify) {
		super.save(oaNotify);
		
		// 更新发送接受人记录
		oaNotifyRecordMapper.deleteByOaNotifyId(oaNotify.getId());
		if (oaNotify.getOaNotifyRecordList().size() > 0){
			oaNotifyRecordMapper.insertAll(oaNotify.getOaNotifyRecordList());
		}
	}
	
	/**
	 * 更新阅读状态
	 */
	@Transactional(readOnly = false)
	public void updateReadFlag(OaNotify oaNotify) {
		OaNotifyRecord oaNotifyRecord = new OaNotifyRecord(oaNotify);
		oaNotifyRecord.setUser(oaNotifyRecord.getCurrentUser());
		oaNotifyRecord.setReadDate(new Date());
		oaNotifyRecord.setReadFlag("1");
		oaNotifyRecordMapper.update(oaNotifyRecord);
	}
	
	@Transactional(readOnly = false)
	public void saveOa(OaNotify oaNotify) {
		this.save(oaNotify);
		if("1".equals(oaNotify.getStatus())){
			List<OaNotifyRecord> list = oaNotify.getOaNotifyRecordList();
			for(OaNotifyRecord o : list){
				//发送通知到客户端
				ServletContext context = SpringContextHolder
						.getBean(ServletContext.class);
				new SystemInfoSocketHandler().sendMessageToUser(UserUtils.get(o.getUser().getId()).getLoginName(), "收到一条新通知，请到我的通知查看！");
			}
		}
	}
	
	
	
}