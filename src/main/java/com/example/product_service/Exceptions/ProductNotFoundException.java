package com.example.product_service.Exceptions;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException( Long id){
        super("could not found the product with this id" + id);
    }
}
