package com.dessertoasis.demo.service.record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.record.Record;
import com.dessertoasis.demo.model.record.RecordRepository;

@Service
public class RecordService {
	
	@Autowired
	private RecordRepository rRepo;
	
	public Record setRecord(Record record) {
		Record save = rRepo.save(record);
		if( save!=null)
			return save;
		return null;
	}

}
