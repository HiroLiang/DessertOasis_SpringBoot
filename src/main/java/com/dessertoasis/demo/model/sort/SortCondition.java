package com.dessertoasis.demo.model.sort;

import java.util.List;

public class SortCondition {
	
	private SortWay sortWay;
	private String sortBy;
	private Integer page;
	private Integer pageSize;
	private List<String> searchBy;
	private List<List<String>> searchList;
	
	
	public enum SortWay{
		DESC,ASC
	}

	public SortCondition() {
		super();
	}

	public SortCondition(SortWay sortWay, String sortBy, Integer page, Integer pageSize, List<String> searchBy,
			List<List<String>> searchList) {
		super();
		this.sortWay = sortWay;
		this.sortBy = sortBy;
		this.page = page;
		this.pageSize = pageSize;
		this.searchBy = searchBy;
		this.searchList = searchList;
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

	public List<String> getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(List<String> searchBy) {
		this.searchBy = searchBy;
	}

	public List<List<String>> getSearchList() {
		return searchList;
	}

	public void setSearchList(List<List<String>> searchList) {
		this.searchList = searchList;
	}

}