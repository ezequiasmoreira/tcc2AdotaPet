package com.adotaPet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.adotaPet.api.domain.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
	
	@Transactional(readOnly=true)
	@Query("SELECT max(obj.codigo) + 1 FROM Pessoa obj WHERE obj.ong.id = :ongId ")
	Integer obterCodigo(@Param("ongId") Integer ongId);

}