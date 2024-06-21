package com.example.FinalProject.mapper;

import com.example.FinalProject.dto.ProductDto;
import com.example.FinalProject.dto.UserDetailsDto;
import com.example.FinalProject.entity.Product;
import com.example.FinalProject.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);

    void updateProductFromDto(@MappingTarget Product product, ProductDto productDto);

}
