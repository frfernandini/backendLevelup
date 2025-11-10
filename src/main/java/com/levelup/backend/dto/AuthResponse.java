package com.levelup.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String tipo = "Bearer";
    private Long id;
    private String nombre;
    private String email;
    private Set<String> roles;
    
    public AuthResponse(String token, Long id, String nombre, String email, Set<String> roles) {
        this.token = token;
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.roles = roles;
    }
}
