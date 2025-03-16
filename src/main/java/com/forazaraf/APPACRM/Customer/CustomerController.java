package com.forazaraf.APPACRM.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
@Tag(name = "CustomerModel API", description = "CustomerModel management operations") // ✅ Correct @Tag usage
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/all")
    @Operation(summary = "Retrieve all customers", description = "Returns a list of all customers with pagination.")
    public ResponseEntity<List<CustomerModel>> getAllCustomers() {
        List<CustomerModel> customers = (List<CustomerModel>) customerRepository.findAll();
        return customers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get CustomerModel by ID", description = "Fetches details of a specific CustomerModel using their ID.")
    public ResponseEntity<CustomerModel> getCustomerById(@PathVariable int id) {
        Optional<CustomerModel> CustomerModel = customerRepository.findById(id);
        return CustomerModel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new CustomerModel", description = "Adds a new CustomerModel to the database.")
    public ResponseEntity<CustomerModel> createCustomer(@RequestBody CustomerModel CustomerModel) {
        if (customerRepository.existsByEmail(CustomerModel.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(customerRepository.save(CustomerModel));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update CustomerModel", description = "Modifies an existing CustomerModel's details.")
    public ResponseEntity<CustomerModel> updateCustomer(@PathVariable int id, @RequestBody CustomerModel updatedCustomer) {
        return customerRepository.findById(id)
                .map(CustomerModel -> {
                    CustomerModel.setName(updatedCustomer.getName());
                    CustomerModel.setEmail(updatedCustomer.getEmail());
                    CustomerModel.setPhone(updatedCustomer.getPhone());
                    CustomerModel.setAddress(updatedCustomer.getAddress());
                    CustomerModel.setCity(updatedCustomer.getCity());
                    CustomerModel.setIndustry(updatedCustomer.getIndustry());
                    CustomerModel.setStatus(updatedCustomer.getStatus());
                    return ResponseEntity.ok(customerRepository.save(CustomerModel));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change CustomerModel status", description = "Updates a CustomerModel's status to 'active' or 'inactive'.")
    public ResponseEntity<CustomerModel> updateCustomerStatus(@PathVariable int id, @RequestParam String status) {
        if (!status.equals("active") && !status.equals("inactive")) {
            return ResponseEntity.badRequest().body(null);
        }
        return customerRepository.findById(id)
                .map(CustomerModel -> {
                    CustomerModel.setStatus(status);
                    customerRepository.save(CustomerModel);
                    return ResponseEntity.ok(CustomerModel);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete CustomerModel", description = "Removes a CustomerModel from the database by ID.")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        if (!customerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        customerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
