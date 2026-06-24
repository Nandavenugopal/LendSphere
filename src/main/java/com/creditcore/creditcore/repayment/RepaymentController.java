package com.creditcore.creditcore.repayment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/repayments")
@RequiredArgsConstructor
public class RepaymentController {

    private final RepaymentService service;

    @PostMapping
    public ResponseEntity<String> repay(@RequestBody RepaymentRequest request) {
        service.processPayment(
        request.getLoanId(),
        request.getAmount(),
        request.getIdempotencyKey()
);
        return ResponseEntity.ok("Payment processed successfully");
    }
}