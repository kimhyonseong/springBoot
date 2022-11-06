package com.example.foodpreference.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
public abstract class BaseValidator<T> implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }

  @Override
  @SuppressWarnings("unchecked")  // 미확인 오퍼레이션 경고 억제
  public void validate(Object target, Errors errors) {
    try {
      doValidate((T) target,errors);
    } catch (IllegalStateException e) {
      log.error("중복 검증",e);
      System.out.println("검증 완.");
      throw e;
    }
  }

  protected abstract void doValidate(final T dto,final Errors errors);
}
