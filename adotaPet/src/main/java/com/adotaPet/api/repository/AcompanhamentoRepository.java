package com.adotaPet.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.adotaPet.api.domain.Acompanhamento;
import com.adotaPet.api.domain.Adocao;;

@Repository
public interface AcompanhamentoRepository extends JpaRepository<Acompanhamento, Integer> {
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Acompanhamento obj WHERE obj.status <> 4 ORDER BY obj.id DESC")
	public List<Acompanhamento> getAcompamhamentosNaoFinalizado();
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Acompanhamento obj WHERE obj.status = :status ORDER BY obj.id DESC")
	public List<Acompanhamento> getAcompanhamentoPorStatus(@Param("status") Integer status);

}