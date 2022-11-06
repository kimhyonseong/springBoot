package com.example.foodpreference.dto;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class MemberDto {
    @NotBlank(message = "아이디는 필수값입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$",message = "아이디는 소문자 영문과 숫자만 사용할 수 있습니다.")
    private String id;

    @Pattern(regexp = "^[가-힣]{2,10}$", message = "이름은 2글자 이상 10글자 이하입니다.")
    private String name;

    @NotBlank(message = "비밀번호는 필수값입니다.")
    @Pattern(regexp = ".{4,20}", message = "비밀번호는 4-20 글자입니다.")
    private String password;
}
