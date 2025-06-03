package com.authapi.auth_api.controllers;

import com.authapi.auth_api.domain.product.Product;
import com.authapi.auth_api.domain.product.ProductRequestDTO;
import com.authapi.auth_api.domain.product.ProductResponseDTO;
import com.authapi.auth_api.repositories.ProductRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    ProductRepository repository;

    @PostMapping
    public ResponseEntity<?> postProduct (@RequestBody @Valid ProductRequestDTO body) {
        Product newProduct = new Product(body);
        this.repository.save(newProduct);
        return ResponseEntity.ok().build();
    }

    @GetMapping 
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(this.repository.findAll().stream().map(ProductResponseDTO::new).toList());
    }
    

}
