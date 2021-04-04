package com.jacsonf.productms.controller.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Input{
	  
    @NotEmpty(message="cannot be empty.")
    private String name;

    @NotEmpty(message="cannot be empty.")
    private String description;

    @NotNull(message="cannot be null.")
    private BigDecimal price;

    public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

}
