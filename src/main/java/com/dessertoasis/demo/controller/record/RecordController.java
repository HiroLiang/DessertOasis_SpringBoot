package com.dessertoasis.demo.controller.record;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dessertoasis.demo.model.record.Record;
import com.dessertoasis.demo.service.record.RecordService;

import jakarta.servlet.http.HttpSession;

@RestController
public class RecordController {

	@Autowired
	private RecordService rService;
	
	@GetMapping("/record/visitRecord")
	public Map<String, Integer> getVisitGroupByCategoryId(){
		Map<String, Integer> visitRecords = rService.getVisitGroupByCategoryId();
		if(visitRecords!=null)
			return visitRecords;
		return null;
	}
	
	@GetMapping("/record/targetSum")
	public Map<String, Integer> getTargetNumberGroupByCategoryId(){
		Map<String,Integer> targetCount = rService.getTargetCount();
		return targetCount;
	}

	@PutMapping("/record/set")
	public void setRecord(@RequestBody Record record, HttpSession session) {
		Integer categoryId = record.getCategoryId();
		switch (categoryId) {
		case 1: //商品
			Record prodRecord = (Record) session.getAttribute("prodRecord");
			if(prodRecord==null || record.getTargetId()!=prodRecord.getTargetId()) {
				Record setRecord = rService.setRecord(record);
				session.setAttribute("prodRecord", setRecord);
			}
			break;
		case 2: //課程
			Record courseRecord = (Record) session.getAttribute("courseRecord");
			if(courseRecord==null || record.getTargetId()!=courseRecord.getTargetId()) {
				Record setRecord = rService.setRecord(record);
				session.setAttribute("courseRecord", setRecord);
			}
			break;
		case 3: //食譜
			Record recipeRecord = (Record) session.getAttribute("recipeRecord");
			if(recipeRecord==null || record.getTargetId()!=recipeRecord.getTargetId()) {
				Record setRecord = rService.setRecord(record);
				session.setAttribute("recipeRecord", setRecord);
			}
			break;
		case 4: //教室
			Record classroomRecord = (Record) session.getAttribute("classroomRecord");
			if(classroomRecord==null || record.getTargetId()!=classroomRecord.getTargetId()) {
				Record setRecord = rService.setRecord(record);
				session.setAttribute("classroomRecord", setRecord);
			}
			break;
		default:
		}
	}
}
