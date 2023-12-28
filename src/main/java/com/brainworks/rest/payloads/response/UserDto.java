package com.brainworks.rest.payloads.response;

import com.brainworks.rest.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer userId;

    @NotEmpty
    @Size(min = 2,message = "Atleast 2 characters expected!!!")
    private String name;

    @Email(message = "Email is not valid!!!")
    @Pattern (regexp = "^[A-Za-z0-9+_.-]+@gmail\\.com$")
    private String email;

    @NotEmpty
    @Pattern (regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,20}$",message = "At least one uppercase letter, one lowercase letter, one digit, and minimum length of 8 characters")
    @JsonIgnoreProperties
    private  String password;

    @NotEmpty
    private String about;

    private Set<RoleDto> role;

}