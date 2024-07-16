package io.system.demo.service;

import io.system.demo.entity.Coupon;
import io.system.demo.entity.Product;
import io.system.demo.exceptionalHandling.ProductNotFoundException;
import io.system.demo.payment.PaypalPaymentProcessor;
import io.system.demo.payment.StripePaymentProcessor;
import io.system.demo.repo.CouponRepository;
import io.system.demo.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CouponRepository couponRepository;

    public BigDecimal calculatePrice(Long productId, String taxNumber, String couponCode) throws Exception {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found", productId));

        BigDecimal price = product.getPrice();
        BigDecimal discount = BigDecimal.ZERO;

        if (couponCode != null && !couponCode.isEmpty()) {
            Coupon coupon = couponRepository.findByCode(couponCode)
                    .orElseThrow(() -> new Exception("Coupon not found"));
            if (coupon.isPercentage()) {
                discount = price.multiply(coupon.getDiscount().divide(BigDecimal.valueOf(100)));
            } else {
                discount = coupon.getDiscount();
            }
        }

        price = price.subtract(discount);
        BigDecimal tax = getTax(taxNumber, price);

        return price.add(tax);
    }

    private BigDecimal getTax(String taxNumber, BigDecimal price) throws Exception {
        String countryCode = taxNumber.substring(0, 2);
        BigDecimal taxRate = switch (countryCode) {
            case "DE" -> BigDecimal.valueOf(0.19);
            case "IT" -> BigDecimal.valueOf(0.22);
            case "FR" -> BigDecimal.valueOf(0.20);
            case "GR" -> BigDecimal.valueOf(0.24);
            default -> throw new Exception("Invalid tax number");
        };

        return price.multiply(taxRate);
    }

    public void makePayment(BigDecimal price, String paymentProcessor) throws Exception {
        switch (paymentProcessor) {
            case "paypal":
                PaypalPaymentProcessor paypal = new PaypalPaymentProcessor();
                paypal.makePayment(price.intValue());
                break;
            case "stripe":
                StripePaymentProcessor stripe = new StripePaymentProcessor();
                boolean result = stripe.pay(price.floatValue());
                if (!result) {
                    throw new Exception("Payment failed");
                }
                break;
            default:
                throw new Exception("Invalid payment processor");
        }
    }
}

