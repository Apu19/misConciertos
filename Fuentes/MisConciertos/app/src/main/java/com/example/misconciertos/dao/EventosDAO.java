package com.example.misconciertos.dao;


import com.example.misconciertos.dto.Evento;

import java.util.ArrayList;
import java.util.List;

public class EventosDAO {
    Evento e = null;
    private static List<Evento> eventos = new ArrayList<>();

    public void add(Evento e) {
        eventos.add(e);
    }

    public List<Evento> getAll(Evento e) {
    return eventos;
    }
}
