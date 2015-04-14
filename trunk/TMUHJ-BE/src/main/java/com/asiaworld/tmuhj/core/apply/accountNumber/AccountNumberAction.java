package com.asiaworld.tmuhj.core.apply.accountNumber;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.apply.customer.Customer;
import com.asiaworld.tmuhj.core.apply.customer.CustomerService;
import com.asiaworld.tmuhj.core.apply.enums.Role;
import com.asiaworld.tmuhj.core.apply.enums.Status;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.ExcelWorkSheet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.web.GenericCRUDActionFull;

/**
 * 使用者
 * 
 * @author Roderick
 * @version 2014/9/29
 */
@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AccountNumberAction extends GenericCRUDActionFull<AccountNumber> {

	private String[] checkItem;

	private File file;

	private String fileFileName;

	private String fileContentType;

	@Autowired
	private AccountNumber accountNumber;

	@Autowired
	private AccountNumberService accountNumberService;

	@Autowired
	private Customer customer;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private DataSet<Customer> dsCustomer;

	private ExcelWorkSheet<AccountNumber> excelWorkSheet;

	private String importSerNo;

	private String[] importSerNos;

	private InputStream inputStream;

	private String reportFile;
	
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
		List<Role> roleList= new ArrayList<Role>(Arrays.asList(Role.values()));
		List<Role> tempList= new ArrayList<Role>();
		roleList.remove(roleList.size()-1);

		int roleCode=roleList.indexOf(getLoginUser().getRole());
				
		for (int i = 0; i < roleCode; i++){
		tempList.add(roleList.get(i));
		}
		
		roleList.removeAll(tempList);
		
		getRequest().setAttribute("roleList", roleList);
		
		List<Status> statusList=new ArrayList<Status>(Arrays.asList(Status.values()));
		getRequest().setAttribute("statusList", statusList);
		
		if (getLoginUser().getRole().equals(Role.管理員)){
			customer.setSerNo(getLoginUser().getCustomer().getSerNo());

		}
		
		dsCustomer.setEntity(customer);
		dsCustomer = customerService.getByRestrictions(dsCustomer);
		
		if (getEntity().getSerNo() != null) {
			boolean isLegalRole=false;
			accountNumber = accountNumberService.getBySerNo(getEntity().getSerNo());
			if(accountNumber != null){
					if(roleList.contains(accountNumber.getRole())){
						isLegalRole=true;
						}
					}
			
			if(isLegalRole){
				if (getLoginUser().getRole().equals(Role.管理員)){
					if (accountNumber.getCustomer().equals(getLoginUser().getCustomer())){
						setEntity(accountNumber);
						} else {
							getResponse().setStatus(HttpServletResponse.SC_FORBIDDEN);
							return null;
						}
					} else {
						setEntity(accountNumber);
						}
				
			} else {
				getResponse().setStatus(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
		} else if (getRequest().getParameter("goQueue") != null
				&& getRequest().getParameter("goQueue").equals("yes")) {
			getRequest().setAttribute("goQueue",
					getRequest().getParameter("goQueue"));
			
		}

		return EDIT;
	}

	@Override
	public String list() throws Exception {
		DataSet<AccountNumber> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));
				
		ds = accountNumberService.getByRestrictions(ds, getLoginUser());

		setDs(ds);
		return LIST;
	}

	@Override
	public String save() throws Exception {
		List<Role> roleList= new ArrayList<Role>(Arrays.asList(Role.values()));
		List<Role> tempList= new ArrayList<Role>();
		roleList.remove(roleList.size()-1);

		int roleCode=roleList.indexOf(getLoginUser().getRole());
				
		for (int i = 0; i < roleCode; i++){
		tempList.add(roleList.get(i));
		}
		
		roleList.removeAll(tempList);
		
		boolean isLegalRole=false;
		for (int i=0; i < roleList.size(); i++){
			if(getRequest().getParameter("role") != null 
					&& getRequest().getParameter("role").equals(roleList.get(i).getRole())){
				isLegalRole=true;
			}
		}
		
		List<Status> statusList=new ArrayList<Status>(Arrays.asList(Status.values()));
		
		boolean isLegalStatus=false;
		for (int i=0; i < statusList.size(); i++){
			if(getRequest().getParameter("status") != null
					&& getRequest().getParameter("status").equals(statusList.get(i).getStatus())){
				isLegalStatus=true;
			}
		}
		
		if(isLegalRole){
			getEntity().setRole(Role.valueOf(getRequest().getParameter("role")));
		} else {
			addActionError("角色錯誤");
		}
		
		if(isLegalStatus){
			getEntity().setStatus(Status.valueOf(getRequest().getParameter("status")));
		} else {
			addActionError("狀態錯誤");
		}
				
		if (getEntity().getUserId() == null
				|| getEntity().getUserId().trim().equals("")) {
			addActionError("用戶代碼不得空白");
			}
			
		if (getEntity().getUserPw() == null
				|| getEntity().getUserPw().trim().equals("")) {
			addActionError("用戶密碼不得空白");
			}
			
		if (getEntity().getUserName() == null
				|| getEntity().getUserName().trim().equals("")) {
			addActionError("用戶姓名不得空白");
			}

		if (getRequest().getParameter("cusSerNo") == null
				|| !NumberUtils.isDigits(getRequest().getParameter("cusSerNo"))
				|| Long.parseLong(getRequest().getParameter("cusSerNo")) < 1
				|| customerService.getBySerNo(Long.parseLong(getRequest().getParameter("cusSerNo"))) == null){
			addActionError("用戶名稱必選");
			} else {
				getEntity().setCustomer(customerService.getBySerNo(Long.parseLong(getRequest().getParameter("cusSerNo"))));
				
				if(getLoginUser().getRole().equals(Role.管理員)){
					if (getLoginUser().getCustomer().getSerNo() != getEntity().getCustomer().getSerNo()){
						addActionError("用戶名稱不正確");
					}
				}
			}

		if (getEntity().getUserId() != null
				&& !getEntity().getUserId().trim().equals("")) {
			if (accountNumberService.userIdIsExist(getEntity())) {
				addActionError("用戶代碼已存在");
				}

			if (getEntity().getUserId().trim().equals("guest")) {
				addActionError("不可使用guest作為用戶代碼");
				}
			}

		if (!hasActionErrors()) {
			accountNumber = accountNumberService.save(getEntity(),
					getLoginUser());
			accountNumber.setCustomer(accountNumber.getCustomer());
			setEntity(accountNumber);
			return VIEW;
		} else {
			getRequest().setAttribute("roleList", roleList);
			getRequest().setAttribute("statusList", statusList);
			setEntity(getEntity());
			if (getLoginUser().getRole().equals(Role.管理員)){
				customer.setSerNo(getLoginUser().getCustomer().getSerNo());
				
			}
			
			dsCustomer.setEntity(customer);
			dsCustomer = customerService.getByRestrictions(dsCustomer);
			setEntity(getEntity());
			return EDIT;
		}
	}

	@Override
	public String update() throws Exception {
		List<Status> statusList=new ArrayList<Status>(Arrays.asList(Status.values()));
		List<Role> roleList= new ArrayList<Role>(Arrays.asList(Role.values()));
		List<Role> tempList= new ArrayList<Role>();
		roleList.remove(roleList.size()-1);

		int roleCode=roleList.indexOf(getLoginUser().getRole());
				
		for (int i = 0; i < roleCode; i++){
		tempList.add(roleList.get(i));
		}
		
		roleList.removeAll(tempList);
		
		if (!roleList.contains(accountNumberService.getBySerNo(getEntity().getSerNo()).getRole())){
			addActionError("權限不足");
		}
		
		if (!hasActionErrors()) {
		boolean isLegalRole=false;
		for (int i=0; i < roleList.size(); i++){
			if(getRequest().getParameter("role") != null 
					&& getRequest().getParameter("role").equals(roleList.get(i).getRole())){
				isLegalRole=true;
			}
		}
		
		boolean isLegalStatus=false;
		for (int i=0; i < statusList.size(); i++){
			if(getRequest().getParameter("status") != null
					&& getRequest().getParameter("status").equals(statusList.get(i).getStatus())){
				isLegalStatus=true;
			}
		}
		
		if(isLegalRole){
			getEntity().setRole(Role.valueOf(getRequest().getParameter("role")));
		} else {
			addActionError("角色錯誤");
		}
		
		if(isLegalStatus){
			getEntity().setStatus(Status.valueOf(getRequest().getParameter("status")));
		} else {
			addActionError("狀態錯誤");
		}
		
		if (getEntity().getUserName() == null
				|| getEntity().getUserName().trim().equals("")) {
			addActionError("用戶姓名不得空白");
		}

		if (getRequest().getParameter("cusSerNo") == null
				|| !NumberUtils.isDigits(getRequest().getParameter("cusSerNo"))
				|| Long.parseLong(getRequest().getParameter("cusSerNo")) < 1
				|| customerService.getBySerNo(Long.parseLong(getRequest().getParameter("cusSerNo"))) == null){
			addActionError("用戶名稱必選");
			} else {
				getEntity().setCustomer(customerService.getBySerNo(Long.parseLong(getRequest().getParameter("cusSerNo"))));
				
				if(getLoginUser().getRole().equals(Role.管理員)){
					if (getLoginUser().getCustomer().getSerNo() != getEntity().getCustomer().getSerNo()){
						addActionError("用戶名稱不正確");
					}
				}
			}
		}
		
		if (!hasActionErrors()) {
			if (getEntity().getUserPw() == null
					|| getEntity().getUserPw().trim().equals("")) {
				accountNumber = accountNumberService.update(getEntity(),
						getLoginUser(), "userId", "userPw");
			} else {
				accountNumber = accountNumberService.update(getEntity(),
						getLoginUser(), "userId");
			}

			setEntity(accountNumber);
			addActionMessage("修改成功");
			return VIEW;
		} else {
			getRequest().setAttribute("roleList", roleList);
			getRequest().setAttribute("statusList", statusList);
			getEntity().setSerNo(getEntity().getSerNo());
			getEntity().setUserId(accountNumberService.getBySerNo(getEntity().getSerNo()).getUserId());
			
			if (getLoginUser().getRole().equals(Role.管理員)){
				customer.setSerNo(getLoginUser().getCustomer().getSerNo());
				
			}
			
			dsCustomer.setEntity(customer);
			dsCustomer = customerService.getByRestrictions(dsCustomer);
			return EDIT;
		}
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String invalidChecked() throws Exception {
		if (checkItem == null || checkItem.length == 0) {
			addActionError("請選擇一筆或一筆以上的資料");
		} else {
			int i = 0;
			while (i < checkItem.length) {
				if (!NumberUtils.isDigits(String.valueOf(checkItem[i]))
						|| Long.parseLong(checkItem[i]) < 1) {
					addActionError(checkItem[i] + "為不可利用的流水號");
				}
				i++;
			}
		}
		
		if(!hasActionErrors()) {
			List<Role> roleList=new ArrayList<Role>(Arrays.asList(Role.values()));
			List<Role> tempList= new ArrayList<Role>();

			roleList.remove(roleList.size()-1);

			int roleCode=roleList.indexOf(getLoginUser().getRole());
					
			for (int i = 0; i < roleCode; i++){
			tempList.add(roleList.get(i));
			}
			
			roleList.removeAll(tempList);
			
			for(int j = 0; j < checkItem.length; j++){
				boolean isLegalRole= false;
				accountNumber=accountNumberService.getBySerNo(Long.parseLong(checkItem[j]));
				if(accountNumber != null 
						&& roleList.contains(accountNumber.getRole())){
					isLegalRole = true;
					}
				
				if(!isLegalRole){
					addActionError(checkItem[j] + "權限不可變更");
				}
				
			}
		}

		if (!hasActionErrors()) {
			int j = 0;
			while (j < checkItem.length) {
				if (accountNumberService.getBySerNo(Long
						.parseLong(checkItem[j])) != null) {
					accountNumber = accountNumberService.getBySerNo(Long.parseLong(checkItem[j]));
					accountNumber.setStatus(Status.不生效);
					accountNumberService.update(accountNumber, getLoginUser());
				}
				j++;
			}
			DataSet<AccountNumber> ds = accountNumberService
					.getByRestrictions(initDataSet());
			List<AccountNumber> results = ds.getResults();

			ds.setResults(results);

			setDs(ds);
			addActionMessage("更新成功");
			return LIST;
		} else {
			DataSet<AccountNumber> ds = accountNumberService
					.getByRestrictions(initDataSet(), getLoginUser());
			List<AccountNumber> results = ds.getResults();

			ds.setResults(results);

			setDs(ds);
			return LIST;
		}
	}

	public String validChecked() throws Exception {
		if (checkItem == null || checkItem.length == 0) {
			addActionError("請選擇一筆或一筆以上的資料");
		} else {
			int i = 0;
			while (i < checkItem.length) {
				if (!NumberUtils.isDigits(String.valueOf(checkItem[i]))
						|| Long.parseLong(checkItem[i]) < 1) {
					addActionError(checkItem[i] + "為不可利用的流水號");
				}
				i++;
			}
		}
		
		if(!hasActionErrors()) {
			List<Role> roleList=new ArrayList<Role>(Arrays.asList(Role.values()));
			List<Role> tempList= new ArrayList<Role>();

			roleList.remove(roleList.size()-1);

			int roleCode=roleList.indexOf(getLoginUser().getRole());
					
			for (int i = 0; i < roleCode; i++){
			tempList.add(roleList.get(i));
			}
			
			roleList.removeAll(tempList);
			
			for(int j = 0; j < checkItem.length; j++){
				boolean isLegalRole= false;
				accountNumber=accountNumberService.getBySerNo(Long.parseLong(checkItem[j]));
				if(accountNumber != null 
						&& roleList.contains(accountNumber.getRole())){
					isLegalRole = true;
					}
				
				if(!isLegalRole){
					addActionError(checkItem[j] + "權限不可變更");
				}
				
			}
		}

		if (!hasActionErrors()) {
			int j = 0;
			while (j < checkItem.length) {
				if (accountNumberService.getBySerNo(Long
						.parseLong(checkItem[j])) != null) {
					accountNumber = accountNumberService.getBySerNo(Long
							.parseLong(checkItem[j]));
					accountNumber.setStatus(Status.生效);
					accountNumberService.update(accountNumber, getLoginUser());
				}
				j++;
			}
			DataSet<AccountNumber> ds = initDataSet();
			ds.setPager(Pager.getChangedPager(
					getRequest().getParameter("recordPerPage"), getRequest()
							.getParameter("recordPoint"), ds.getPager()));
			ds = accountNumberService.getByRestrictions(ds);
			List<AccountNumber> results = ds.getResults();

			ds.setResults(results);

			setDs(ds);
			addActionMessage("更新成功");
			return LIST;
		} else {
			DataSet<AccountNumber> ds = accountNumberService
					.getByRestrictions(initDataSet(), getLoginUser());
			List<AccountNumber> results = ds.getResults();

			ds.setResults(results);

			setDs(ds);
			return LIST;
		}
	}

	public String view() throws Exception {
		getRequest().setAttribute("viewSerNo", getRequest().getParameter("viewSerNo"));	
		
		if (getRequest().getParameter("viewSerNo") != null
				&& NumberUtils.isDigits(getRequest().getParameter("viewSerNo"))){
		
			accountNumber = accountNumberService.getBySerNo(Long.parseLong(getRequest().getParameter("viewSerNo")));
			if (accountNumber != null){
				setEntity(accountNumber);
				}
			}
		return VIEW;
		}

	public String queue() throws Exception {
		if (file == null || !file.isFile()) {
			addActionError("請選擇檔案");
		} else {
			if (createWorkBook(new FileInputStream(file)) == null) {
				addActionError("檔案格式錯誤");
			}
		}

		if (!hasActionErrors()) {
			Workbook book = createWorkBook(new FileInputStream(file));
			// book.getNumberOfSheets(); 判斷Excel文件有多少個sheet
			Sheet sheet = book.getSheetAt(0);
			excelWorkSheet = new ExcelWorkSheet<AccountNumber>();

			// 保存工作單名稱
			Row firstRow = sheet.getRow(0);

			// 保存列名
			List<String> cellNames = new ArrayList<String>();
			String[] rowTitles = new String[6];
			int n = 0;
			while (n < rowTitles.length) {
				if (firstRow.getCell(n) == null) {
					rowTitles[n] = "";
				} else {
					int typeInt = firstRow.getCell(n).getCellType();
					switch (typeInt) {
					case 0:
						String tempNumeric = "";
						tempNumeric = tempNumeric
								+ firstRow.getCell(n).getNumericCellValue();
						rowTitles[n] = tempNumeric;
						break;

					case 1:
						rowTitles[n] = firstRow.getCell(n).getStringCellValue()
								.trim();
						break;

					case 2:
						rowTitles[n] = firstRow.getCell(n).getCellFormula()
								.trim();
						break;

					case 3:
						rowTitles[n] = "";
						break;

					case 4:
						String tempBoolean = "";
						tempBoolean = ""
								+ firstRow.getCell(n).getBooleanCellValue();
						rowTitles[n] = tempBoolean;
						break;

					case 5:
						String tempByte = "";
						tempByte = tempByte
								+ firstRow.getCell(n).getErrorCellValue();
						rowTitles[n] = tempByte;
						break;
					}

				}

				cellNames.add(rowTitles[n]);
				n++;
			}

			LinkedHashSet<AccountNumber> originalData = new LinkedHashSet<AccountNumber>();
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);

				String[] rowValues = new String[6];
				int k = 0;
				while (k < rowValues.length) {
					if (row.getCell(k) == null) {
						rowValues[k] = "";
					} else {
						int typeInt = row.getCell(k).getCellType();
						switch (typeInt) {
						case 0:
							String tempNumeric = "";
							tempNumeric = tempNumeric
									+ row.getCell(k).getNumericCellValue();
							rowValues[k] = tempNumeric;
							break;

						case 1:
							rowValues[k] = row.getCell(k).getStringCellValue()
									.trim();
							break;

						case 2:
							rowValues[k] = row.getCell(k).getCellFormula()
									.trim();
							break;

						case 3:
							rowValues[k] = "";
							break;

						case 4:
							String tempBoolean = "";
							tempBoolean = ""
									+ row.getCell(k).getBooleanCellValue();
							rowValues[k] = tempBoolean;
							break;

						case 5:
							String tempByte = "";
							tempByte = tempByte
									+ row.getCell(k).getErrorCellValue();
							rowValues[k] = tempByte;
							break;
						}

					}
					k++;
				}

				String role = "";
				List<Role> roleList=new ArrayList<Role>(Arrays.asList(Role.values()));
				List<Role> tempList= new ArrayList<Role>();
				for (int j=0; j < roleList.size(); j++){
					if (rowValues[4].trim().equals(roleList.get(j).getRole())){
						role = roleList.get(j).getRole();
					}
				}

				if (role.equals("")){
					role = Role.不明.getRole();
				}
				
				int roleCode=roleList.indexOf(getLoginUser().getRole());
				
				for (int j = 0; j < roleCode; j++){
				tempList.add(roleList.get(j));
				}
				
				roleList.removeAll(tempList);
				
				boolean isLegalRole=false;
				roleList.remove(roleList.size()-1);
				for (int j=0; j < roleList.size(); j++){
					if(role.equals(roleList.get(j).getRole())){
						isLegalRole=true;
					}
				}

				List<Status> statusList=new ArrayList<Status>(Arrays.asList(Status.values()));
				String status="";
				for (int j=0; j < statusList.size(); j++){
					if(rowValues[5].trim().equals(statusList.get(j).getStatus())){
						status = statusList.get(j).getStatus();
					}
				}
				
				if(status.equals("")){
					status = Status.審核中.getStatus();
				}
				
				accountNumber = new AccountNumber(
						
						rowValues[0].trim(), rowValues[1].trim(),
						rowValues[2].trim(), Role.valueOf(role), Status.valueOf(status),
						null, "");

				if (customerService.getCusSerNoByName(rowValues[3].trim()) != 0){
					customer = customerService.getBySerNo(customerService.getCusSerNoByName(rowValues[3].trim()));
					accountNumber.setCustomer(customer);
				}
				
				long serNo = accountNumberService
						.getSerNoByUserId(accountNumber.getUserId());
				if (serNo != 0) {
					accountNumber.setExistStatus("已存在");
				} else {						
					if(getLoginUser().getRole().equals(Role.管理員)){
						if (accountNumber.getUserId().isEmpty()
								|| accountNumber.getUserPw().isEmpty()
								|| accountNumber.getCustomer() == null
								|| !isLegalRole
								|| !getLoginUser().getCustomer().equals(accountNumber.getCustomer())) {
							customer = new Customer();
							customer.setName(rowValues[3].trim());
							accountNumber.setCustomer(customer);
							accountNumber.setExistStatus("資料錯誤");
							} else {
								customer = new Customer();
								customer.setName(rowValues[3].trim());
								accountNumber.setCustomer(customer);
								accountNumber.setExistStatus("正常");			
								
							}
					} else {
						if (accountNumber.getUserId().isEmpty()
								|| accountNumber.getUserPw().isEmpty()
								|| accountNumber.getCustomer() == null
								|| !isLegalRole) {
							customer = new Customer();
							customer.setName(rowValues[3].trim());
							accountNumber.setCustomer(customer);
							accountNumber.setExistStatus("資料錯誤");
							} else {
								customer = new Customer();
								customer.setName(rowValues[3].trim());
								accountNumber.setCustomer(customer);
								accountNumber.setExistStatus("正常");			
								
							}
						}
					}

				originalData.add(accountNumber);

			}

			Iterator<AccountNumber> setIterator = originalData.iterator();
			
			int normal = 0;
			while (setIterator.hasNext()) {
				accountNumber = setIterator.next();
				excelWorkSheet.getData().add(accountNumber);
				if (accountNumber.getExistStatus().equals("正常")) {
					normal = normal + 1;
				}
			}
			
			DataSet<AccountNumber> ds = initDataSet();
			List<AccountNumber> results=ds.getResults();
			
			ds.getPager().setTotalRecord((long)excelWorkSheet.getData().size());
			ds.getPager().setRecordPoint(0);
			
			if(excelWorkSheet.getData().size() < ds.getPager().getRecordPerPage()){
				int i=0;
				while(i < excelWorkSheet.getData().size()){
					results.add(excelWorkSheet.getData().get(i));
					i++;
				}
			} else {
				int i=0;
				while(i < ds.getPager().getRecordPerPage()){
					results.add(excelWorkSheet.getData().get(i));
					i++;
				}
			}

			getSession().put("cellNames", cellNames);
			getSession().put("importList", excelWorkSheet.getData());
			getSession().put("total", excelWorkSheet.getData().size());
			getSession().put("normal", normal);
			getSession().put("abnormal",
					excelWorkSheet.getData().size() - normal);
			
			setDs(ds);
			return QUEUE;
		} else {
			getRequest().setAttribute("goQueue", "yes");
			return EDIT;
		}
	}
	
	@SuppressWarnings("unchecked")
	public String paginate() throws Exception {
		clearCheckedItem();
		
		List<AccountNumber> importList = (List<AccountNumber>) getSession().get("importList");
		
		DataSet<AccountNumber> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));

		if (importList == null){
			return null;
		} else {
			ds.getPager().setTotalRecord((long) importList.size());
			
		}
		
		int first = ds.getPager().getRecordPerPage()
				* (ds.getPager().getCurrentPage() - 1);
		int last = first + ds.getPager().getRecordPerPage();

		List<AccountNumber> results = new ArrayList<AccountNumber>();

		int i = 0;
		while (i < importList.size()) {
			if (i >= first && i < last) {
				results.add(importList.get(i));
			}
			i++;
		}

		ds.setResults(results);
		setDs(ds);
		return QUEUE;
	}

	@SuppressWarnings("unchecked")
	public void getCheckedItem() {
		Map<String, Object> checkItemMap;
		if (getSession().containsKey("checkItemMap")) {
			checkItemMap = (TreeMap<String, Object>) getSession().get(
					"checkItemMap");
		} else {
			checkItemMap = new TreeMap<String, Object>();
		}

		if (!checkItemMap.containsKey(this.importSerNo)) {
			checkItemMap.put(this.importSerNo, this.importSerNo);
		} else {
			checkItemMap.remove(this.importSerNo);
		}
		getSession().put("checkItemMap", checkItemMap);
	}

	public void allCheckedItem() {
		Map<String, Object> checkItemMap = new TreeMap<String, Object>();

		int i = 0;
		while (i < importSerNos.length) {
			checkItemMap.put(importSerNos[i], importSerNos[i]);
			i++;
		}

		getSession().put("checkItemMap", checkItemMap);
	}

	public void clearCheckedItem() {
		Map<String, Object> checkItemMap = new TreeMap<String, Object>();
		getSession().put("checkItemMap", checkItemMap);
	}

	@SuppressWarnings("unchecked")
	public String importData() throws Exception {
		List<AccountNumber> importList = (List<AccountNumber>) getSession()
				.get("importList");

		Map<String, Object> checkItemMap = (TreeMap<String, Object>) getSession()
				.get("checkItemMap");

		if (checkItemMap == null || checkItemMap.size() == 0) {
			addActionError("請選擇一筆或一筆以上的資料");
		}

		if (!hasActionErrors()) {
			Iterator<?> it = checkItemMap.values().iterator();
			List<AccountNumber> importIndexs = new ArrayList<AccountNumber>();
			while (it.hasNext()) {
				String index = it.next().toString();
				
				if(NumberUtils.isDigits(index)){
					if(Integer.parseInt(index) >=0 && Integer.parseInt(index) < importList.size()){
						importIndexs.add(importList.get(Integer.parseInt(index)));
				}
					}
			}

			for (int i = 0; i < importIndexs.size(); i++) {
				
				if (importIndexs.get(i).getExistStatus().equals("正常")) {
				accountNumberService.save(importIndexs.get(i), getLoginUser());
				}
			}

			getRequest().setAttribute("successCount", importIndexs.size());
			return VIEW;
		} else {
			paginate();
			return QUEUE;
		}
	}
	
	public String exports() throws Exception {
		reportFile = "account_sample.xlsx";

		// Create blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet("account");
		// Create row object
		XSSFRow row;
		// This data needs to be written (Object[])
		Map<String, Object[]> empinfo = new LinkedHashMap<String, Object[]>();
		empinfo.put("1", new Object[] { "userID/使用者", "userPW/使用者密碼",
				"userName/姓名", "fk_name/用戶名稱", "role/角色", "status/狀態" });

		empinfo.put("2", new Object[] { "cdc", "cdc", "XXX","疾病管制局", "管理員",
				 "生效" });

		// Iterate over data and write to sheet
		Set<String> keyid = empinfo.keySet();
		int rowid = 0;
		for (String key : keyid) {
			row = spreadsheet.createRow(rowid++);
			Object[] objectArr = empinfo.get(key);
			int cellid = 0;
			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}

		ByteArrayOutputStream boas = new ByteArrayOutputStream();
		workbook.write(boas);
		setInputStream(new ByteArrayInputStream(boas.toByteArray()));

		return SUCCESS;
	}

	// 判斷文件類型
	public Workbook createWorkBook(InputStream is) throws IOException {
		if (fileFileName.toLowerCase().endsWith("xls")) {
			return new HSSFWorkbook(is);
		}
		if (fileFileName.toLowerCase().endsWith("xlsx")) {
			return new XSSFWorkbook(is);
		}
		return null;
	}

	/**
	 * @return the dsCustomer
	 */
	public DataSet<Customer> getDsCustomer() {
		return dsCustomer;
	}

	/**
	 * @param dsCustomer
	 *            the dsCustomer to set
	 */
	public void setDsCustomer(DataSet<Customer> dsCustomer) {
		this.dsCustomer = dsCustomer;
	}

	/**
	 * @return the checkItem
	 */
	public String[] getCheckItem() {
		return checkItem;
	}

	/**
	 * @param checkItem
	 *            the checkItem to set
	 */
	public void setCheckItem(String[] checkItem) {
		this.checkItem = checkItem;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the fileFileName
	 */
	public String getFileFileName() {
		return fileFileName;
	}

	/**
	 * @param fileFileName
	 *            the fileFileName to set
	 */
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	/**
	 * @return the fileContentType
	 */
	public String getFileContentType() {
		return fileContentType;
	}

	/**
	 * @param fileContentType
	 *            the fileContentType to set
	 */
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/**
	 * @return the excelWorkSheet
	 */
	public ExcelWorkSheet<AccountNumber> getExcelWorkSheet() {
		return excelWorkSheet;
	}

	/**
	 * @param excelWorkSheet
	 *            the excelWorkSheet to set
	 */
	public void setExcelWorkSheet(ExcelWorkSheet<AccountNumber> excelWorkSheet) {
		this.excelWorkSheet = excelWorkSheet;
	}

	/**
	 * @return the importSerNo
	 */
	public String getImportSerNo() {
		return importSerNo;
	}

	/**
	 * @param importSerNo
	 *            the importSerNo to set
	 */
	public void setImportSerNo(String importSerNo) {
		this.importSerNo = importSerNo;
	}

	/**
	 * @return the importSerNos
	 */
	public String[] getImportSerNos() {
		return importSerNos;
	}

	/**
	 * @param importSerNos
	 *            the importSerNos to set
	 */
	public void setImportSerNos(String[] importSerNos) {
		this.importSerNos = importSerNos;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream
	 *            the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @return the reportFile
	 */
	public String getReportFile() {
		return reportFile;
	}

	/**
	 * @param reportFile
	 *            the reportFile to set
	 */
	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}
}