package com.mrthaw.inventory_backend.common.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueItemListValidator.class)
@Documented
public @interface UniqueItemList {

    String message() default "Duplicate item found";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
