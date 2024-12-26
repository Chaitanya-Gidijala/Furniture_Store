package com.foryou.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foryou.webapp.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

	List<Image> findByProductCategory(String productCategory);
	
	

}
