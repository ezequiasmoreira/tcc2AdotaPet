package com.adotaPet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adotaPet.api.domain.Animal;;


@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {

}