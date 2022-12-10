package com.example.foodpreference.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemImgDto {
    private String fileName;
    private String originFileName;
    private List<ItemImgDto> itemImgDtoList;
}
