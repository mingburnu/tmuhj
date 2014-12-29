package com.asiaworld.tmuhj.module.apply.database.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDaoFull;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;
import com.asiaworld.tmuhj.core.util.DsBeanFactory;
import com.asiaworld.tmuhj.module.apply.database.entity.Database;
import com.asiaworld.tmuhj.module.apply.database.entity.DatabaseDao;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.entity.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.service.ResourcesUnionService;

@Service
public class DatabaseService extends GenericServiceFull<Database> {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private DatabaseDao dao;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

	@Override
	public DataSet<Database> getByRestrictions(DataSet<Database> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		HttpServletRequest request = ServletActionContext.getRequest();
		String keywords = request.getParameter("keywords");

		String option = request.getParameter("option");
		
		if(option.equals("中文題名")){
			option="DBchttitle";
		}else if(option.equals("英文題名")){
			option="DBengtitle";
		}else if(option.equals("出版社")){
			option="publishname";
		}else if(option.equals("內容描述")){
			option="Content";
		}
		
		String recordPerPage = request.getParameter("recordPerPage");
		if (recordPerPage != null) {
			Pager pager = ds.getPager();
			pager.setRecordPerPage(Integer.parseInt(recordPerPage));
			ds.setPager(pager);
		}
		if (StringUtils.isNotEmpty(keywords)) {
			char[] cArray = keywords.toCharArray();
			keywords = "";
			for (int i = 0; i < cArray.length; i++) {
				int charCode = (int) cArray[i];
				if (charCode > 65280 && charCode < 65375) {
					int halfChar = charCode - 65248;
					cArray[i] = (char) halfChar;
				}
				keywords += cArray[i];
			}

			keywords = keywords.replaceAll(
					"[^a-zA-Z0-9\u4e00-\u9fa5\u0391-\u03a9\u03b1-\u03c9]", " ");
			String[] wordArray = keywords.split(" ");
			String sql = "";

			for (int i = 0; i < wordArray.length; i++) {
				if (wordArray[i].isEmpty() == false) {
					sql = sql + "LOWER(" + option + ") like LOWER('%"
							+ wordArray[i] + "%') or ";
				}
			}

			restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
		}
		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDaoFull<Database> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public DataSet<Database> getBySql(DataSet<Database> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		HttpServletRequest request = ServletActionContext.getRequest();

		String keywords = request.getParameter("keywords");

		String recordPerPage = request.getParameter("recordPerPage");
		if (recordPerPage != null) {
			Pager pager = ds.getPager();
			pager.setRecordPerPage(Integer.parseInt(recordPerPage));
			ds.setPager(pager);
		}
		if (StringUtils.isNotEmpty(keywords)) {
			char[] cArray = keywords.toCharArray();
			keywords = "";
			for (int i = 0; i < cArray.length; i++) {
				int charCode = (int) cArray[i];
				if (charCode > 65280 && charCode < 65375) {
					int halfChar = charCode - 65248;
					cArray[i] = (char) halfChar;
				}
				keywords += cArray[i];
			}

			keywords = keywords.replaceAll(
					"[^a-zA-Z0-9\u4e00-\u9fa5\u0391-\u03a9\u03b1-\u03c9]", " ");
			String[] wordArray = keywords.split(" ");
			String sql = "";

			for (int i = 0; i < wordArray.length; i++) {
				if (wordArray[i].isEmpty() == false) {
					sql = sql + "LOWER(DBchttitle) like LOWER('%"
							+ wordArray[i]
							+ "%') or  LOWER(DBengtitle) like LOWER('%"
							+ wordArray[i] + "%') or ";
				}

			}

			restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
		}

		return dao.findByRestrictions(restrictions, ds);
	}

	public DataSet<Database> getByCusSerNo(DataSet<Database> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		HttpServletRequest request = ServletActionContext.getRequest();
		String cusSerNo = request.getParameter("cusSerNo");

		String recordPerPage = request.getParameter("recordPerPage");
		if (recordPerPage != null) {
			Pager pager = ds.getPager();
			pager.setRecordPerPage(Integer.parseInt(recordPerPage));
			ds.setPager(pager);
		}

		ArrayList<ResourcesUnion> resourcesUnionList = null;
		if (NumberUtils.isDigits(cusSerNo)) {
			resourcesUnionList = resourcesUnionService.totalDb(Long
					.parseLong(cusSerNo));
		}

		String sql = "";
		for (int i = 0; i < resourcesUnionList.size(); i++) {
			sql = sql + "serNo=" + resourcesUnionList.get(i).getDatSerNo()
					+ " or ";
		}

		restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
		return dao.findByRestrictions(restrictions, ds);
	}
}