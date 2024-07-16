package io.system.demo.controller;



import io.system.demo.rest.PaymentController;
import io.system.demo.rest.request.PriceRequest;
import io.system.demo.rest.request.PurchaseRequest;
import io.system.demo.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    public void testCalculatePriceEndpoint() throws Exception {
        PriceRequest request = new PriceRequest();
        request.setProduct(1L);
        request.setTaxNumber("DE123456789");
        request.setCouponCode("P10");

        when(paymentService.calculatePrice(1L, "DE123456789", "P10"))
                .thenReturn(BigDecimal.valueOf(107.10).setScale(2));

        mockMvc.perform(post("/calculate-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"product\": 1, \"taxNumber\": \"DE123456789\", \"couponCode\": \"P10\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(107.10));
    }

    @Test
    public void testPurchaseEndpoint() throws Exception {
        PurchaseRequest request = new PurchaseRequest();
        request.setProduct(1L);
        request.setTaxNumber("DE123456789");
        request.setCouponCode("P10");
        request.setPaymentProcessor("paypal");

        when(paymentService.calculatePrice(1L, "DE123456789", "P10"))
                .thenReturn(BigDecimal.valueOf(107.10).setScale(2));

        doNothing().when(paymentService).makePayment(BigDecimal.valueOf(107.10).setScale(2), "paypal");

        mockMvc.perform(post("/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"product\": 1, \"taxNumber\": \"DE123456789\", \"couponCode\": \"P10\", \"paymentProcessor\": \"paypal\" }"))
                .andExpect(status().isOk());
    }
}

