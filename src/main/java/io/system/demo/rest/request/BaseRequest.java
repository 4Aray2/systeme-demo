package io.system.demo.rest.request;

import io.system.demo.validation.CouponCode;
import io.system.demo.validation.TaxNumber;
import jakarta.validation.constraints.NotNull;

public class BaseRequest {
    @NotNull(message = "product required")
    private Long product;

    @NotNull(message = "taxNumber required")
    @TaxNumber(message = "invalid tax number")
    private String taxNumber;

    @CouponCode(message = "invalid coupon")
    private String couponCode;

    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
}
