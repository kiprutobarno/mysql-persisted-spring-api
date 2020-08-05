package com.example.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.api.models.Product;
import com.example.api.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    ProductService service;

    @GetMapping("/products")
    public Map<String, Object> getProducts() {
        List<Product> products = service.reads();
        HashMap<String, Object> map = new HashMap<>();
        ResponseEntity<?> response = ResponseEntity.ok(map);
        map.put("message", "OK");
        map.put("status", response.getStatusCodeValue());
        map.put("products", products);
        return map;
    }

    @GetMapping("/products/{id}")
    public Map<String, Object> getProduct(@PathVariable Integer id) {
        Product product = service.read(id);
        HashMap<String, Object> map = new HashMap<>();
        ResponseEntity<?> response = ResponseEntity.ok(map);
        map.put("message", "OK");
        map.put("status", response.getStatusCodeValue());
        map.put("product", product);
        return map;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/products")
    public Map<String, Object> createProduct(@RequestBody Product product) {
        service.create(product);
        HashMap<String, Object> map = new HashMap<>();
        ResponseEntity<?> response = ResponseEntity.status(HttpStatus.CREATED).body(map);
        map.put("message", "Created");
        map.put("status", response.getStatusCodeValue());
        return map;
    }

    @PutMapping("/products/{id}")
    public Map<String, Object> updateProduct(@PathVariable Integer id, @RequestBody Product productDetails) {
        Product product = service.read(id);
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        service.create(product);
        HashMap<String, Object> map = new HashMap<>();
        ResponseEntity<?> response = ResponseEntity.ok(map);
        map.put("message", "Updated");
        map.put("status", response.getStatusCodeValue());
        return map;
    }

    @DeleteMapping("/products/{id}")
    public Map<String, Object> delete(@PathVariable Integer id) {
        service.delete(id);
        HashMap<String, Object> map = new HashMap<String, Object>();
        ResponseEntity<?> response = ResponseEntity.ok(map);
        map.put("message", "Deleted");
        map.put("status", response.getStatusCodeValue());
        return map;
    }
}