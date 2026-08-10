package com.evaluacion.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evaluacion.demo.model.Pais;

public interface PaisRepository extends JpaRepository<Pais, Integer> {
}
