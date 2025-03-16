package com.forazaraf.APPACRM.Interactions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/interactions")
@Tag(name = "Interactions API", description = "Handles customer interactions")
public class InteractionController {

    @Autowired
    private InteractionRepository interactionRepository;

    @GetMapping
    @Operation(summary = "Get all interactions", description = "Returns a list of all customer interactions.")
    public ResponseEntity<List<Interaction>> getAllInteractions() {
        List<Interaction> interactions = interactionRepository.findAll();
        return interactions.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(interactions);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get interaction by ID", description = "Fetches details of a specific interaction using its ID.")
    public ResponseEntity<Interaction> getInteractionById(@PathVariable Long id) {
        Optional<Interaction> interaction = interactionRepository.findById(id);
        return interaction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get interactions by customer ID", description = "Fetches all interactions for a specific customer.")
    public ResponseEntity<List<Interaction>> getInteractionsByCustomer(@PathVariable Long customerId) {
        List<Interaction> interactions = interactionRepository.findByCustomerId(customerId);
        return interactions.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(interactions);
    }

    @PostMapping
    @Operation(summary = "Create a new interaction", description = "Adds a new interaction record for a customer.")
    public ResponseEntity<Interaction> createInteraction(@RequestBody Interaction interaction) {
        return ResponseEntity.ok(interactionRepository.save(interaction));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an interaction", description = "Modifies details of an existing interaction.")
    public ResponseEntity<Interaction> updateInteraction(@PathVariable Long id, @RequestBody Interaction updatedInteraction) {
        return interactionRepository.findById(id)
                .map(interaction -> {
                    interaction.setInteractionType(updatedInteraction.getInteractionType());
                    interaction.setDetails(updatedInteraction.getDetails());
                    return ResponseEntity.ok(interactionRepository.save(interaction));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an interaction", description = "Removes an interaction record from the database.")
    public ResponseEntity<Void> deleteInteraction(@PathVariable Long id) {
        if (!interactionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        interactionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
