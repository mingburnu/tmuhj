package com.asiaworld.tmuhj.module.apply.database;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.asiaworld.tmuhj.core.apply.customer.Customer;
import com.asiaworld.tmuhj.core.entity.GenericEntityFull;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.ResourcesBuyers;

@Entity
@Table(name = "db")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Database extends GenericEntityFull {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1647915342720534484L;

	// 資料庫中文題名
	@Column(name = "DBchttitle")
	private String dbChtTitle;

	// 資料庫英文題名
	@Column(name = "DBengtitle")
	private String dbEngTitle;

	// 語系
	@Column(name = "languages")
	private String languages;

	// 收錄種類
	@Column(name = "IncludedSpecies")
	private String includedSpecies;

	// 出版社
	@Column(name = "publishname")
	private String publishName;

	// 內容
	@Column(name = "Content")
	@Type(type = "text")
	private String content;

	// URL
	@Column(name = "URL")
	private String url;

	// 主題
	@Column(name = "topic")
	private String topic;

	// 分類
	@Column(name = "classification")
	private String classification;

	// 收錄年代
	@Column(name = "IndexedYears")
	private String indexedYears;
	
	@Transient
	private ResourcesBuyers resourcesBuyers;
	
	@Transient
	private List<Customer> customers;
	
	@Transient
	private String existStatus;

	/**
	 * @return the dbChtTitle
	 */
	public String getDbChtTitle() {
		return dbChtTitle;
	}

	/**
	 * @param dbChtTitle
	 *            the dbChtTitle to set
	 */
	public void setDbChtTitle(String dbChtTitle) {
		this.dbChtTitle = dbChtTitle;
	}

	/**
	 * @return the dbEngTitle
	 */
	public String getDbEngTitle() {
		return dbEngTitle;
	}

	/**
	 * @param dbEngTitle
	 *            the dbEngTitle to set
	 */
	public void setDbEngTitle(String dbEngTitle) {
		this.dbEngTitle = dbEngTitle;
	}

	/**
	 * @return the languages
	 */
	public String getLanguages() {
		return languages;
	}

	/**
	 * @param languages
	 *            the languages to set
	 */
	public void setLanguages(String languages) {
		this.languages = languages;
	}

	/**
	 * @return the includedSpecies
	 */
	public String getIncludedSpecies() {
		return includedSpecies;
	}

	/**
	 * @param includedSpecies
	 *            the includedSpecies to set
	 */
	public void setIncludedSpecies(String includedSpecies) {
		this.includedSpecies = includedSpecies;
	}

	/**
	 * @return the publishName
	 */
	public String getPublishName() {
		return publishName;
	}

	/**
	 * @param publishName
	 *            the publishName to set
	 */
	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @param topic
	 *            the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * @return the classification
	 */
	public String getClassification() {
		return classification;
	}

	/**
	 * @param classification
	 *            the classification to set
	 */
	public void setClassification(String classification) {
		this.classification = classification;
	}

	/**
	 * @return the indexedYears
	 */
	public String getIndexedYears() {
		return indexedYears;
	}

	/**
	 * @param indexedYears
	 *            the indexedYears to set
	 */
	public void setIndexedYears(String indexedYears) {
		this.indexedYears = indexedYears;
	}

	/**
	 * @return the resourcesBuyers
	 */
	public ResourcesBuyers getResourcesBuyers() {
		return resourcesBuyers;
	}

	/**
	 * @param resourcesBuyers the resourcesBuyers to set
	 */
	public void setResourcesBuyers(ResourcesBuyers resourcesBuyers) {
		this.resourcesBuyers = resourcesBuyers;
	}

	/**
	 * @return the customers
	 */
	public List<Customer> getCustomers() {
		return customers;
	}

	/**
	 * @param customers the customers to set
	 */
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	/**
	 * @return the existStatus
	 */
	public String getExistStatus() {
		return existStatus;
	}

	/**
	 * @param existStatus the existStatus to set
	 */
	public void setExistStatus(String existStatus) {
		this.existStatus = existStatus;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Database [dbChtTitle=" + dbChtTitle + ", dbEngTitle="
				+ dbEngTitle + ", languages=" + languages
				+ ", includedSpecies=" + includedSpecies + ", publishName="
				+ publishName + ", content=" + content + ", url=" + url
				+ ", topic=" + topic + ", classification=" + classification
				+ ", indexedYears=" + indexedYears + ", resourcesBuyers="
				+ resourcesBuyers + ", customers=" + customers
				+ ", existStatus=" + existStatus + "]";
	}

	public Database() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Database(String dbChtTitle, String dbEngTitle, String languages,
			String includedSpecies, String publishName, String content,
			String url, String topic, String classification,
			String indexedYears, ResourcesBuyers resourcesBuyers,
			List<Customer> customers, String existStatus) {
		super();
		this.dbChtTitle = dbChtTitle;
		this.dbEngTitle = dbEngTitle;
		this.languages = languages;
		this.includedSpecies = includedSpecies;
		this.publishName = publishName;
		this.content = content;
		this.url = url;
		this.topic = topic;
		this.classification = classification;
		this.indexedYears = indexedYears;
		this.resourcesBuyers = resourcesBuyers;
		this.customers = customers;
		this.existStatus = existStatus;
	}
		
}