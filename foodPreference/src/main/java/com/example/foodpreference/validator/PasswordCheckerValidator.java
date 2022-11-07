package com.example.foodpreference.validator;

import com.example.foodpreference.dto.MemberDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PasswordCheckerValidator extends BaseValidator<MemberDto>{
    @Override
    protected void doValidate(MemberDto dto, Errors errors) {
        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            errors.rejectValue("passwordConfirm","비밀번호 불일치","비밀번호와 일치하지 않습니다.");
        }
    }
}
