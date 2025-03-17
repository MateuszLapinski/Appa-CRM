package com.forazaraf.APPACRM.Role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    public String getName() {
        return name.toUpperCase();
    }
}
