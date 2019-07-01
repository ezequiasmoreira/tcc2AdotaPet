package com.adotaPet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adotaPet.api.domain.Acompanhamento;;

@Repository
public interface AcompanhamentoRepository extends JpaRepository<Acompanhamento, Integer> {

}