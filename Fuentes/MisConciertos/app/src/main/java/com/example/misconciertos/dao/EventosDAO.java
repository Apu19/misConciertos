package com.example.misconciertos.dao;


import com.example.misconciertos.dto.Evento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EventosDAO {
    public static List<Evento> eventos = new ArrayList<Evento>();
    public void add(Evento e){
        eventos.add(e);
    }
    public List<Evento> getAll(){
        return eventos;
    }

}
