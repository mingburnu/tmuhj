package j.index.module.apply.resourcesUnion.service;

import java.util.ArrayList;
import java.util.Iterator;

import j.index.core.dao.GenericDaoSerNo;
import j.index.core.dao.IiiRestrictions;
import j.index.core.model.DataSet;
import j.index.core.service.GenericServiceSerNo;
import j.index.core.util.IiiBeanFactory;
import j.index.module.apply.resourcesUnion.entity.ResourcesUnion;
import j.index.module.apply.resourcesUnion.entity.ResourcesUnionDao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

		IiiRestrictions restrictions = IiiBeanFactory.getIiiRestrictions();
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
}
