package com.creditcore.creditcore.loan;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan-applications")
@RequiredArgsConstructor
public class LoanApplicationController {

    private final LoanApplicationService service;

    @PostMapping
    public ResponseEntity<LoanApplication> apply(
            @RequestParam Long productId,
            @RequestBody LoanApplication application) {
        return ResponseEntity.ok(service.apply(application, productId));
    }

    @GetMapping
    public ResponseEntity<List<LoanApplication>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<LoanApplication> approve(@PathVariable Long id) {
        return ResponseEntity.ok(service.approve(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<LoanApplication> reject(@PathVariable Long id) {
        return ResponseEntity.ok(service.reject(id));
    }

    @PutMapping("/{id}/disburse")
    public ResponseEntity<LoanApplication> disburse(@PathVariable Long id) {
        return ResponseEntity.ok(service.disburse(id));
    }
}