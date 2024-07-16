package io.system.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CouponCodeConstraintValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CouponCode {

    String value() default "";

    String message() default "invalid coupon code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}