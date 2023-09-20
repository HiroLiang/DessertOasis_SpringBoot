package com.dessertoasis.demo.controller.record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;

import com.dessertoasis.demo.model.record.Record;
import com.dessertoasis.demo.service.record.RecordService;

import jakarta.servlet.http.HttpSession;

@Controller
public class RecordController {

	@Autowired
	private RecordService rService;

	@PutMapping("/record/set")
	public void setRecord(Record record, HttpSession session) {
		Integer categoryId = record.getCategoryId();
		switch (categoryId) {
		case 1:

			Record setRecord = rService.setRecord(record);
			if (setRecord != null) {

			}
		}
	}
}
