package com.adotaPet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adotaPet.api.domain.Ong;

@Repository
public interface OngRepository extends JpaRepository<Ong, Integer> {

}