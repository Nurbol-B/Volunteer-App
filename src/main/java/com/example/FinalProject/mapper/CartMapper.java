package com.example.FinalProject.mapper;

import com.example.FinalProject.dto.CartDto;
import com.example.FinalProject.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {
    @Mapping(source = "user.id", target = "userId")
    CartDto toDto(Cart cart);
    Cart toEntity(CartDto cartDto);
}
