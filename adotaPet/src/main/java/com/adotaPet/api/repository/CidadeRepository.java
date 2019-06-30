package com.adotaPet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adotaPet.api.domain.Cidade;


@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

}






