package com.eventosapp.igreja.repository;

import org.springframework.data.repository.CrudRepository;

import com.eventosapp.igreja.models.Igreja;

public interface IgrejaRepository extends CrudRepository<Igreja, String> {

	
	public Igreja findByCodigo(long codigo);
}
