package com.jacsonf.productms.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hamcrest.core.Is;
import org.hamcrest.text.IsEmptyString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacsonf.productms.ProductMsApplicationTests;
import com.jacsonf.productms.config.ExceptionControllerAdvice;
import com.jacsonf.productms.controller.dto.request.Input;
import com.jacsonf.productms.domain.Product;
import com.jacsonf.productms.service.ProductService;

class ProductControllerTest extends ProductMsApplicationTests {

	private final String BASE_URL = "/products";
	private MockMvc mockMvc;

	@Autowired
	ProductControllerImpl productController;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductService service;

	@BeforeEach
	public void setUp() {
		objectMapper = new ObjectMapper();
		this.mockMvc = MockMvcBuilders.standaloneSetup(productController)
				.setControllerAdvice(new ExceptionControllerAdvice()).build();
	}

	@Test
	void whenPostValidProduct_thenSucess() throws Exception {
		Input productRequestDto = new Input();

		productRequestDto.setDescription("SSSS");
		productRequestDto.setName("SSSS");
		productRequestDto.setPrice(BigDecimal.TEN);
		this.mockMvc
				.perform(post(BASE_URL).contentType("application/json").accept("application/json")
						.content(objectMapper.writeValueAsString(productRequestDto)))
				.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.id").exists());
	}

	@Test
	void whenPostProductWhithInvalidParamns_thenBadRequest() throws Exception {
		Input productRequestDto = new Input();

		productRequestDto.setName("SSSS");
		productRequestDto.setPrice(BigDecimal.TEN);
		this.mockMvc
				.perform(post("/products").contentType("application/json").accept("application/json")
						.content(objectMapper.writeValueAsString(productRequestDto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void whenPutInexistProductId_thenNotFound() throws Exception {

		Input productRequestDto = new Input();
		productRequestDto.setDescription("SSSS");
		productRequestDto.setName("SSSS");
		productRequestDto.setPrice(BigDecimal.TEN);
		this.mockMvc
				.perform(put(BASE_URL + "/whatherverId").contentType("application/json").accept("application/json")
						.content(objectMapper.writeValueAsString(productRequestDto)))
				.andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	void whenPutInvalidProductWhithInvalidParams_thenBadRequest() throws Exception {

		Input productRequestDto = new Input();
		productRequestDto.setDescription("SSSS");
		productRequestDto.setName("SSSS");
		this.mockMvc
				.perform(put(BASE_URL + "/whatherverId").contentType("application/json").accept("application/json")
						.content(objectMapper.writeValueAsString(productRequestDto)))
				.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	void whenPutValidProduct_thenSucess() throws Exception {

		Product product = new Product("Cap", "Red", new BigDecimal(9.5));
		Input productRequestDto = new Input();

		productRequestDto.setDescription("CapUpgrade");
		productRequestDto.setName("CabStyle");
		productRequestDto.setPrice(BigDecimal.TEN);

		product = service.save(product);
		this.mockMvc
				.perform(put(BASE_URL + "/" + product.getId()).contentType("application/json")
						.accept("application/json").content(objectMapper.writeValueAsString(productRequestDto)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(product.getId())))
				.andExpect(jsonPath("$.name", is(productRequestDto.getName())))
				.andExpect(jsonPath("$.description", is(productRequestDto.getDescription())))
				.andExpect(jsonPath("$.price", is(10)));
	}

	@Test
	void whenGetByInexistProductId_thenNotFound() throws Exception {
		this.mockMvc.perform(get(BASE_URL + "/54666")).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void whenGetByValidProductId_thenSucess() throws Exception {
		Product product = new Product("Cap", "Red", new BigDecimal(9.5));
		product = service.save(product);

		this.mockMvc.perform(get(BASE_URL + "/" + product.getId())).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.id", is(product.getId()))).andExpect(jsonPath("$.name", is(product.getName())))
				.andExpect(jsonPath("$.description", is(product.getDescription())))
				.andExpect(jsonPath("$.price", is(9.5)));

	}

	@Test
	void whenGetAllProducts_thenResponseOk() throws Exception {
		this.mockMvc.perform(get("/products")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void whenDeleteInexistProductId_thenNotfound() throws Exception {
		this.mockMvc.perform(delete(BASE_URL + "/99999")).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	void whenDeleteValidProductId_thenResponseOk() throws Exception {
		Product product = new Product("Cap", "Red", new BigDecimal(9.5));
		product = service.save(product);

		this.mockMvc.perform(delete(BASE_URL + "/" + product.getId())).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void whenSearchValidProductParams_thenSouldBeFound() throws Exception {
		String nameAndDescription = "o";
		String minPrice = "199";
		String maxPrice = "210";
		Product product = new Product("Ipod", "Cool design", new BigDecimal(200.0));
		product = service.save(product);

		this.mockMvc
				.perform(get(BASE_URL + "/search").param("q", nameAndDescription).param("min_price", minPrice)
						.param("max_price", maxPrice))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$[0].id", is(product.getId())))
				.andExpect(jsonPath("$[0].name", is(product.getName())))
				.andExpect(jsonPath("$[0].description", is(product.getDescription())))
				.andExpect(jsonPath("$[0].price", is(200.0)));
	}

	@Test
	void whenSerchProductInvalidParams_thenReturnBadRequest() throws Exception {
		String nameAndDescription = "0";
		String minPrice = "XX";
		String maxPrice = "AA";
		Product product = new Product("Ipod", "Cool design", new BigDecimal(9.5));
		product = service.save(product);

		this.mockMvc.perform(get(BASE_URL + "/search").param("q", nameAndDescription).param("min_price", minPrice)
				.param("max_price", maxPrice)).andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	

	@Test
	void whenSerchInexistProductWhitchValidParamans_thenSouldBeResponseEmpty() throws Exception {
		String nameAndDescription = "0";
		String minPrice = "9.4";
		String maxPrice = "9.4";
		Product product = new Product("Ipod", "Cool design", new BigDecimal(9.5));
		product = service.save(product);

		this.mockMvc
				.perform(get(BASE_URL + "/search").param("q", nameAndDescription).param("min_price", minPrice)
						.param("max_price", maxPrice))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$", is(Collections.emptyList())));
	}
}
