package com.levelup.backend.controllers;

import com.levelup.backend.dto.UsuarioActualizadoDto;
import com.levelup.backend.exceptions.ResourceNotFoundException;
import com.levelup.backend.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) { this.usuarioService = usuarioService; }

    @PutMapping("/{id}/imagen")
    public ResponseEntity<UsuarioActualizadoDto> actualizarImagenPerfil(
            @PathVariable("id") Long id,
            @RequestParam("imagen") MultipartFile imagen) {

        try {
            UsuarioActualizadoDto dto = usuarioService.actualizarImagenPerfil(id, imagen);
            return ResponseEntity.ok(dto);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

