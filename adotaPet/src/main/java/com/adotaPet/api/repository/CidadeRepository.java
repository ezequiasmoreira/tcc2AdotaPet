package com.adotaPet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.adotaPet.api.domain.Animal;
import com.adotaPet.api.domain.Cidade;


@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Cidade obj WHERE obj.estado.id = :estadoId ORDER BY obj.nome")
	public List<Cidade> findCidades(@Param("estadoId") Integer estado_id);
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Cidade obj WHERE obj.id = :id ")
	Cidade findCidadeById(@Param("id") Integer id);

	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Cidade obj WHERE obj.nome = :nome ")
	Cidade findCidadeByName(@Param("nome") String nome);

}






