package com.adotaPet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.adotaPet.api.domain.Raca;


@Repository
public interface RacaRepository extends JpaRepository<Raca, Integer> {
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Raca obj WHERE obj.id = :id ")
	Raca findRacaId(@Param("id") Integer id);

}