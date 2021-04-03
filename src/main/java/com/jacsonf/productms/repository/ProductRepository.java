package com.jacsonf.productms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jacsonf.productms.domain.Product;

public interface ProductRepository extends JpaRepository<Product, String>, ProductCriteriaSearch {

}
