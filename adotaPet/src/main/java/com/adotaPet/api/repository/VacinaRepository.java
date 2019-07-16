package com.adotaPet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adotaPet.api.domain.Vacina;


@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Integer> {

}