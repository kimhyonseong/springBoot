package com.example.foodpreference.validator;

import com.example.foodpreference.dto.MemberDto;
import com.example.foodpreference.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class CheckIdValidator extends BaseValidator<MemberDto>{
  private final MemberRepository memberRepository;

  @Override
  protected void doValidate(MemberDto dto, Errors errors) {
    if (memberRepository.existsById(dto.getId())) {
      errors.rejectValue("id","아이디 중복 오류","이미 사용중인 아이디");
    }
  }
}
