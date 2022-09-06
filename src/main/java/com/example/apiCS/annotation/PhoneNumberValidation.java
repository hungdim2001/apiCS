package com.example.apiCS.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PhoneNumberValidation implements ConstraintValidator<PhoneNumber, String> {
    public void initialize() {

    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext ctx) {
        if(phoneNumber== null){
            return  false;
        }
        if (phoneNumber.length() ==10&& phoneNumber.startsWith("0")
                && Arrays.stream(phoneNumber.split("")).noneMatch(x -> Character.isDigit(x.charAt(0)))) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate("Phone number must start with 0 and contains 10 digits").addConstraintViolation();
            return false;
        }
        return true;
    }
}
