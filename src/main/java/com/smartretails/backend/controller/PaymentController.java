package com.smartretails.backend.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Value("${razorpay.keyId}")
    private String keyId;

    @Value("${razorpay.keySecret}")
    private String keySecret;

    // ============================
    // 1️⃣ CREATE ORDER
    // ============================
    @PostMapping("/create-order")
    public Map<String, Object> createOrder(@RequestBody Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();
        try {
            Number amtNum = (Number) data.get("amount");
            int amount = amtNum.intValue(); // safely cast any number type

            RazorpayClient client = new RazorpayClient(keyId, keySecret);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100); // in paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "order_rcptid_" + System.currentTimeMillis());

            Order order = client.orders.create(orderRequest);

            response.put("orderId", order.get("id"));
            response.put("amount", order.get("amount"));
            response.put("currency", order.get("currency"));
            response.put("key", keyId);
            response.put("status", "created");
        } catch (Exception ex) {
            response.put("status", "failed");
            response.put("message", "Error creating Razorpay order: " + ex.getMessage());
            ex.printStackTrace();
        }
        return response;
    }

    // ============================
    // 2️⃣ VERIFY PAYMENT
    // ============================
    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyPayment(@RequestBody Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Support both naming conventions
            String orderId = (String) data.getOrDefault("orderId", data.get("razorpay_order_id"));
            String paymentId = (String) data.getOrDefault("paymentId", data.get("razorpay_payment_id"));
            String signature = (String) data.getOrDefault("signature", data.get("razorpay_signature"));

            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", orderId);
            options.put("razorpay_payment_id", paymentId);
            options.put("razorpay_signature", signature);

            boolean isValidSignature = Utils.verifyPaymentSignature(options, keySecret);

            if (isValidSignature) {
                response.put("status", "success");
                response.put("message", "Payment verified successfully!");
            } else {
                response.put("status", "failed");
                response.put("message", "Invalid Razorpay signature!");
            }
        } catch (Exception ex) {
            response.put("status", "failed");
            response.put("message", "Error verifying payment: " + ex.getMessage());
            ex.printStackTrace();
        }
        return ResponseEntity.ok(response);
    }
}
