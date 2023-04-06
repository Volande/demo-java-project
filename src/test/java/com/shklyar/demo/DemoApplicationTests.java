package com.shklyar.demo;

import com.shklyar.demo.dao.ImageRepository;
import com.shklyar.demo.dao.ProductRepository;
import com.shklyar.demo.entities.Images;
import com.shklyar.demo.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	ProductRepository productRepository;
	ImageRepository imageRepository;
	@Autowired
	public DemoApplicationTests(ProductRepository productRepository,ImageRepository imageRepository){
		this.imageRepository=imageRepository;
		this.productRepository=productRepository;
	}

	@Test
	void contextLoads() {
		Images image = imageRepository.findImagesById(288L);
		Product product = productRepository.findProductById(247L);

		System.out.println(product.getImage().size());
		product.getImage().remove(product.getImage().get(0));
		System.out.println(product.getImage().size());
		productRepository.save(product);
	}

}
