package com.adotaPet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.adotaPet.api.domain.Vacina;


@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Integer> {
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Vacina obj WHERE obj.id = :id ")
	Vacina findVacinaId(@Param("id") Integer id);

}