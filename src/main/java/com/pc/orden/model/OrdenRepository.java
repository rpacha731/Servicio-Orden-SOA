package com.pc.orden.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrdenRepository extends JpaRepository<Orden, Integer> {

}
