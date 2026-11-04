package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dtos.request.ProductRequest;
import com.example.demo.dtos.response.ProductResponse;
import com.example.demo.entity.ProductEntity;
import com.example.demo.enums.ErrorCodeEnum;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
    private ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse findProductById(Long id) {
        ProductEntity p = productRepository.findById(id).orElseThrow(
            () -> new NotFoundException(ErrorCodeEnum.PRODUCT_NOT_FOUND));
        return  toResponse(p);
    }

    public ProductResponse saveProduct(ProductRequest productRequest) {
        ProductEntity product = new ProductEntity();
        product = productRepository.save(toEntity(productRequest));
        return toResponse(product);
    }

    private ProductEntity toEntity(ProductRequest p) {
        return new ProductEntity(
            p.getProductName(), 
            p.getProductDescription(), 
            p.getPrice(), 
            p.getStockQuantity(), 
            p.getStatus()
            );
    }

    private ProductResponse toResponse(ProductEntity p) {
        ProductResponse response = new ProductResponse();
        response.setProductName(p.getProductName());
        response.setProductDescription(p.getProductDescription());
        response.setPrice(p.getPrice());
        response.setStockQuantity(p.getStockQuantity());
        response.setStatus(p.getStatus());
        return response;
    }
}
