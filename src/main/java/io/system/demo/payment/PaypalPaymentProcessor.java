package io.system.demo.payment;

public class PaypalPaymentProcessor {
    public void makePayment(int price) throws Exception {
        if (price > 100000) {
            throw new Exception("Price too high");
        }
    }
}

