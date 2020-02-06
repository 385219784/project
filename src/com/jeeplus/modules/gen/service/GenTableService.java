package com.jeeplus.modules.gen.service;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.BaseService;
import com.jeeplus.modules.gen.entity.GenTable;
import com.jeeplus.modules.gen.entity.GenTableColumn;
import com.jeeplus.modules.gen.mapper.GenDataBaseDictMapper;
import com.jeeplus.modules.gen.mapper.GenTableColumnMapper;
import com.jeeplus.modules.gen.mapper.GenTableMapper;
import com.jeeplus.modules.gen.util.a;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GenTableService extends BaseService {

	@Autowired
	private GenTableMapper genTableMapper;

	@Autowired
	private GenTableColumnMapper genTableColumnMapper;

	@Autowired
	private GenDataBaseDictMapper genDataBaseDictMapper;

	public GenTable a1(String id) {
		GenTable a1 = (GenTable) this.genTableMapper.get(id);
		if (a1.getGenIdType() == null) {
			a1.setGenIdType("1");
		}
		GenTableColumn a2 = new GenTableColumn();
		a2.setGenTable(new GenTable(a1.getId()));
		a1.setColumnList(this.genTableColumnMapper.findList(a2));
		a1.setAllColumnList(this.genTableColumnMapper.findListAllStatus(a1));
		return a1;
	}

	public Page<GenTable> a2(Page<GenTable> a5, GenTable a4) {
		a4.setPage(a5);
		a5.setList(this.genTableMapper.findList(a4));
		return a5;
	}

	public List<GenTable> a3() {
		return this.genTableMapper.findAllList(new GenTable());
	}

	public List<GenTable> a4(GenTable a6) {
		return this.genDataBaseDictMapper.findTableList(a6);
	}

	public boolean a5(String a7) {
		if (StringUtils.isBlank(a7)) {
			return true;
		}else {
			GenTable a6 = new GenTable();
			a6.setName(a7);
			List<GenTable> list = this.genTableMapper.findList(a6);
			return list.size() == 0;
		}
	}

	public boolean a7(String a8) {
		if (StringUtils.isBlank(a8)) {
			return true;
		}
		GenTable a1 = new GenTable();
		a1.setName(a8);
		List<GenTable> a2 = this.genDataBaseDictMapper.findTableListByName(a1);
		return a2.size() == 0;
	}

	public GenTable a5(GenTable a1) {
		if (StringUtils.isNotBlank(a1.getName())) {
			List<GenTable> a2 = this.genDataBaseDictMapper.findTableListByName(a1);
			if (a2.size() > 0) {
				if (StringUtils.isBlank(a1.getId())) {
					a1 = (GenTable) a2.get(0);

					if (StringUtils.isBlank(a1.getComments())) {
						a1.setComments(a1.getName());
					}
					a1.setClassName(StringUtils.toCapitalizeCamelCase(a1.getName()));
				}

				List<GenTableColumn> a3 = this.genDataBaseDictMapper.findTableColumnList(a1);
				for (GenTableColumn a4 : a3) {
					boolean b = false;
					for (GenTableColumn e : a1.getColumnList()) {
						if ((e.getName() != null) && (e.getName().equals(a4.getName()))) {
							b = true;
						}
					}
					if (!b) {
						a1.getColumnList().add(a4);
					}

				}

				Iterator var4 = a1.getColumnList().iterator(), var7;
				GenTableColumn e;
				boolean b;
				GenTableColumn column;
				while(var4.hasNext()) {
					e = (GenTableColumn)var4.next();
					b = false;
					var7 = a3.iterator();

					while(var7.hasNext()) {
						column = (GenTableColumn)var7.next();
						if(column.getName().equals(e.getName())) {
							b = true;
						}
					}

					if(!b) {
						e.setDelFlag("1");
					}
				}
				List<String> a5 = this.genDataBaseDictMapper.findTablePK(a1);
				a1.setPkList(a5);
				if (a5.size() > 0) {
					for (GenTableColumn a6 : a1.getColumnList()) {
						if (((String) a5.get(0)).equalsIgnoreCase(a6.getName())) {
							if ((a6.getJdbcType().toLowerCase().contains("integer")) || (a6.getJdbcType().toLowerCase().contains("int")))
								a1.setGenIdType("2");
							else {
								a1.setGenIdType("1");
							}
						}

					}
				}
				a.a4(a1);
			}
		}

		return a1;
	}

	@Transactional(readOnly = false)
	public void b1(GenTable a1) {
		a1.setIsSync("0");
		GenTable a2 = null;
		if (StringUtils.isBlank(a1.getId())) {
			a1.preInsert();
			this.genTableMapper.insert(a1);
		} else {
			a2 = a1(a1.getId());
			a1.preUpdate();
			this.genTableMapper.update(a1);
		}

		HashSet a3 = new HashSet();

		GenTableColumn a4;
		for (Iterator localIterator = a1.getColumnList().iterator(); localIterator.hasNext(); ) {
			a4 = (GenTableColumn) localIterator.next();
			a4.setGenTable(a1);
			if (StringUtils.isBlank(a4.getId())) {
				a4.preInsert();
				this.genTableColumnMapper.insert(a4);
			} else {
				a3.add(a4.getId());
				a4.preUpdate();
				this.genTableColumnMapper.update(a4);
			}
		}
		if (a2 != null) {
			List<GenTableColumn> a5 = a2.getAllColumnList();

			for (GenTableColumn c1 : a5)
				if (!a3.contains(c1.getId()))
					this.genTableColumnMapper.delete(c1);
		}
	}

	@Transactional(readOnly = false)
	public void b2(GenTable a2) {
		boolean a1 = true;
		GenTable a3 = null;
		Iterator localIterator;
		if (StringUtils.isBlank(a2.getId())) {
			a1 = false;
		} else {
			a3 = a1(a2.getId());
			if ((a3.getColumnList().size() != a2.getColumnList().size()) || (!a3.getName().equals(a2.getName())) || (!a3.getComments().equals(a2.getComments())) || (!a3.getGenIdType().equals(a2.getGenIdType())))
				a1 = false;
			else {
				GenTableColumn a4;
				for (localIterator = a2.getColumnList().iterator(); localIterator.hasNext(); ) {
					a4 = (GenTableColumn) localIterator.next();
					if (StringUtils.isBlank(a4.getId())) {
						a1 = false;
					} else {
						GenTableColumn a5 = (GenTableColumn) this.genTableColumnMapper.get(a4.getId());
						if ((!a5.getName().equals(a4.getName())) ||
								(!a5
										.getJdbcType().equals(a4.getJdbcType())) ||
								(!a5
										.getIsPk().equals(a4.getIsPk())) ||
								(!a5
										.getComments().equals(a4.getComments()))) {
							a1 = false;
						}

					}

				}

			}

		}

		if (!a1) {
			a2.setIsSync("0");
		}

		if (StringUtils.isBlank(a2.getId())) {
			a2.preInsert();
			this.genTableMapper.insert(a2);
		} else {
			a2.preUpdate();
			a2.setOldName(a3.getOldName());
			a2.setOldComments(a3.getOldComments());
			a2.setOldGenIdType(a3.getOldGenIdType());
			this.genTableMapper.update(a2);
		}

		HashSet<String> a6 = new HashSet();
		GenTableColumn a7, a8;

		Iterator<GenTableColumn> a4 = a2.getColumnList().iterator();
		while(a4.hasNext()) {
			a7 = a4.next();
			if (StringUtils.isBlank(a7.getId())) {
				a7.setGenTable(a2);
				a7.preInsert();
				this.genTableColumnMapper.insert(a7);
			} else {
				a6.add(a7.getId());
				a8 = this.genTableColumnMapper.get(a7.getId());
				a7.preUpdate();
				a7.setOldComments(a8.getOldComments());
				a7.setOldIsPk(a8.getOldIsPk());
				a7.setOldJdbcType(a8.getOldJdbcType());
				a7.setOldName(a8.getOldName());
				this.genTableColumnMapper.update(a7);
			}
		}

		if (a3 != null) {
			List<GenTableColumn> a9 = a3.getColumnList();
			for (GenTableColumn b1 : a9){
				if (!a6.contains(b1.getId())){
					if (b1.getOldName() != null) {
						this.genTableColumnMapper.deleteByLogic(b1);
					} else{
						this.genTableColumnMapper.delete(b1);
					}
				}
			}
		}
	}

	@Transactional(readOnly = false)
	public void c(GenTable a1) {
		a1.setIsSync("1");
		a1.setOldName(a1.getName());
		a1.setOldComments(a1.getComments());
		a1.setOldGenIdType(a1.getGenIdType());

		for (GenTableColumn b1 : a1.getAllColumnList()) {
			if ("1".equals(b1.getDelFlag())) {
				this.genTableColumnMapper.delete(b1);
			} else {
				b1.setOldComments(b1.getComments());
				b1.setOldIsPk(b1.getIsPk());
				b1.setOldJdbcType(b1.getJdbcType());
				b1.setOldName(b1.getName());
				this.genTableColumnMapper.update(b1);
			}

		}

		this.genTableMapper.update(a1);
	}

	@Transactional(readOnly = false)
	public void d(GenTable a1) {
		a1.preInsert();
		a1.setOldName(a1.getName());
		a1.setOldComments(a1.getComments());
		a1.setOldGenIdType(a1.getGenIdType());
		this.genTableMapper.insert(a1);

		for (GenTableColumn b : a1.getColumnList()) {
			b.setGenTable(a1);
			b.setId(null);
			b.preInsert();
			b.setOldComments(b.getComments());
			b.setOldIsPk(b.getIsPk());
			b.setOldJdbcType(b.getJdbcType());
			b.setOldName(b.getName());
			this.genTableColumnMapper.insert(b);
		}
	}

	@Transactional(readOnly = false)
	public void e(GenTable c) {
		this.genTableMapper.delete(c);
		this.genTableColumnMapper.deleteByGenTable(c);
	}

	@Transactional(readOnly = false)
	public void f(String a) {
		if (StringUtils.isNotBlank(a))
			this.genTableMapper.buildTable(a);
	}

	@Transactional(readOnly = false)
	public void x(String d) {
		String[] a = d.toString().split(";");
		for (String c : a)
			if (StringUtils.isNotBlank(c))
				this.genTableMapper.buildTable(c);
	}
}