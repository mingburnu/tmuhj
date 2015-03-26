package com.asiaworld.tmuhj.core.apply.accountNumber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.apply.customer.Customer;
import com.asiaworld.tmuhj.core.apply.customer.CustomerService;
import com.asiaworld.tmuhj.core.apply.enums.Role;
import com.asiaworld.tmuhj.core.apply.enums.Status;
import com.asiaworld.tmuhj.core.dao.GenericDaoFull;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;
import com.asiaworld.tmuhj.core.util.EncryptorUtil;
import com.asiaworld.tmuhj.core.util.DsBeanFactory;

/**
 * 使用者 Service
 * 
 * @author Roderick
 * @version 2014/9/30
 */
@Service
public class AccountNumberService extends GenericServiceFull<AccountNumber> {

	@Autowired
	private AccountNumberDao dao;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	protected GenericDaoFull<AccountNumber> getDao() {
		return dao;
	}

	/**
	 * 登入取得帳號資料
	 * 
	 * @param ds
	 * @return
	 * @throws Exception
	 */
	@Override
	public DataSet<AccountNumber> getByRestrictions(DataSet<AccountNumber> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		AccountNumber entity = ds.getEntity();
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		if (StringUtils.isNotEmpty(entity.getUserId())) {
			restrictions.eq("userId", entity.getUserId());
		}
		
		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	public AccountNumber save(AccountNumber entity, AccountNumber user)
			throws Exception {
		Assert.notNull(entity);
		// entity.setTimeToSystime();
		entity.initInsert(user);
		if (StringUtils.isNotEmpty(entity.getUserPw())) { // 密碼非空則進行加密
			final String encryptedPassword = EncryptorUtil.encrypt(entity
					.getUserPw());
			entity.setUserPw(encryptedPassword);
		}

		AccountNumber dbEntity = dao.save(entity);
		makeUserInfo(Arrays.asList(dbEntity));

		return dbEntity;
	}

	/**
	 * 檢查登入帳密
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Boolean checkUserId(AccountNumber entity) throws Exception {
		Assert.notNull(entity);

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		restrictions.eq("userId", entity.getUserId());
		restrictions.ne("role", Role.使用者);
		List<AccountNumber> secUsers = dao.findByRestrictions(restrictions);
		if (secUsers == null || secUsers.isEmpty()) {
			return false;
		} else {
			return true;
		}

	}

	public Boolean checkUserPw(AccountNumber entity) throws Exception {
		Assert.notNull(entity);

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		restrictions.eq("userId", entity.getUserId());
		restrictions.ne("role", Role.使用者);
		restrictions.ne("status", Status.審核中);
		restrictions.ne("status", Status.不生效);
		List<AccountNumber> secUsers = dao.findByRestrictions(restrictions);
		if (secUsers == null || secUsers.isEmpty()) {
			return false;
		}
		AccountNumber secUser = secUsers.get(0);

		return EncryptorUtil.checkPassword(entity.getUserPw(),
				secUser.getUserPw());
	}

	public boolean userIdIsExist(AccountNumber entity) throws Exception {
		Assert.notNull(entity);

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		restrictions.eq("userId", entity.getUserId().trim());
		List<AccountNumber> accountNumbers = dao
				.findByRestrictions(restrictions);
		if (accountNumbers == null || accountNumbers.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public long getSerNoByUserId(String userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AccountNumber.class);
		criteria.add(Restrictions.eq("userId", userId));
		if (criteria.list().size() > 0) {
			return ((AccountNumber) criteria.list().get(0)).getSerNo();
		} else {
			return 0;
		}
	}

	public DataSet<AccountNumber> getByRestrictions(DataSet<AccountNumber> ds,
			AccountNumber loginUser) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		AccountNumber entity = ds.getEntity();
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		List<Role> roleList = new ArrayList<Role>(Arrays.asList(Role.values()));
		List<Role> tempList= new ArrayList<Role>();
		roleList.remove(roleList.size()-1);

		int roleCode=roleList.indexOf(loginUser.getRole());
				
		for (int i = 0; i < roleCode; i++){
			tempList.add(roleList.get(i));
		}
		
		for (int i = 0; i < tempList.size(); i++) {
			restrictions.ne("role", tempList.get(i));
		}

		if (StringUtils.isNotEmpty(entity.getUserId())) {
			restrictions.eq("userId", entity.getUserId());
		}
		
		if (loginUser.getRole() == Role.管理員){
			restrictions.eq("cusSerNo", loginUser.getCusSerNo());
		}
		
		if (entity.getCustomer() != null
				&& StringUtils.isNotEmpty(entity.getCustomer().getName())) {
			List<?> customerList = customerService.getCustomerListByName(entity
					.getCustomer().getName());
			Iterator<?> iterator = customerList.iterator();

			String sql = "";
			while (iterator.hasNext()) {
				Customer customer = (Customer) iterator.next();
				sql = sql + "cus_serNo=" + customer.getSerNo() + " or ";
			}
			if (!sql.isEmpty()) {
				restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
			} else {
				return ds;
			}

		}

		return dao.findByRestrictions(restrictions, ds);
	}

	public boolean hasUser(long cusSerNo) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AccountNumber.class);
		criteria.add(Restrictions.eq("cusSerNo", cusSerNo));
		criteria.setProjection(Projections.rowCount());
		Long totalUser = (Long) criteria.list().get(0);

		if (totalUser > 0) {
			return true;
		} else {
			return false;
		}
	}
}
