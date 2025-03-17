package com.forazaraf.APPACRM.Offers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/offers")
@Tag(name = "Offers API", description = "Operations related to offers")
public class OfferController {

    @Autowired
    private OfferRepository offerRepository;

    @GetMapping
    @Operation(summary = "Get all offers", description = "Returns a list of all offers.")
    public ResponseEntity<List<Offer>> getAllOffers() {
        List<Offer> offers = offerRepository.findAll();
        return offers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(offers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get offer by ID", description = "Fetches details of a specific offer.")
    public ResponseEntity<Offer> getOfferById(@PathVariable Long id) {
        Optional<Offer> offer = offerRepository.findById(id);
        return offer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new offer", description = "Adds a new offer.")
    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer) {
        return ResponseEntity.ok(offerRepository.save(offer));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update offer status", description = "Updates the status of an existing offer.")
    public ResponseEntity<Offer> updateOfferStatus(@PathVariable Long id, @RequestParam Offer.OfferStatus status) {
        Optional<Offer> offerOpt = offerRepository.findById(id);
        if (offerOpt.isPresent()) {
            Offer offer = offerOpt.get();
            offer.setStatus(status);
            return ResponseEntity.ok(offerRepository.save(offer));
        }
        return ResponseEntity.notFound().build();
    }
}
