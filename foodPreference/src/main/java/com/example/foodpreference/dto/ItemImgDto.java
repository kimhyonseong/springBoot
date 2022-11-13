package com.example.foodpreference.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemImgDto {
    private String imgUrl;
    private String originName;
    private List<ItemImgDto> itemImgDtoList;
}
