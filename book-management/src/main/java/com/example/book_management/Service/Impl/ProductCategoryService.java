package com.example.book_management.Service.Impl;

import com.example.book_management.Entities.ProductCategory;
import com.example.book_management.Repository.ProductCategoryRepository;
import com.example.book_management.Service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService implements IProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;


    @Override
    public ProductCategory add(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public ProductCategory update(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public void delete(Long id) {
        productCategoryRepository.deleteById(id);

    }

    @Override
    public List<ProductCategory> getAll() {

        return productCategoryRepository.findAll();
    }

    @Override
    public ProductCategory getById(Long id) {

        return productCategoryRepository.findById(id).orElse(null);
    }
}
