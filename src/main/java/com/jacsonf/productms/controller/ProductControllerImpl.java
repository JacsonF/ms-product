package com.jacsonf.productms.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.jacsonf.productms.controller.dto.request.ProductRequestDto;
import com.jacsonf.productms.domain.Product;
import com.jacsonf.productms.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Product save(@Valid @RequestBody ProductRequestDto dto) {
		Product product = new Product(dto.getName(), dto.getDescription(), dto.getPrice());
		return productService.save(product);
	}

	@GetMapping("{id}")
	public Product one(@PathVariable("id") String id) {
		return productService.one(id);
	}

	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Product update(@PathVariable("id") String id, @Valid @RequestBody ProductRequestDto dto) {
		Product product = productService.one(id);
		product.setDescription(dto.getDescription());
		product.setName(dto.getName());
		product.setPrice(dto.getPrice());

		return productService.save(product);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Product>> getAll() {
		return ResponseEntity.ok(productService.getAll());
	}
	
	@DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") String id) {
        productService.delete(id);
    }

	@GetMapping(path = "/search")
	public List<Product> searchProductByFilter(@RequestParam(required = false,name="min_price" ) BigDecimal minPrice,
			 @RequestParam(required = false, name = "max_price") BigDecimal maxPrice,
			 @RequestParam(required = false, name = "q", defaultValue = "") String nameOrDescription) {
	
		return  productService.search(nameOrDescription, minPrice,maxPrice);

	}


}
/**
 * POST /products Criação de um produto PUT /products/{id} Atualização de um
 * produto GET /products/{id} Busca de um produto por ID GET /products Lista de
 * produtos GET /products/search Lista de produtos filtrados DELETE
 * /products/{id} Deleção de um produto
 */