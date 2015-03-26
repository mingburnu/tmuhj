package com.asiaworld.tmuhj.core.apply.feLogs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.asiaworld.tmuhj.core.apply.enums.Act;
import com.asiaworld.tmuhj.core.entity.GenericEntityLog;

/**
 * FE_Logs
 * 
 * @author Roderick
 * @version 2015/01/26
 */
@Entity
@Table(name = "FE_Logs")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FeLogs extends GenericEntityLog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1299854351303327443L;

	// 類型
	@Column(name = "actionType")
	@Enumerated(EnumType.STRING)
	private Act actionType;

	// 關鍵字
	@Column(name = "keyword")
	private String keyword;

	// 用戶流水號
	@Column(name = "cus_SerNo")
	private Long cusSerNo;

	// 帳戶流水號
	@Column(name = "acc_SerNo")
	private Long accSerNo;

	// 資料庫流水號
	@Column(name = "dat_SerNo")
	private Long datSerNo;

	// 電子書流水號
	@Column(name = "ebk_SerNo")
	private Long ebkSerNo;

	// 期刊流水號
	@Column(name = "jou_SerNo")
	private Long jouSerNo;

	/**
	 * @return the actionType
	 */
	public Act getActionType() {
		return actionType;
	}

	/**
	 * @param actionType
	 *            the actionType to set
	 */
	public void setActionType(Act actionType) {
		this.actionType = actionType;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword
	 *            the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the cusSerNo
	 */
	public Long getCusSerNo() {
		return cusSerNo;
	}

	/**
	 * @param cusSerNo
	 *            the cusSerNo to set
	 */
	public void setCusSerNo(Long cusSerNo) {
		this.cusSerNo = cusSerNo;
	}

	/**
	 * @return the accSerNo
	 */
	public Long getAccSerNo() {
		return accSerNo;
	}

	/**
	 * @param accSerNo
	 *            the accSerNo to set
	 */
	public void setAccSerNo(Long accSerNo) {
		this.accSerNo = accSerNo;
	}

	/**
	 * @return the datSerNo
	 */
	public Long getDatSerNo() {
		return datSerNo;
	}

	/**
	 * @param datSerNo
	 *            the datSerNo to set
	 */
	public void setDatSerNo(Long datSerNo) {
		this.datSerNo = datSerNo;
	}

	/**
	 * @return the ebkSerNo
	 */
	public Long getEbkSerNo() {
		return ebkSerNo;
	}

	/**
	 * @param ebkSerNo
	 *            the ebkSerNo to set
	 */
	public void setEbkSerNo(Long ebkSerNo) {
		this.ebkSerNo = ebkSerNo;
	}

	/**
	 * @return the jouSerNo
	 */
	public Long getJouSerNo() {
		return jouSerNo;
	}

	/**
	 * @param jouSerNo
	 *            the jouSerNo to set
	 */
	public void setJouSerNo(Long jouSerNo) {
		this.jouSerNo = jouSerNo;
	}

	public FeLogs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FeLogs(Act actionType, String keyword, Long cusSerNo, Long accSerNo,
			Long datSerNo, Long ebkSerNo, Long jouSerNo) {
		super();
		this.actionType = actionType;
		this.keyword = keyword;
		this.cusSerNo = cusSerNo;
		this.accSerNo = accSerNo;
		this.datSerNo = datSerNo;
		this.ebkSerNo = ebkSerNo;
		this.jouSerNo = jouSerNo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FeLogs [actionType=" + actionType + ", keyword=" + keyword
				+ ", cusSerNo=" + cusSerNo + ", accSerNo=" + accSerNo
				+ ", datSerNo=" + datSerNo + ", ebkSerNo=" + ebkSerNo
				+ ", jouSerNo=" + jouSerNo + "]";
	}

}
