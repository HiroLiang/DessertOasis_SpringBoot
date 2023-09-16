package com.dessertoasis.demo.model.course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dessertoasis.demo.model.product.ProductPicture;
@Repository
public interface TeacherPictureRepository extends JpaRepository<TeacherPicture, Integer> {

	List<TeacherPicture> findTeacherPictureByTeacherId(Integer teacherId);
}
