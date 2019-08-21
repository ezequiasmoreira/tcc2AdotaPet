package com.adotaPet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.adotaPet.api.domain.Ong;

@Repository
public interface OngRepository extends JpaRepository<Ong, Integer> {
		
	@Transactional(readOnly=true)
	Ong findByCnpj(String cnpj);
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Ong obj WHERE obj.id = :id ")
	Ong findOngId(@Param("id") Integer id);

}