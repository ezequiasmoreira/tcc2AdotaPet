package com.adotaPet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adotaPet.api.domain.Doenca;


@Repository
public interface DoencaRepository extends JpaRepository<Doenca, Integer> {

}