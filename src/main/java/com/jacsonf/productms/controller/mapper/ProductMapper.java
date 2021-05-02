package com.jacsonf.productms.controller.mapper;

import com.jacsonf.productms.controller.dto.request.Input;
import com.jacsonf.productms.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {
    public  static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    public abstract Product toProduct(Input input);
}
