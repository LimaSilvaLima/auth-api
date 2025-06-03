package com.authapi.auth_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authapi.auth_api.domain.product.Product;

public interface ProductRepository  extends JpaRepository<Product, String>{
    
    
}
