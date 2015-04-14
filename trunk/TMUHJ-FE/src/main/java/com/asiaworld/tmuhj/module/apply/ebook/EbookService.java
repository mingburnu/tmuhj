package com.asiaworld.tmuhj.module.apply.ebook;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.apply.customer.Customer;
import com.asiaworld.tmuhj.core.apply.customer.CustomerService;
import com.asiaworld.tmuhj.core.dao.GenericDaoFull;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;
import com.asiaworld.tmuhj.core.util.DsBeanFactory;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnionService;

@Service
public class EbookService extends GenericServiceFull<Ebook> {

	@Autowired
	private Customer customer;

	@Autowired
	private EbookDao dao;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

	@Autowired
	private CustomerService customerService;

	@Override
	public DataSet<Ebook> getByRestrictions(DataSet<Ebook> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		Ebook entity = ds.getEntity();

		String keywords = entity.getKeywords();
		if (keywords == null || keywords.trim().equals("")) {
			Pager pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}

		String option = entity.getOption();

		if (option == null) {
			option = "";
		} else if (option.equals("書名")) {
			option = "bookname";
		} else if (option.equals("ISBN")) {
			option = "ISBN";
		} else if (option.equals("出版社")) {
			option = "publishname";
		} else if (option.equals("作者")) {
			option = "autherName";
		} else {
			option = "";
		}

		if (StringUtils.isNotEmpty(keywords)) {
			char[] cArray = keywords.toCharArray();
			StringBuilder keywordsBuilder = new StringBuilder();
			for (int i = 0; i < cArray.length; i++) {
				int charCode = (int) cArray[i];
				if (charCode > 65280 && charCode < 65375) {
					int halfChar = charCode - 65248;
					cArray[i] = (char) halfChar;
				}
				keywordsBuilder.append(cArray[i]);
			}

			keywords = keywordsBuilder.toString();

			keywords = keywords.replaceAll(
					"[^a-zA-Z0-9\u4e00-\u9fa5\u0391-\u03a9\u03b1-\u03c9]", " ");
			String[] wordArray = keywords.split(" ");

			StringBuilder sqlBuilder = new StringBuilder();
			for (int i = 0; i < wordArray.length; i++) {
				if (option.equals("ISBN")) {
					if (NumberUtils.isDigits(wordArray[i].replace("-", ""))
							&& wordArray[i].replace("-", "").length() == 13) {
						sqlBuilder.append("ISBN=" + wordArray[i] + " or ");
					}
				} else {
					if (!wordArray[i].isEmpty() && !option.isEmpty()) {
						sqlBuilder.append("LOWER(" + option + ") like LOWER('%"
								+ wordArray[i] + "%') or ");
					}
				}
			}

			String sql = sqlBuilder.toString();
			if (sql.isEmpty()) {
				Pager pager = ds.getPager();
				pager.setTotalRecord(0L);
				ds.setPager(pager);
				return ds;
			} else {
				restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
			}
		}
		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDaoFull<Ebook> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public DataSet<Ebook> getBySql(DataSet<Ebook> ds, String keywords)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		if (keywords == null || keywords.trim().equals("")) {
			Pager pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}

		if (StringUtils.isNotEmpty(keywords)) {
			char[] cArray = keywords.toCharArray();
			StringBuilder keywordsBuilder = new StringBuilder();
			for (int i = 0; i < cArray.length; i++) {
				int charCode = (int) cArray[i];
				if (charCode > 65280 && charCode < 65375) {
					int halfChar = charCode - 65248;
					cArray[i] = (char) halfChar;
				}
				keywordsBuilder.append(cArray[i]);
			}
			keywords = keywordsBuilder.toString();

			keywords = keywords.replaceAll(
					"[^a-zA-Z0-9\u4e00-\u9fa5\u0391-\u03a9\u03b1-\u03c9]", " ");
			String[] wordArray = keywords.split(" ");

			StringBuilder sqlBuilder = new StringBuilder();
			for (int i = 0; i < wordArray.length; i++) {
				if (!wordArray[i].isEmpty()) {
					sqlBuilder.append("LOWER(bookname) like LOWER('%"
							+ wordArray[i]
							+ "%') or  LOWER(publishname) like LOWER('%"
							+ wordArray[i]
							+ "%') or  LOWER(authername) like LOWER('%"
							+ wordArray[i] + "%') or ");
				}

				if (NumberUtils.isDigits(wordArray[i].replace("-", ""))
						&& wordArray[i].replace("-", "").length() == 13) {
					sqlBuilder.append("ISBN=" + wordArray[i].replace("-", "")
							+ " or ");
				}
			}

			String sql = sqlBuilder.toString();
			if (sql.isEmpty()) {
				Pager pager = ds.getPager();
				pager.setTotalRecord(0L);
				ds.setPager(pager);
				return ds;
			} else {
				restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
			}
		}

		return dao.findByRestrictions(restrictions, ds);
	}

	public DataSet<Ebook> getByCusSerNo(DataSet<Ebook> ds, long cusSerNo)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		Pager pager = ds.getPager();

		customer = customerService.getBySerNo(cusSerNo);

		List<ResourcesUnion> resourcesUnionList = null;
		if (cusSerNo > 0) {
			resourcesUnionList = resourcesUnionService.totalEbook(customer, pager);
		}

		if (resourcesUnionList != null && !resourcesUnionList.isEmpty()
				&& resourcesUnionList.size() > 0) {
			StringBuilder sqlBuilder = new StringBuilder();
			for (int i = 0; i < resourcesUnionList.size(); i++) {
				sqlBuilder.append("serNo="
						+ resourcesUnionList.get(i).getEbkSerNo() + " or ");
			}

			String sql = sqlBuilder.toString();
			restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
		} else {
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}

		List<Ebook> results = dao.findByRestrictions(restrictions);
		pager.setTotalRecord(resourcesUnionService.countTotalEbook(customer));
		ds.setResults(results);
		ds.setPager(pager);
		return ds;
	}
}