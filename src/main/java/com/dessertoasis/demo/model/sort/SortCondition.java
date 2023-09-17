package com.dessertoasis.demo.model.sort;

import java.util.List;

import lombok.Data;

@Data
public class SortCondition {
	/*
	 * 		物件結構： 
	 * 		{
	 * 			"sortWay":"ASC",
	 * 			"sortBy":"fullName", 
	 * 			"page":1, "pageSize":10,
	 * 			"dateRules":[ 
	 * 				{
	 * 					"key":"date", 
	 * 					"type":"Date", 
	 * 					"start":"2020-02-02T11:11:11",
	 * 					"end":"2021-12-12T11:11:11" 
	 * 				}
	 * 			],
	 * 			"searchRules":[ 
	 * 				{ 
	 * 					"key":"fullName",
	 * 					"type":"String", 
	 * 					"input":"大中天" 
	 * 				},
	 * 				{ 
	 * 					"key":"teacherMail", 
	 * 					"type":"String",
	 * 					"input":"qqq@qqq.com" 
	 * 				} 
	 * 			], 
	 * 			"numKey":"teacherTel", 
	 * 			"numStart":8, 
	 * 			"numEnd":12 
	 * 			"numberLabel":"price"
	 * 		}
	 * 
	 * 		兩個List的數量可以自由增加
	 */
	private SortWay sortWay;
	private String sortBy;
	private Integer page;
	private Integer pageSize;
	private List<DateRules> dateRules;
	private List<SearchRules> searchRules;
	private String numKey;
	private Integer numStart;
	private Integer numEnd;
	private String numberLabel;

	public enum SortWay {
		DESC, ASC
	}

	public SortCondition() {
		super();
	}

	public SortCondition(SortWay sortWay, String sortBy, Integer page, Integer pageSize, List<DateRules> dateRules,
			List<SearchRules> searchRules, String numKey, Integer numStart, Integer numEnd) {
		super();
		this.sortWay = sortWay;
		this.sortBy = sortBy;
		this.page = page;
		this.pageSize = pageSize;
		this.dateRules = dateRules;
		this.searchRules = searchRules;
		this.numKey = numKey;
		this.numStart = numStart;
		this.numEnd = numEnd;
	}

	public String getNumberLabel() {
		return numberLabel;
	}

	public void setNumberLabel(String numberLabel) {
		this.numberLabel = numberLabel;
	}

	public String getNumKey() {
		return numKey;
	}

	public void setNumKey(String numKey) {
		this.numKey = numKey;
	}

	public Integer getNumStart() {
		return numStart;
	}

	public void setNumStart(Integer numStart) {
		this.numStart = numStart;
	}

	public Integer getNumEnd() {
		return numEnd;
	}

	public void setNumEnd(Integer numEnd) {
		this.numEnd = numEnd;
	}

	public SortWay getSortWay() {
		return sortWay;
	}

	public void setSortWay(SortWay sortWay) {
		this.sortWay = sortWay;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<DateRules> getDateRules() {
		return dateRules;
	}

	public void setDateRules(List<DateRules> dateRules) {
		this.dateRules = dateRules;
	}

	public List<SearchRules> getSearchRules() {
		return searchRules;
	}

	public void setSearchRules(List<SearchRules> searchRules) {
		this.searchRules = searchRules;
	}

}