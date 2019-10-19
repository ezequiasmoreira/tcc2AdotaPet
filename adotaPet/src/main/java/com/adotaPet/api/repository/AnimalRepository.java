package com.adotaPet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.adotaPet.api.domain.Adocao;
import com.adotaPet.api.domain.Animal;
import com.adotaPet.api.domain.Doenca;
import com.adotaPet.api.domain.Pessoa;
import com.adotaPet.api.domain.Raca;;


@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {
	
	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Animal obj INNER JOIN obj.doencas doen WHERE obj.nome LIKE %:nome% AND doen IN :doencas")
	Page<Animal> findDistinctByNomeContainingAndDoencasIn(@Param("nome") String nome, @Param("doencas") List<Doenca> doencas, Pageable pageRequest);
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Animal obj WHERE obj.ong.id = :ongId ORDER BY obj.codigo")
	public List<Animal> findOng(@Param("ongId") Integer ongId);
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Animal obj WHERE obj.id = :id ")
	Animal findAnimalId(@Param("id") Integer id);
	
	@Transactional(readOnly=true)
	Page<Animal> findByRacaIdAndStatus(Integer raca_id,Integer status,Pageable pageRequest);
	
	@Transactional(readOnly=true)
	Page<Animal> findByRacaIdAndStatusAndOngId(Integer raca_id,Integer status,Integer ong_id,Pageable pageRequest);
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Animal obj  WHERE obj.status = 1 ")
	List<Animal> obterAnimaisPorFiltro();
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Animal obj  WHERE obj.ong.id = :ongId")
	List<Animal> obterAnimaisPorFiltro(@Param("ongId") Integer ongId);
	
	@Transactional(readOnly=true)
	@Query("SELECT max(obj.codigo) + 1 FROM Animal obj WHERE obj.ong.id = :ongId ")
	Integer obterCodigo(@Param("ongId") Integer ongId);
}