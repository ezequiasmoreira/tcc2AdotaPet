package com.adotaPet.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.adotaPet.api.domain.Adocao;

@Repository
public interface AdocaoRepository extends JpaRepository<Adocao, Integer> {
	@Transactional(readOnly=true)
	@Query("SELECT max(obj.codigo) + 1 FROM Adocao obj WHERE obj.ong.id = :ongId ")
	Integer obterCodigo(@Param("ongId") Integer ongId);
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Adocao obj WHERE obj.pessoaInterressado.id = :pessoaId ORDER BY obj.id DESC")
	public List<Adocao> getAdocoes(@Param("pessoaId") Integer pessoaId);
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Adocao obj WHERE  obj.ong.id = :ongId AND obj.status = 1 ORDER BY obj.id DESC")
	public List<Adocao> solicitacaoAdocoes(@Param("ongId") Integer ongId);
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Adocao obj WHERE obj.status = 1  ORDER BY obj.id DESC")
	public List<Adocao> solicitacaoAdocoes();
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Adocao obj WHERE  obj.status = :status AND obj.animal.id = :animalId ORDER BY obj.id DESC")
	public List<Adocao> obterAdocaoPorAnimaleStatus(@Param("animalId") Integer animalId,@Param("status") Integer status);
}