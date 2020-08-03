package com.example.api.controllers;

import java.util.List;

import com.example.api.models.Product;
import com.example.api.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    ProductService service;

    @GetMapping("/products")
    public List<Product> list() {
        return service.listAll();
    }
}