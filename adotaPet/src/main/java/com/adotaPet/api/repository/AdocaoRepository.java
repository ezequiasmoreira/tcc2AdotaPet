package com.adotaPet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adotaPet.api.domain.Adocao;;

@Repository
public interface AdocaoRepository extends JpaRepository<Adocao, Integer> {

}