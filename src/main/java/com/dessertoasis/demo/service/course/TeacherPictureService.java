package com.dessertoasis.demo.service.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dessertoasis.demo.model.course.TeacherPicture;
import com.dessertoasis.demo.model.course.TeacherPictureRepository;
import com.dessertoasis.demo.model.product.ProductPicture;
import com.dessertoasis.demo.model.product.ProductPictureRepository;
@Service
public class TeacherPictureService {

	@Autowired
    private TeacherPictureRepository tpRepo;

    public List<TeacherPicture> findTeacherPicturesByTeacherId(Integer teacherId){
    	return tpRepo.findTeacherPictureByTeacherId(teacherId);
    }
	public TeacherPictureService() {
		// TODO Auto-generated constructor stub
	}

}
