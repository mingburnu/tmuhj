package com.asiaworld.tmuhj.module.apply.journal;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDaoFull;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;
import com.asiaworld.tmuhj.core.util.DsBeanFactory;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnionService;

@Service
public class JournalService extends GenericServiceFull<Journal> {

	@Autowired
	private JournalDao dao;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

	@Override
	public DataSet<Journal> getByRestrictions(DataSet<Journal> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		Journal entity = ds.getEntity();

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
		} else if (option.equals("中文刊名")) {
			option = "chinesetitle";
		} else if (option.equals("英文刊名")) {
			option = "englishtitle";
		} else if (option.equals("英文縮寫")) {
			option = "abbreviationtitle";
		} else if (option.equals("出版商")) {
			option = "publishname";
		} else if (option.equals("ISSN")) {
			option = "ISSN";
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
				if (option.equals("ISSN")) {
					if (NumberUtils.isDigits(wordArray[i].replace("-", "")
							.substring(0, 6))
							&& wordArray[i].replace("-", "").length() == 8) {

						if (wordArray[i].replace("-", "").substring(7)
								.equals("x")
								|| wordArray[i].replace("-", "").substring(7)
										.equals("X")
								|| NumberUtils.isDigits(wordArray[i].replace(
										"-", "").substring(7))) {
							sqlBuilder.append("ISSN='"
									+ wordArray[i].replace("-", "").replace(
											"x", "X") + "' or ");
						}
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
	protected GenericDaoFull<Journal> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public DataSet<Journal> getBySql(DataSet<Journal> ds, String keywords)
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
				if (wordArray[i].isEmpty() == false) {
					sqlBuilder.append("LOWER(chinesetitle) like LOWER('%"
							+ wordArray[i]
							+ "%') or  LOWER(englishtitle) like LOWER('%"
							+ wordArray[i]
							+ "%') or  LOWER(abbreviationtitle) like LOWER('%"
							+ wordArray[i]
							+ "%') or  LOWER(publishname) like LOWER('%"
							+ wordArray[i] + "%') or ");
				}

				if (wordArray[i].replace("-", "").length() == 8
						&& NumberUtils.isDigits(wordArray[i].replace("-", "")
								.substring(0, 6))) {
					if (wordArray[i].replace("-", "").substring(7).equals("x")
							|| wordArray[i].replace("-", "").substring(7)
									.equals("X")
							|| NumberUtils.isDigits(wordArray[i].replace("-",
									"").substring(7))) {
						sqlBuilder.append("ISSN='"
								+ wordArray[i].replace("-", "").replace("x",
										"X") + "' or ");
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

	public DataSet<Journal> getByCusSerNo(DataSet<Journal> ds, long cusSerNo)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		List<ResourcesUnion> resourcesUnionList = null;
		if (cusSerNo > 0) {
			resourcesUnionList = resourcesUnionService.totalJournal(cusSerNo);
		}

		if (resourcesUnionList != null && !resourcesUnionList.isEmpty()
				&& resourcesUnionList.size() > 0) {
			StringBuilder sqlBuilder = new StringBuilder();
			for (int i = 0; i < resourcesUnionList.size(); i++) {
				sqlBuilder.append("serNo="
						+ resourcesUnionList.get(i).getJouSerNo() + " or ");
			}

			String sql = sqlBuilder.toString();
			restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
		} else {
			Pager pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}
		return dao.findByRestrictions(restrictions, ds);
	}
}
