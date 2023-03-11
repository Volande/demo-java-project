package com.shklyar.demo.service;

import com.shklyar.demo.dao.CategoryRepository;

import com.shklyar.demo.entities.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category initCategory(String category) {


        Category categories = categoryRepository.getByTitle(category);

        if (categories == null) {
            categories = new Category();
            categories.setTitle(category);
            categoryRepository.save(categories);
        }

        return categories;
    }
}
