package com.mrthaw.inventory_backend.purchase_order.validator.annotation;

import com.mrthaw.inventory_backend.purchase_order.validator.UniqueItemListValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueItemListValidator.class)
@Documented
public @interface UniqueItemList {
    String message() default "Duplicate item not allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
