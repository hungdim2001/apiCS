package com.example.apiCS.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    boolean containsDigit() default false;
    String message() default "Sai mật khẩu";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
