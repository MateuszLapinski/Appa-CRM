package com.forazaraf.APPACRM.Role;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
@Tag(name = "Roles API", description = "Operations related to user roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    @Operation(summary = "Get all roles", description = "Returns a list of all roles.")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get role by ID", description = "Fetches details of a specific role.")
    public ResponseEntity<Role> getRoleById(@PathVariable int id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
