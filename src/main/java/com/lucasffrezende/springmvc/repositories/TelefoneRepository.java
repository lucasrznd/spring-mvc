package com.lucasffrezende.springmvc.repositories;

import com.lucasffrezende.springmvc.models.Telefone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TelefoneRepository extends CrudRepository<Telefone, Long> {

    @Query("SELECT t FROM Telefone t WHERE t.pessoa.id = ?1")
    public List<Telefone> getTelefonesByPessoa(Long id);

}
