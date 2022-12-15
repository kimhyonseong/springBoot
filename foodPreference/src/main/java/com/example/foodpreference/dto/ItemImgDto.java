package com.example.foodpreference.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
}
