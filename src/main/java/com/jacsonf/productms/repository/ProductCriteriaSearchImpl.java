package com.jacsonf.productms.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;

import com.jacsonf.productms.domain.Product;

@Service
public class ProductCriteriaSearchImpl implements ProductCriteriaSearch {
	EntityManager em;

	public ProductCriteriaSearchImpl(EntityManager em) {
		this.em = em;
	}

	public List<Product> search(String nameAndDescription, BigDecimal minPrice, BigDecimal maxPrice) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Product> cq = cb.createQuery(Product.class);
		Root<Product> product = cq.from(Product.class);
		List<Predicate> predicates = new ArrayList<>();
		
		buildQuery(nameAndDescription, minPrice, maxPrice, predicates, cb, product);
		cq.where(predicates.toArray( new Predicate[] {}));

		TypedQuery<Product> query = em.createQuery(cq);
		return query.getResultList();
	}
	
	private void buildQuery(String nameAndDescription, BigDecimal minPrice, BigDecimal maxPrice,List<Predicate> predicates,CriteriaBuilder cb, Root<Product> product) {
		if (nameAndDescription!=null) {
			Predicate name = cb.like(product.get("name"), "%" + nameAndDescription + "%");
			Predicate description = cb.like(product.get("description"), "%" + nameAndDescription + "%");
			Predicate nameOrDescription = cb.or(name,description);
			predicates.add(nameOrDescription);
		}
		if (minPrice!=null) {
			predicates.add(cb.greaterThanOrEqualTo(product.get("price"), minPrice));
		}
		if(maxPrice!=null) {
			predicates.add(cb.lessThanOrEqualTo(product.get("price"), maxPrice));
		}
	}

}
