package com.example.foodpreference.dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class MemberDto {
    private Long idx;
    private String name;
    private String id;
    private String password;
    private String role;
}
