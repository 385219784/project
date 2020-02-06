package com.jeeplus.modules.gen.service;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.gen.entity.GenCustomObj;
import com.jeeplus.modules.gen.mapper.GenCustomObjMapper;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GenCustomObjService extends CrudService<GenCustomObjMapper, GenCustomObj> {
	public GenCustomObj get(String id) {
		return (GenCustomObj) super.get(id);
	}

	public List<GenCustomObj> findList(GenCustomObj genCustomObj) {
		return super.findList(genCustomObj);
	}

	public Page<GenCustomObj> findPage(Page<GenCustomObj> page, GenCustomObj genCustomObj) {
		return super.findPage(page, genCustomObj);
	}

	@Transactional(readOnly = false)
	public void save(GenCustomObj genCustomObj) {
		super.save(genCustomObj);
	}

	@Transactional(readOnly = false)
	public void delete(GenCustomObj genCustomObj) {
		super.delete(genCustomObj);
	}
}