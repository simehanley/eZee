package com.ezee.dao.impl;

import java.util.List;

import com.ezee.dao.EzeeProjectDao;
import com.ezee.model.entity.project.EzeeProject;

public class EzeeProjectDaoImpl extends EzeeBaseDaoImpl<EzeeProject> implements EzeeProjectDao {

	@Override
	public EzeeProject get(long id) {
		return super.get(id, EzeeProject.class);
	}

	@Override
	public List<EzeeProject> get() {
		return super.get(EzeeProject.class);
	}
}