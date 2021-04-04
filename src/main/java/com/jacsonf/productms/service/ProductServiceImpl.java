package com.jacsonf.productms.service;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Service;

import com.jacsonf.productms.domain.Product;
import com.jacsonf.productms.repository.ProductRepository;


@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        return null;
    }

    @Override
    public Product one(String id) {
         return productRepository.findById(id).orElseThrow(() -> new NoResultException("product code "+id+" not found"));
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> search(String descriptionAndName, BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.search(descriptionAndName, minPrice, maxPrice);
    }

    @Override
    public void delete(String id) {
    	if (!productRepository.existsById(id)) {
            throw new NoResultException("product code "+id+" not found");
        }
        productRepository.deleteById(id);
    }
}
