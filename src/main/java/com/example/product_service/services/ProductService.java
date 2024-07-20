package com.example.product_service.services;

import com.example.product_service.Entities.Product;
import com.example.product_service.Exceptions.ProductNotFoundException;
import com.example.product_service.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired

    private ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }


    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public Product findById(Long id){
        return productRepository.findById(id).orElseThrow(()->new ProductNotFoundException(id));
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }
}
