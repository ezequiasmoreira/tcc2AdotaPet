package com.adotaPet.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adotaPet.api.domain.VacinaItem;


@Repository
public interface VacinaItemRepository extends JpaRepository<VacinaItem, Integer> {

}