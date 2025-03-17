package com.forazaraf.APPACRM.Leads;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/leads")
@Tag(name = "Leads API", description = "Operations related to leads")
public class LeadController {

    @Autowired
    private LeadRepository leadRepository;

    @GetMapping
    @Operation(summary = "Get all leads", description = "Returns a list of all leads.")
    public ResponseEntity<List<Lead>> getAllLeads() {
        List<Lead> leads = leadRepository.findAll();
        return leads.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(leads);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get lead by ID", description = "Fetches details of a specific lead.")
    public ResponseEntity<Lead> getLeadById(@PathVariable Long id) {
        Optional<Lead> lead = leadRepository.findById(id);
        return lead.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new lead", description = "Adds a new lead.")
    public ResponseEntity<Lead> createLead(@RequestBody Lead lead) {
        return ResponseEntity.ok(leadRepository.save(lead));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update lead status", description = "Updates the status of an existing lead.")
    public ResponseEntity<Lead> updateLeadStatus(@PathVariable Long id, @RequestParam LeadStatus status) {
        Optional<Lead> leadOpt = leadRepository.findById(id);
        if (leadOpt.isPresent()) {
            Lead lead = leadOpt.get();
            lead.setStatus(status);
            return ResponseEntity.ok(leadRepository.save(lead));
        }
        return ResponseEntity.notFound().build();
    }
}

