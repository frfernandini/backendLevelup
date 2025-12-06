package com.levelup.backend.services.impl;

import com.levelup.backend.models.Evento;
import com.levelup.backend.repositories.EventoRepository;
import com.levelup.backend.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Override
    public List<Evento> getAllEventos() {
        return eventoRepository.findAll();
    }
}

