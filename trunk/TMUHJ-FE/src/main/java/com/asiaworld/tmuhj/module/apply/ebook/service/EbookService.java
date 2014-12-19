package com.asiaworld.tmuhj.module.apply.ebook.service;

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
import com.asiaworld.tmuhj.module.apply.ebook.entity.Ebook;
import com.asiaworld.tmuhj.module.apply.ebook.entity.EbookDao;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.entity.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.service.ResourcesUnionService;

@Service
public class EbookService extends GenericServiceFull<Ebook> {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private EbookDao dao;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

	@Override
	public DataSet<Ebook> getByRestrictions(DataSet<Ebook> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		HttpServletRequest request = ServletActionContext.getRequest();
		String keywords = request.getParameter("keywords");
		if (keywords == null || keywords.trim().equals("")) {
			Pager pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}

		String option = request.getParameter("option");

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

		String recordPerPage = request.getParameter("recordPerPage");
		String recordPoint = request.getParameter("recordPoint");

		Pager pager = ds.getPager();

		if (recordPerPage != null && NumberUtils.isDigits(recordPerPage)
				&& Integer.parseInt(recordPerPage) > 0 && recordPoint != null
				&& NumberUtils.isDigits(recordPoint)
				&& Integer.parseInt(recordPoint) >= 0) {
			pager.setRecordPerPage(Integer.parseInt(recordPerPage));
			pager.setCurrentPage(Integer.parseInt(recordPoint)
					/ Integer.parseInt(recordPerPage) + 1);
			pager.setOffset(Integer.parseInt(recordPerPage)
					* (pager.getCurrentPage() - 1));
			pager.setRecordPoint(Integer.parseInt(recordPoint));
			ds.setPager(pager);
		} else if (recordPerPage != null && NumberUtils.isDigits(recordPerPage)
				&& Integer.parseInt(recordPerPage) > 0 && recordPoint == null) {
			pager.setRecordPerPage(Integer.parseInt(recordPerPage));
			pager.setRecordPoint(pager.getOffset());
			ds.setPager(pager);
		} else {
			pager.setRecordPoint(pager.getOffset());
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

			keywords = keywords
					.replaceAll(
							"[^a-zA-Z0-9\u4e00-\u9fa5\u0391-\u03a9\u03b1-\u03c9\u002d]",
							" ");
			String[] wordArray = keywords.split(" ");
			String sql = "";

			for (int i = 0; i < wordArray.length; i++) {
				if (option.equals("ISBN")) {
					if (NumberUtils.isDigits(wordArray[i].replace("-", ""))
							&& wordArray[i].replace("-", "").length() == 13) {
						sql = sql + "ISBN=" + wordArray[i] + " or ";
					}
				} else {
					if (!wordArray[i].isEmpty() && !option.isEmpty()) {
						sql = sql + "LOWER(" + option + ") like LOWER('%"
								+ wordArray[i] + "%') or ";
					}
				}
			}

			if (sql.isEmpty()) {
				pager = ds.getPager();
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

	public DataSet<Ebook> getBySql(DataSet<Ebook> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		HttpServletRequest request = ServletActionContext.getRequest();

		String keywords = request.getParameter("keywords");
		if (keywords == null || keywords.trim().equals("")) {
			Pager pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}

		String recordPerPage = request.getParameter("recordPerPage");
		String recordPoint = request.getParameter("recordPoint");

		Pager pager = ds.getPager();

		if (recordPerPage != null && NumberUtils.isDigits(recordPerPage)
				&& Integer.parseInt(recordPerPage) > 0 && recordPoint != null
				&& NumberUtils.isDigits(recordPoint)
				&& Integer.parseInt(recordPoint) >= 0) {
			pager.setRecordPerPage(Integer.parseInt(recordPerPage));
			pager.setCurrentPage(Integer.parseInt(recordPoint)
					/ Integer.parseInt(recordPerPage) + 1);
			pager.setOffset(Integer.parseInt(recordPerPage)
					* (pager.getCurrentPage() - 1));
			pager.setRecordPoint(Integer.parseInt(recordPoint));
			ds.setPager(pager);
		} else if (recordPerPage != null && NumberUtils.isDigits(recordPerPage)
				&& Integer.parseInt(recordPerPage) > 0 && recordPoint == null) {
			pager.setRecordPerPage(Integer.parseInt(recordPerPage));
			pager.setRecordPoint(pager.getOffset());
			ds.setPager(pager);
		} else {
			pager.setRecordPoint(pager.getOffset());
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

			keywords = keywords
					.replaceAll(
							"[^a-zA-Z0-9\u4e00-\u9fa5\u0391-\u03a9\u03b1-\u03c9\u002d]",
							" ");
			String[] wordArray = keywords.split(" ");
			String sql = "";

			for (int i = 0; i < wordArray.length; i++) {
				if (!wordArray[i].isEmpty()) {
					sql = sql + "LOWER(bookname) like LOWER('%" + wordArray[i]
							+ "%') or  LOWER(publishname) like LOWER('%"
							+ wordArray[i]
							+ "%') or  LOWER(authername) like LOWER('%"
							+ wordArray[i] + "%') or ";
				}

				if (NumberUtils.isDigits(wordArray[i].replace("-", ""))
						&& wordArray[i].replace("-", "").length() == 13) {
					sql = sql + "ISBN=" + wordArray[i].replace("-", "")
							+ " or ";
				}
			}

			if (sql.isEmpty()) {
				pager = ds.getPager();
				pager.setTotalRecord(0L);
				ds.setPager(pager);
				return ds;
			} else {
				restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
			}
		}

		return dao.findByRestrictions(restrictions, ds);
	}

	public DataSet<Ebook> getByCusSerNo(DataSet<Ebook> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		HttpServletRequest request = ServletActionContext.getRequest();
		String cusSerNo = request.getParameter("cusSerNo");

		String recordPerPage = request.getParameter("recordPerPage");
		String recordPoint = request.getParameter("recordPoint");

		Pager pager = ds.getPager();

		if (recordPerPage != null && NumberUtils.isDigits(recordPerPage)
				&& Integer.parseInt(recordPerPage) > 0 && recordPoint != null
				&& NumberUtils.isDigits(recordPoint)
				&& Integer.parseInt(recordPoint) >= 0) {
			pager.setRecordPerPage(Integer.parseInt(recordPerPage));
			pager.setCurrentPage(Integer.parseInt(recordPoint)
					/ Integer.parseInt(recordPerPage) + 1);
			pager.setOffset(Integer.parseInt(recordPerPage)
					* (pager.getCurrentPage() - 1));
			pager.setRecordPoint(Integer.parseInt(recordPoint));
			ds.setPager(pager);
		} else if (recordPerPage != null && NumberUtils.isDigits(recordPerPage)
				&& Integer.parseInt(recordPerPage) > 0 && recordPoint == null) {
			pager.setRecordPerPage(Integer.parseInt(recordPerPage));
			pager.setRecordPoint(pager.getOffset());
			ds.setPager(pager);
		} else {
			pager.setRecordPoint(pager.getOffset());
			ds.setPager(pager);
		}

		ArrayList<ResourcesUnion> resourcesUnionList = null;
		if (NumberUtils.isDigits(cusSerNo)) {
			resourcesUnionList = resourcesUnionService.totalEbook(Long
					.parseLong(cusSerNo));
		}

		String sql = "";
		if (resourcesUnionList != null && !resourcesUnionList.isEmpty()
				&& resourcesUnionList.size() > 0) {
			for (int i = 0; i < resourcesUnionList.size(); i++) {
				sql = sql + "serNo=" + resourcesUnionList.get(i).getEbkSerNo()
						+ " or ";
			}

			restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
		} else {
			pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}
		return dao.findByRestrictions(restrictions, ds);
	}
}
