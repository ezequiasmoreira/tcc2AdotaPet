package com.adotaPet.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Pessoa obj WHERE obj.ong.id = :ongId ")
	Page<Pessoa> obterPessoaPorOng(@Param("ongId") Integer ongId, Pageable pageRequest);
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Pessoa obj WHERE obj.ong.id = :ongId ")
	public List<Pessoa> listPessoasOng(@Param("ongId") Integer ongId);
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Pessoa obj WHERE obj.id = :id ")
	Pessoa findPessoaId(@Param("id") Integer id);
	
	@Transactional(readOnly=true)
	Pessoa findByEmail(String email);
	
	@Transactional(readOnly=true)
	Pessoa findByCpf(String cpf);
	
	@Transactional(readOnly=true)
	Pessoa findByRg(String rg);
	
	
}