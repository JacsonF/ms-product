package com.jacsonf.productms.controller.dto.response;

import java.util.Objects;

import org.springframework.lang.NonNull;

public class Error {
	 private final Integer status_code;
	    private final String message;

	    public Error(@NonNull Integer status_code, @NonNull String message) {
	        this.status_code = Objects.requireNonNull(status_code);
	        this.message = Objects.requireNonNull(message);
	    }

	    public Integer getStatus_code() {
	        return status_code;
	    }

	    public String getMessage() {
	        return message;
	    }

}
