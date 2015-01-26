package com.asiaworld.tmuhj.module.apply.feLogs.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.asiaworld.tmuhj.core.dao.GenericDaoLog;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.service.GenericServiceLog;
import com.asiaworld.tmuhj.module.apply.feLogs.entity.FeLogs;
import com.asiaworld.tmuhj.module.apply.feLogs.entity.FeLogsDao;

@Service
public class FeLogsService extends GenericServiceLog<FeLogs> {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private FeLogsDao dao;
	
	@Override
	public DataSet<FeLogs> getByRestrictions(DataSet<FeLogs> ds)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericDaoLog<FeLogs> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
}
