package com.asiaworld.tmuhj.module.apply.resourcesUnion.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDaoSerNo;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.service.GenericServiceSerNo;
import com.asiaworld.tmuhj.core.util.DsBeanFactory;
import com.asiaworld.tmuhj.module.apply.ebook.entity.Ebook;
import com.asiaworld.tmuhj.module.apply.journal.entity.Journal;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.entity.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.entity.ResourcesUnionDao;

@Service
public class ResourcesUnionService extends GenericServiceSerNo<ResourcesUnion> {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ResourcesUnionDao dao;

	@Override
	public DataSet<ResourcesUnion> getByRestrictions(DataSet<ResourcesUnion> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDaoSerNo<ResourcesUnion> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public ArrayList<ResourcesUnion> totalDb(long cusSerNo) {
		Session session = sessionFactory.getCurrentSession();
		ArrayList<ResourcesUnion> totalList = new ArrayList<ResourcesUnion>();

		Criteria criteria = session.createCriteria(ResourcesUnion.class);
		LogicalExpression andOperator = Restrictions.and(
				Restrictions.eq("cusSerNo", cusSerNo),
				Restrictions.gt("datSerNo", 0L));
		criteria.add(andOperator);

		Iterator<?> iterator = criteria.list().iterator();
		while (iterator.hasNext()) {
			ResourcesUnion resourcesUnion = (ResourcesUnion) iterator.next();
			totalList.add(resourcesUnion);
		}

		return totalList;
	}

	public Integer countTotalDb(long cusSerNo) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(ResourcesUnion.class);
		LogicalExpression andOperator = Restrictions.and(
				Restrictions.eq("cusSerNo", cusSerNo),
				Restrictions.gt("datSerNo", 0L));
		criteria.add(andOperator);
		List<?> list = criteria.list();
		int count = list.size();
		System.out.println("total Db: " + count);
		return count;
	}

	public ArrayList<ResourcesUnion> totalJournal(long cusSerNo) {
		Session session = sessionFactory.getCurrentSession();
		ArrayList<ResourcesUnion> totalList = new ArrayList<ResourcesUnion>();

		Criteria criteria = session.createCriteria(ResourcesUnion.class);
		LogicalExpression andOperator = Restrictions.and(
				Restrictions.eq("cusSerNo", cusSerNo),
				Restrictions.gt("jouSerNo", 0L));
		criteria.add(andOperator);

		Iterator<?> iterator = criteria.list().iterator();
		while (iterator.hasNext()) {
			ResourcesUnion resourcesUnion = (ResourcesUnion) iterator.next();
			totalList.add(resourcesUnion);
		}

		return totalList;
	}

	public Integer countTotalJournal(long cusSerNo) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(ResourcesUnion.class);
		LogicalExpression andOperator = Restrictions.and(
				Restrictions.eq("cusSerNo", cusSerNo),
				Restrictions.gt("jouSerNo", 0L));
		criteria.add(andOperator);
		List<?> list = criteria.list();
		int count = list.size();
		System.out.println("total Journal: " + count);
		return count;
	}

	public ArrayList<ResourcesUnion> totalEbook(long cusSerNo) {
		Session session = sessionFactory.getCurrentSession();
		ArrayList<ResourcesUnion> totalList = new ArrayList<ResourcesUnion>();

		Criteria criteria = session.createCriteria(ResourcesUnion.class);
		LogicalExpression andOperator = Restrictions.and(
				Restrictions.eq("cusSerNo", cusSerNo),
				Restrictions.gt("ebkSerNo", 0L));
		criteria.add(andOperator);

		Iterator<?> iterator = criteria.list().iterator();
		while (iterator.hasNext()) {
			ResourcesUnion resourcesUnion = (ResourcesUnion) iterator.next();
			totalList.add(resourcesUnion);
		}

		return totalList;
	}

	public Integer countTotalEbook(long cusSerNo) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(ResourcesUnion.class);
		LogicalExpression andOperator = Restrictions.and(
				Restrictions.eq("cusSerNo", cusSerNo),
				Restrictions.gt("ebkSerNo", 0L));
		criteria.add(andOperator);
		List<?> list = criteria.list();
		int count = list.size();
		System.out.println("total Ebook: " + count);
		return count;
	}

	public ResourcesUnion getByObjSerNo(long objSerNo,
			Class<?> objClass) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ResourcesUnion.class);
		criteria.setFirstResult(0);
		criteria.setMaxResults(1);

		if (objClass.equals(Journal.class)) {
			criteria.add(Restrictions.eq("jouSerNo", objSerNo));
		} else if (objClass.equals(Ebook.class)) {
			criteria.add(Restrictions.eq("ebkSerNo", objSerNo));
		} else {
			criteria.add(Restrictions.eq("datSerNo", objSerNo));
		}

		ResourcesUnion resourcesUnion = (ResourcesUnion) criteria.list().get(0);

		return resourcesUnion;
	}

	public List<?> getByJouSerNo(long jouSerNo) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ResourcesUnion.class);
		
		criteria.add(Restrictions.eq("jouSerNo", jouSerNo));
		
		List<?> journalResourcesUnionList=criteria.list();

		return journalResourcesUnionList;
	}
}