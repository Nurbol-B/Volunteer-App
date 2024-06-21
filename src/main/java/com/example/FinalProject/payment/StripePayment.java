package com.example.FinalProject.payment;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import java.util.HashMap;
import java.util.Map;

public class StripePayment {

    public StripePayment() {
        // Установите ваш ключ API
        Stripe.apiKey = "sk_test_51PTRDA05rU3CaGF1moSU73vwX7O2xOh6hELSDsPzTw5hs1DWbB1zQV6i81lI8yY57wCUZ6XXTUPvtGNaduVOcEnp00poTgF3hC";
    }

    public Charge createCharge(int amount, String currency, String source, String description) {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", currency);
        chargeParams.put("source", source);
        chargeParams.put("description", description);

        try {
            return Charge.create(chargeParams);
        } catch (StripeException e) {
            // Обработка ошибок
            System.out.println("Stripe error: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        StripePayment stripePayment = new StripePayment();
        Charge charge = stripePayment.createCharge(1000, "usd", "tok_visa", "Test Charge");
        System.out.println(charge);
    }
}

