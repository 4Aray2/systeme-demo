package io.system.demo.rest;

import io.system.demo.rest.request.PriceRequest;
import io.system.demo.rest.request.PurchaseRequest;
import io.system.demo.rest.response.ErrorResponse;
import io.system.demo.rest.response.PriceResponse;
import io.system.demo.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/calculate-price")
    public ResponseEntity<?> calculatePrice(@Valid @RequestBody PriceRequest request) {
        try {
            BigDecimal price = paymentService.calculatePrice(request.getProduct(), request.getTaxNumber(), request.getCouponCode());
            return ResponseEntity.ok(new PriceResponse(price));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(@Valid @RequestBody PurchaseRequest request) {
        try {
            BigDecimal price = paymentService.calculatePrice(request.getProduct(), request.getTaxNumber(), request.getCouponCode());
            paymentService.makePayment(price, request.getPaymentProcessor());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
}

