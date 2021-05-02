package com.jacsonf.productms.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import com.jacsonf.productms.controller.mapper.ProductMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jacsonf.productms.controller.dto.request.Input;
import com.jacsonf.productms.domain.Product;
import com.jacsonf.productms.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductControllerImpl implements ProductController{

	private final ProductService productService;

	public ProductControllerImpl(ProductService productService) {
		this.productService = productService;
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Product save(@Valid @RequestBody Input dto) {
		return productService.save(ProductMapper.INSTANCE.toProduct(dto));
	}

	@Override
	@GetMapping("{id}")
	public Product one(@PathVariable("id") String id) {
		return productService.one(id);
	}

	@Override
	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Product update(@PathVariable("id") String id, @Valid @RequestBody Input dto) {
		Product product = productService.one(id);
		product.setDescription(dto.getDescription());
		product.setName(dto.getName());
		product.setPrice(dto.getPrice());

		return productService.save(product);
	}

	@Override
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Product> getAll() {
		return productService.getAll();
	}

	@Override
	@DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") String id) {
        productService.delete(id);
    }

	@Override
	@GetMapping(path = "/search")
	public List<Product> searchProductByFilter(@RequestParam(required = false,name="min_price" ) BigDecimal minPrice,
			 @RequestParam(required = false, name = "max_price") BigDecimal maxPrice,
			 @RequestParam(required = false, name = "q", defaultValue = "") String nameOrDescription) {
	
		return  productService.search(nameOrDescription, minPrice,maxPrice);
	}

}