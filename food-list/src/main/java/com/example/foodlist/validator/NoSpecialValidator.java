package com.example.foodlist.validator;

import com.example.foodlist.annotation.NoSpecial;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NoSpecialValidator implements ConstraintValidator<NoSpecial,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        String pattern = "[ !@#$%^&*\\(\\)\\-\\.\\,\\\\_+=|\\/\\[\\]\\<\\>\\?'\"`~]";

        return !Pattern.compile(pattern).matcher(value).find();
    }
}
