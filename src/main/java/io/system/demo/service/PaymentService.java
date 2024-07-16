package io.system.demo.service;

import java.math.BigDecimal;

public interface PaymentService {

    public BigDecimal calculatePrice(
            Long productId,
            String taxNumber,
            String couponCode
    ) throws Exception;

    public void makePayment(
            BigDecimal price,
            String paymentProcessor
    ) throws Exception;
}
