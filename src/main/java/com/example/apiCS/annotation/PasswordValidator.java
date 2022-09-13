package com.example.apiCS.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    private Password ann;
    public void initialize(Password ann) {
        this.ann = ann;
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext cxt) {
        if(password==null){
            return false;
        }
        if (password.toLowerCase().equals(password)) {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("Mật khẩu phải chứa in hoa.").addConstraintViolation();
            return false;
        }

        if (ann.containsDigit() && Arrays.stream(password.split("")).noneMatch(x -> Character.isDigit(x.charAt(0)))) {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("Mật khẩu phải chứa số.").addConstraintViolation();
            return false;
        }
        if (password.length() < 8) {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("Mật khẩu phải hơn 8 ký tự.").addConstraintViolation();
            return false;
        }
        Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasSpecial = special.matcher(password);
        if (!hasSpecial.find()) {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("Mật khẩu phải chứa ký tự đặc biệt.").addConstraintViolation();
            return false;
        }

        return true;
    }

}
