package com.eventosapp.igreja.repository;

import org.springframework.data.repository.CrudRepository;

import com.eventosapp.igreja.models.Evento;
import com.eventosapp.igreja.models.Igreja;

public interface EventoRepository extends CrudRepository<Evento, String>{
     Iterable<Evento> findByIgreja(Igreja igreja); 
     
     public Evento findByCodigo(long codigo);
}
