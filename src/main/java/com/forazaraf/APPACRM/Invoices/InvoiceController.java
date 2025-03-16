package com.forazaraf.APPACRM.Invoices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/invoices")
@Tag(name = "Invoices API", description = "Handles invoices and billing")
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    @GetMapping
    @Operation(summary = "Get all invoices", description = "Returns a list of all invoices.")
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}/details")
    @Operation(summary = "Get invoice with items", description = "Fetches invoice details along with associated items.")
    public ResponseEntity<InvoiceDetailsResponse> getInvoiceWithItems(@PathVariable Long id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<InvoiceItem> items = invoiceItemRepository.findByInvoiceId(id);
        InvoiceDetailsResponse response = new InvoiceDetailsResponse(invoice.get(), items);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get invoice by ID", description = "Fetches details of a specific invoice.")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        return invoice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new invoice", description = "Adds a new invoice.")
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        return ResponseEntity.ok(invoiceRepository.save(invoice));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an invoice", description = "Modifies details of an existing invoice.")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice updatedInvoice) {
        return invoiceRepository.findById(id)
                .map(invoice -> {
                    invoice.setInvoiceNumber(updatedInvoice.getInvoiceNumber());
                    invoice.setDueDate(updatedInvoice.getDueDate());
                    invoice.setStatus(updatedInvoice.getStatus());
                    invoice.setTotalAmount(updatedInvoice.getTotalAmount());
                    invoice.setVatAmount(updatedInvoice.getVatAmount());
                    return ResponseEntity.ok(invoiceRepository.save(invoice));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an invoice", description = "Removes an invoice from the database.")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        if (!invoiceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        invoiceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

