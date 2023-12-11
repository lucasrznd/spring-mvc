package com.lucasffrezende.springmvc.repositories;

import com.lucasffrezende.springmvc.models.Telefone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TelefoneRepository extends CrudRepository<Telefone, Long> {
}
