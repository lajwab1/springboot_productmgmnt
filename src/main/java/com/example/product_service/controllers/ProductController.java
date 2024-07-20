package com.example.product_service.controllers;

import com.example.product_service.Dtos.ProductDto;
import com.example.product_service.Entities.Product;
import com.example.product_service.Exceptions.ProductNotFoundException;
import com.example.product_service.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@CrossOrigin("http://localhost:3001")
@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts() {

        return productService.getAllProducts();
    }

    @PostMapping("/saveProduct")
    public Product saveProduct(@RequestBody ProductDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return productService.saveProduct(product);
    }

    @GetMapping("/findById")
    public ResponseEntity<?> findById(@RequestParam Long id) {
        try {
            Product product = productService.findById(id);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found with this Id " + id);

        }
    }

    @PostMapping("/edit")
    public Product edit(@RequestParam Long id, @RequestBody ProductDto dto) {
        Product product = productService.findById(id);
        if (product != null) {
            product.setPrice(dto.getPrice());
            product.setName(dto.getName());
           return productService.saveProduct(product);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }



    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long id) {
        try {
            Product product = productService.findById(id);

            if (product != null) {
                productService.delete(product);
                return ResponseEntity.ok("Product with ID " + id + " deleted successfully.");
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Product not found with ID: " + id);
            }
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error occurred: " + ex.getMessage());
        }
    }
}
