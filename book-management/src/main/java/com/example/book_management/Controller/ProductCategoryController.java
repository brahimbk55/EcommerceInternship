package com.example.book_management.Controller;


import com.example.book_management.Entities.Product;
import com.example.book_management.Entities.ProductCategory;
import com.example.book_management.Service.Impl.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productCategory")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @PostMapping
    public ResponseEntity<ProductCategory> createProductCategory(@RequestBody ProductCategory productCategory) {
        return ResponseEntity.ok(productCategoryService.add(productCategory));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategory> getProductCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(productCategoryService.getById(id));
    }
    @GetMapping
    public ResponseEntity<List<ProductCategory>> getAllProductCategories() {
        return ResponseEntity.ok(productCategoryService.getAll());
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductCategory> updateProduct(@PathVariable Long id, @RequestBody ProductCategory productCategory) {
        productCategory.setId(id);
        return ResponseEntity.ok(productCategoryService.update(productCategory));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductCategory(@PathVariable Long id) {
        productCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
