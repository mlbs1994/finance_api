package com.mlbs.finance_api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.mlbs.finance_api.entities.Gasto;

public interface GastoRepository extends MongoRepository<Gasto, String>{
	 Page<Gasto> findAll(Pageable pageable);
}
