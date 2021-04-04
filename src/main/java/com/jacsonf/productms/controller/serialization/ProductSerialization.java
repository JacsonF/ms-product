package com.jacsonf.productms.controller.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jacsonf.productms.controller.dto.response.Output;
import com.jacsonf.productms.domain.Product;
import org.modelmapper.ModelMapper;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class ProductSerialization extends JsonSerializer<Product> {

    private final ModelMapper modelMapper;

    public ProductSerialization(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public void serialize(Product product, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Output output = modelMapper.map(product, Output.class);
        jsonGenerator.writeObject(output);
    }
}
