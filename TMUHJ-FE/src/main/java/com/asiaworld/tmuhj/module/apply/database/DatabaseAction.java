package com.asiaworld.tmuhj.module.apply.database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.apply.customer.Customer;
import com.asiaworld.tmuhj.core.apply.customer.CustomerService;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.web.GenericCRUDActionFull;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.ResourcesBuyers;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.ResourcesBuyersService;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnionService;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DatabaseAction extends GenericCRUDActionFull<Database> {

	@Autowired
	private Database database;

	@Autowired
	private DatabaseService databaseService;

	@Autowired
	private ResourcesUnion resourcesUnion;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

	@Autowired
	private ResourcesBuyers resourcesBuyers;

	@Autowired
	private ResourcesBuyersService resourcesBuyersService;

	@Autowired
	private Customer customer;

	@Autowired
	private CustomerService customerService;

	@Override
	public void validateSave() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void validateUpdate() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void validateDelete() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String query() throws Exception {
		String keywords = getRequest().getParameter("keywords");

		getRequest().setAttribute("keywords", keywords);
		getRequest().setAttribute("query", "apply.database.query.action");

		DataSet<Database> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));

		ds = databaseService.getBySql(ds, keywords);
		setDs(ds);
		return "database";
	}

	@Override
	public String list() throws Exception {
		database = databaseService.getBySerNo(Long.parseLong(getRequest()
				.getParameter("serNo")));

		resourcesUnion = resourcesUnionService.getByObjSerNo(
				Long.parseLong(getRequest().getParameter("serNo")),
				database.getClass());

		resourcesBuyers = resourcesBuyersService.getBySerNo(resourcesUnion
				.getResSerNo());

		List<?> dbResourcesUnionList = resourcesUnionService.getByDatSerNo(Long
				.parseLong(getRequest().getParameter("serNo")));

		List<String> ownerNameList = new ArrayList<String>();

		Iterator<?> iterator = dbResourcesUnionList.iterator();

		while (iterator.hasNext()) {
			ResourcesUnion datResourcesUnion = (ResourcesUnion) iterator.next();
			customer = customerService.getBySerNo(datResourcesUnion
					.getCusSerNo());
			if(customer!=null){
			ownerNameList.add(customer.getName());
			}
		}

		String ownerNames = ownerNameList.toString().replace("[", "")
				.replace("]", "");

		getRequest().setAttribute("database", database);
		getRequest().setAttribute("resourcesBuyers", resourcesBuyers);
		getRequest().setAttribute("ownerNames", ownerNames);
		getRequest().setAttribute("backURL", getRequest().getParameter("currentURL").replace("@@@", "?").replace("^^^", "&"));
		return "d-detail";
	}

	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String owner() throws Exception {
		long cusSerNo = 0;
		if (NumberUtils.isDigits(getRequest().getParameter("cusSerNo"))
				&& Long.parseLong(getRequest().getParameter("cusSerNo")) > 0) {
			cusSerNo = Long.parseLong(getRequest().getParameter("cusSerNo"));
		}

		getRequest().setAttribute("cusSerNo", cusSerNo);
		getRequest().setAttribute("owner", "apply.database.owner.action");

		DataSet<Database> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));
		ds = databaseService.getByCusSerNo(ds, cusSerNo);
		setDs(ds);

		return "database";
	}

	public String focus() throws Exception {
		String option = getRequest().getParameter("option");
		String keywords = getRequest().getParameter("keywords");

		getRequest().setAttribute("keywords", keywords);
		getRequest().setAttribute("option", option);
		getRequest().setAttribute("focus", "apply.database.focus.action");

		getEntity().setOption(option);
		getEntity().setKeywords(keywords);
		
		DataSet<Database> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));
		ds = databaseService.getByRestrictions(ds);
		setDs(ds);
		return "database";
	}
}