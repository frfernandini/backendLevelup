package com.levelup.backend.dto;

public class UsuarioActualizadoDto {

    private Long id;
    private String imagenUrl;

    public UsuarioActualizadoDto(Long id, String imagenUrl) {
        this.id = id;
        this.imagenUrl = imagenUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
}

