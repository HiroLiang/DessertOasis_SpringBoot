package com.dessertoasis.demo.service.record;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.record.Record;
import com.dessertoasis.demo.model.record.RecordRepository;

@Service
public class RecordService {

	@Autowired
	private RecordRepository rRepo;

	public Map<String, Integer> getVisitGroupByCategoryId() {
		Map<String, Integer> visitRecords = new HashMap<>();
		String[] key = {"website", "product", "course", "recipe", "classroom" };
		for (int i = 0; i < 5; i++) {
			Integer result = rRepo.countByCategoryId(i);
			if (result != null) {
				visitRecords.put(key[i], result);
			}
		}
		if(visitRecords.size()==5) {
			System.out.println(visitRecords);
			return visitRecords;
		}
		return null;
	}

	public Record setRecord(Record record) {
		Record save = rRepo.save(record);
		if (save != null)
			return save;
		return null;
	}

}
