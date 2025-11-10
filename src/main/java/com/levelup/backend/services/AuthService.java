package com.levelup.backend.services;

import com.levelup.backend.dto.AuthResponse;
import com.levelup.backend.dto.LoginRequest;
import com.levelup.backend.dto.RegistroRequest;
import com.levelup.backend.exceptions.BadRequestException;
import com.levelup.backend.models.Usuario;
import com.levelup.backend.repositories.UsuarioRepository;
import com.levelup.backend.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UsuarioRepository usuarioRepository;
    // ✅ Simplificado - sin componentes de seguridad compleja
    
    @Transactional
    public AuthResponse registro(RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }
        
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(request.getPassword());  // ✅ Sin encriptar - más simple
        usuario.setTelefono(request.getTelefono());
        usuario.setDireccion(request.getDireccion());
        usuario.setActivo(true);
        
        Set<String> roles = new HashSet<>();
        roles.add("USER");
        usuario.setRoles(roles);
        
        usuario = usuarioRepository.save(usuario);
        
        // ✅ Token simple - solo retorna datos del usuario
        return new AuthResponse("simple-token-" + usuario.getId(), usuario.getId(), usuario.getNombre(), 
                              usuario.getEmail(), usuario.getRoles());
    }
    
    public AuthResponse login(LoginRequest request) {
        // ✅ Login simplificado - sin JWT complejo
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));
        
        // Validación simple de password
        if (!usuario.getPassword().equals(request.getPassword())) {
            throw new BadRequestException("Contraseña incorrecta");
        }
        
        // Token simple
        return new AuthResponse("simple-token-" + usuario.getId(), usuario.getId(), usuario.getNombre(),
                              usuario.getEmail(), usuario.getRoles());
    }
}
