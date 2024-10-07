package com.riwi.final_assessment_logistics_warehouse.common.infrastructure.validators.enums;

import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
  private Class<? extends Enum<?>> enumClass;

  @Override
  public void initialize(ValidEnum constraintAnnotation) {
    this.enumClass = constraintAnnotation.enumClass();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null)
      return false;
    return Arrays.stream(this.enumClass.getEnumConstants()).map(Enum::name).anyMatch(value::equals);
  }
}
