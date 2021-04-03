package com.jacsonf.productms.repository;

import java.math.BigDecimal;
import java.util.List;

import com.jacsonf.productms.domain.Product;

public interface ProductCriteriaSearch {
	List<Product> search(String nameAndDescription, BigDecimal minPrice, BigDecimal maxPrice);
}
