package com.creditcore.creditcore.loan;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan-products")
@RequiredArgsConstructor
public class LoanProductController {

    private final LoanProductService service;

    @PostMapping
    public ResponseEntity<LoanProduct> create(
            @RequestBody LoanProduct product
    ) {
        return ResponseEntity.ok(service.create(product));
    }

    @GetMapping
    public ResponseEntity<List<LoanProduct>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}