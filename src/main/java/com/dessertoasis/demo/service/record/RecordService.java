package com.dessertoasis.demo.service.record;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.course.CourseRepository;
import com.dessertoasis.demo.model.order.Order;
import com.dessertoasis.demo.model.order.OrderRepository;
import com.dessertoasis.demo.model.product.ProductRepository;
import com.dessertoasis.demo.model.recipe.RecipeRepository;
import com.dessertoasis.demo.model.record.Record;
import com.dessertoasis.demo.model.record.RecordRepository;

@Service
public class RecordService {

	@Autowired
	private RecordRepository rRepo;

	@Autowired
	private RecipeRepository reRepo;
	
	@Autowired
	private ProductRepository prodRepo;
	
	@Autowired
	private CourseRepository cRepo;
	
	@Autowired
	private OrderRepository oRepo;
	
	public Map<String, Integer> getTargetCount(){
		Map<String, Integer> targetCounter = new HashMap<>();
		Integer recipe = reRepo.countByRecipeStatus(1);
		Integer prod = (int) prodRepo.count();
		Integer course = cRepo.countByCourseStatus("啟用");
		List<Order> allOrder = oRepo.findAll();
		Integer counter = 0;
		for (Order order : allOrder) {
			counter += order.getReservations().size();
			counter += order.getProdOrderItems().size();
			counter += order.getCourseOrderItems().size();
		}
		targetCounter.put("recipe", recipe);
		targetCounter.put("product", prod);
		targetCounter.put("course", course);
		targetCounter.put("sale", counter);
		return targetCounter;
	}
	
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
