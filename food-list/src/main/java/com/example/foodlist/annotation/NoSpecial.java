package com.example.foodlist.annotation;

import com.example.foodlist.validator.NoSpecialValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.CONSTRUCTOR,ElementType.PARAMETER,ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoSpecialValidator.class)
public @interface NoSpecial{
    String message() default "특수문자 금지";

    Class[] groups() default {};
    Class[] payload() default {};
}
