package com.adotaPet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adotaPet.api.domain.Estado;


@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

}






