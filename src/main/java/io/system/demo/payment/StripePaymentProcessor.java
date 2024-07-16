package io.system.demo.payment;

public class StripePaymentProcessor {
    public boolean pay(float price) {
        return price >= 100;
    }
}

