package com.lucasffrezende.springmvc.repositories;

import com.lucasffrezende.springmvc.models.Pessoa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PessoaRepository extends CrudRepository<Pessoa, Long> {

    @Query("SELECT p FROM Pessoa p WHERE p.nome LIKE %?1%")
    List<Pessoa> findPessoaByNome(String nome);

}
