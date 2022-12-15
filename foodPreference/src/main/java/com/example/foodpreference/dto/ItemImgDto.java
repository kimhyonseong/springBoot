package com.example.foodpreference.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemImgDto {
    private String fileName;
    private String originFileName;
    private List<ItemImgDto> itemImgDtoList;
}
