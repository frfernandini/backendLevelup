package com.levelup.backend.services;

import com.levelup.backend.dto.UsuarioActualizadoDto;
import com.levelup.backend.exceptions.ResourceNotFoundException;
import com.levelup.backend.models.Usuario;
import com.levelup.backend.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final S3Service s3Service;

    public UsuarioService(UsuarioRepository usuarioRepository, S3Service s3Service) {
        this.usuarioRepository = usuarioRepository;
        this.s3Service = s3Service;
    }

    public UsuarioActualizadoDto actualizarImagenPerfil(Long usuarioId, MultipartFile imagenFile) throws IOException {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + usuarioId));

        String url = s3Service.subirArchivo(imagenFile);
        usuario.setImagenUrl(url);
        usuarioRepository.save(usuario);

        return new UsuarioActualizadoDto(usuario.getId(), url);
    }
}

