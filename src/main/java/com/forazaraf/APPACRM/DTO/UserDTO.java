package com.forazaraf.APPACRM.DTO;

import com.forazaraf.APPACRM.Role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class UserDTO {

        private String email;
        private String password;
        private String username;

        private int RoleId;

    }

