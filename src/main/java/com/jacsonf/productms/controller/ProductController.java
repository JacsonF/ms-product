package com.jacsonf.productms.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.jacsonf.productms.controller.dto.request.Input;
import com.jacsonf.productms.domain.Product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface ProductController {

    @Operation(summary = "Cria um novo produto")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Product save(@Valid @RequestBody Input dto);

    @Operation(summary = "Retorna o produto correspondente ao identificador recuperado por parametro")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "    \"status_code\": 404,\n" +
                                            "    \"message\": \"product code 40288187789990c701789991c7340001 not found\"\n" +
                                            "}"
                            )
                    )
            )
    })
    @GetMapping("{id}")
    Product one(@PathVariable("id") String id);

    @Operation(summary = "Atualiza o produto correspondente ao identificador recuperado por parametro")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "    \"status_code\": 404,\n" +
                                            "    \"message\": \"product code 402881877899bc0c017899bc2bd10000 not found\"\n" +
                                            "}"
                            )
                    )
            )
    })
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    Product update(@PathVariable("id") String id, @Valid @RequestBody Input dto);

    @Operation(summary = "Retorna a lista atual de todos os produtos")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<Product> getAll();

    @Operation(summary = "Deleta o produto correspondente ao identificador recuperado por parametro")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "    \"status_code\": 404,\n" +
                                            "    \"message\": \"product code 402881877897f4c9017897f6df560005 not found\"\n" +
                                            "}"
                            )
                    )
            )
    })
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable("id") String id);

    @Operation(summary = "Retorna a lista atual de todos os produtos filtrados de acordo com os parametros passados na Url")
    @GetMapping(path = "/search")
    List<Product> searchProductByFilter(@RequestParam(required = false, name = "min_price") BigDecimal minPrice,
                                        @RequestParam(required = false, name = "max_price") BigDecimal maxPrice,
                                        @RequestParam(required = false, name = "q", defaultValue = "") String nameOrDescription);
}
