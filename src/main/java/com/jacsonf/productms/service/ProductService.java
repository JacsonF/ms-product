package com.jacsonf.productms.service;

import java.math.BigDecimal;
import java.util.List;

import com.jacsonf.productms.domain.Product;

public interface ProductService {
    Product save(Product product);
    Product update(Product product);
    Product one (String id);
    List<Product> getAll();
    List<Product> search(String nameAndDescription, BigDecimal minPrice, BigDecimal maxPrice);
    void delete(String id);
}
