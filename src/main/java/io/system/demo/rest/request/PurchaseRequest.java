package io.system.demo.rest.request;

import jakarta.validation.constraints.NotBlank;

public class PurchaseRequest extends BaseRequest {

    @NotBlank(message = "Payment processor cannot be blank")
    private String paymentProcessor;

    public String getPaymentProcessor() {
        return paymentProcessor;
    }

    public void setPaymentProcessor(String paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }
}
