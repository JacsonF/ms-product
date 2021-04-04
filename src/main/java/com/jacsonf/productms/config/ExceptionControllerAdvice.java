package com.jacsonf.productms.config;

import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.jacsonf.productms.controller.dto.response.Error;

import io.swagger.v3.oas.annotations.Hidden;

@ControllerAdvice
public class ExceptionControllerAdvice {
	private static final String VALIDATION_ERROR = "validation error";

	@Hidden
    @ResponseBody
    @ExceptionHandler(NoResultException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error noResult(NoResultException e) {
        return new Error(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }
    
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Error methodArgument(MethodArgumentNotValidException exception) {
		String message = exception.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
				.collect(Collectors.joining());
		return new Error(HttpStatus.BAD_REQUEST.value(), message);
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Error other(Exception e) {
		return new Error(HttpStatus.BAD_REQUEST.value(), VALIDATION_ERROR);
	}

}
