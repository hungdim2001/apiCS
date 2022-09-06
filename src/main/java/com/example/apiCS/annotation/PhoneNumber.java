package com.example.apiCS.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidation.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {
    String message() default "phone number must not be blank";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
