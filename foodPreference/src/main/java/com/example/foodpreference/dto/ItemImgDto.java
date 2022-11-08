package com.example.foodpreference.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemImgDto {
    private String imgUrl;
    private int width;
    private int height;
    private String extension;
}
