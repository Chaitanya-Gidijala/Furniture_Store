
package com.foryou.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foryou.webapp.entity.Image;
import com.foryou.webapp.repository.ImageRepository;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ImageRepository imageRepository;

    public void saveImage(Image image) {
        imageRepository.save(image);
    }

    public List<Image> getImagesByProductCategory(String productCategory) {
        return imageRepository.findByProductCategory(productCategory);
    }
    public Image getProductById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

	public Image findById(Long productId) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<Image> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

//	public double getAllProductPrices() {
//        return imageRepository.findAllPrices(); // Assuming you have a method in your repository to retrieve all product prices
//    }

	
}
