package io.system.demo.service;

import io.system.demo.entity.Coupon;
import io.system.demo.entity.Product;
import io.system.demo.repo.CouponRepository;
import io.system.demo.repo.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PaymentServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculatePriceOutOfStock() throws Exception {
        Product product = new Product(1L, "iphone", BigDecimal.valueOf(100), false);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Exception exception = assertThrows(Exception.class, () -> {
            paymentService.calculatePrice(1L, "DE123456789", null);
        });
        assertEquals("Product out of stock", exception.getMessage());
    }

    @Test
    public void testCalculatePriceWithoutCoupon() throws Exception {
        Product product = new Product(1L, "iphone", BigDecimal.valueOf(100), true);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        BigDecimal price = paymentService.calculatePrice(1L, "DE123456789", null);
        assertEquals(BigDecimal.valueOf(119.00).setScale(2, RoundingMode.CEILING), price);
    }

    @Test
    public void testCalculatePriceWithPercentageCoupon() throws Exception {
        Product product = new Product(1L, "iphone", BigDecimal.valueOf(100), true);
        Coupon coupon = new Coupon(1L, "P10", BigDecimal.valueOf(10), true, true);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(couponRepository.findByCode("P10")).thenReturn(Optional.of(coupon));

        BigDecimal price = paymentService.calculatePrice(1L, "DE123456789", "P10");
        assertEquals(BigDecimal.valueOf(107.10).setScale(2, RoundingMode.CEILING), price);
    }

    @Test
    public void testCalculatePriceWithFixedCoupon() throws Exception {
        Product product = new Product(1L, "iphone", BigDecimal.valueOf(100), true);
        Coupon coupon = new Coupon(1L, "F15", BigDecimal.valueOf(15), false, true);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(couponRepository.findByCode("F15")).thenReturn(Optional.of(coupon));

        BigDecimal price = paymentService.calculatePrice(1L, "DE123456789", "F15");
        assertEquals(BigDecimal.valueOf(101.15).setScale(2, RoundingMode.CEILING), price);
    }

    @Test
    public void testMakePaymentWithPaypal() throws Exception {
        paymentService.makePayment(BigDecimal.valueOf(100000), "paypal");
    }

    @Test
    public void testMakePaymentWithPaypalFails() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            paymentService.makePayment(BigDecimal.valueOf(100001), "paypal");
        });
        assertEquals("Price too high", exception.getMessage());
    }

    @Test
    public void testMakePaymentWithStripe() throws Exception {
        paymentService.makePayment(BigDecimal.valueOf(5000), "stripe");
    }

    @Test
    public void testMakePaymentWithStripeFails() {
        Exception exception = assertThrows(Exception.class, () -> {
            paymentService.makePayment(BigDecimal.valueOf(99), "stripe");
        });
        assertEquals("Payment failed", exception.getMessage());
    }
}