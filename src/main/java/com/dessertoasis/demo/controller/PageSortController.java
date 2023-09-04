package com.dessertoasis.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.sort.SortCondition;
import com.dessertoasis.demo.service.PageSortService;

@RestController
public class PageSortController {
	
	@Autowired
	private PageSortService pService;

	@PostMapping("/getSortedPage")
	public String getSortedPage(@RequestBody SortCondition sortCod) {
		String pageJson;
		try {
			pageJson = pService.getPageJson(sortCod);
			if(pageJson != null) {
				return pageJson;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/getSearchHint")
	public List<String> getSearchHint(@RequestParam String tableName , @RequestParam String columnName , @RequestParam String searchValue){
	
		return null;
	}
}
