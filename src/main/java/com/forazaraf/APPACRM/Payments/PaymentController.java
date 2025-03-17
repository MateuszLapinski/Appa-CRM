package com.forazaraf.APPACRM.Payments;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@Tag(name = "Payments API", description = "Operations related to invoice payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping
    @Operation(summary = "Get all payments", description = "Returns a list of all payments.")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(payments);
    }

    @GetMapping("/{invoiceId}")
    @Operation(summary = "Get payments for an invoice", description = "Fetches payments related to a specific invoice.")
    public ResponseEntity<List<Payment>> getPaymentsByInvoiceId(@PathVariable Long invoiceId) {
        List<Payment> payments = paymentRepository.findByInvoiceId(invoiceId);
        return payments.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(payments);
    }

    @PostMapping
    @Operation(summary = "Create a new payment", description = "Adds a new payment for an invoice.")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        return ResponseEntity.ok(paymentRepository.save(payment));
    }
}
